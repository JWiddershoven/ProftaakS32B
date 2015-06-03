/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import Interfaces.IGame;
import Interfaces.IMap;
import Interfaces.IUser;
import Server.CollisionChecker;
import Server.Game;
import Server.Server;
import Shared.Ball;
import Shared.Block;
import Shared.CPU;
import Shared.GameObject;
import Shared.Map;
import Shared.Paddle;
import Shared.PowerUp;
import Shared.TVector2;
import Shared.User;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.gc;
import java.nio.charset.Charset;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * VOOR DE SERVER
 *
 * @author Mnesymne
 */
public class RMIGame implements IGame, Runnable {

    int playerAmount = 1;
    private int id;
    private int gameTime;
    private boolean powerUps;      
    private int startup = 0;
    private final int FPS = 60;
    private final long targetTime = 1000 / FPS;
    private boolean inProgress = false;
    private Thread gameLoopThread;
    private Map selectedmap;
    private int gameTimeInSecondsRemaining;
    /**
     * Ticks per second
     */
    private Timer secondsTimer;
    //private ArrayList<Map> selectedMaps;
    private final ArrayList<IUser> userList;
    private final ArrayList<CPU> botList;
    public ArrayList<GameObject> objectList;
    private final ArrayList<Ball> ballList;
    private final ArrayList<Block> blockList;
    private final ArrayList<Paddle> paddleList;
    private final ArrayList<Block> destroyableBlockList;
    private final ArrayList<GameObject> changedObjectsList;
    private final ArrayList<GameObject> removedObjectsList;

    private User player1, player2, player3, player4;
    private CPU cpu1, cpu2, cpu3, cpu4;
    private Paddle P1Paddle, P2Paddle, P3Paddle, P4Paddle;
    private int numberOfPLayersLeft = 4;

    private final TVector2 windowSize;

    public TVector2 getWindowSize() {
        return windowSize;
    }

    private BufferedImage BallImage, DestroyImage, normalBlockImage, PowerUpImage, PaddleImage, WhiteSpaceImage;

    // <editor-fold defaultstate="collapsed" desc="- - - - - - - - - - - I n t e r f a c e s   O V E R R I D E - - - - - - - - - - -">>
    @Override
    public boolean leaveGame(int gameid, String username) throws RemoteException {

        for (IUser user : userList) {
            if (user.getUsername(user).equals(username)) {
                userList.remove(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean kickPlayer(String username, int lobbyID) throws RemoteException {

        for (IUser user : userList) {
            if (user.getUsername(user).equals(username)) {
                userList.remove(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public void addMap(IMap map) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void joinGame(int gameid, String username) throws RemoteException {
        boolean add = true;

        for (IUser user : userList) {
            if (user.getUsername(user).equals(username)) {
                add = false;
                break;
            }
        }
        if (add) {
            userList.add(new RMIUser(username, "doesnotexist", "doesnotexist@gmail.com", 10));
            //System.out.println(Juser.getUsername(Juser));
            //return this;
        }
        //return null;
    }

    @Override
    public ArrayList<String> getPlayersInformationInGame(int game) throws RemoteException {
        ArrayList<String> returnvalue = new ArrayList<>();
        if (this.userList != null) {
            for (IUser user : this.userList) {
                if (user == null || user.getUsername(user).equals(null)) {
                    System.out.println("Player information - PLAYER IS NULL");
                }
                else {

                    returnvalue.add(user.getPlayerInformation(user.getUsername(user)));
                }
            }
        }
        return returnvalue;
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public void moveLeft(int gameId, String username) throws RemoteException {
        for (int i = paddleList.size(); i > 0; i--) {
            Paddle p = paddleList.get(i);
            User u = (User) p.getPlayer();
            if (u.getUsername().equals(username)) {
                p.Move(Paddle.Direction.LEFT);
                break;
            }
        }
    }

    @Override
    public void moveRight(int gameId, String username) throws RemoteException {
        for (int i = paddleList.size(); i > 0; i--) {
            Paddle p = paddleList.get(i);
            User u = (User) p.getPlayer();
            if (u.getUsername().equals(username)) {
                p.Move(Paddle.Direction.RIGHT);
                break;
            }
        }
    }

    @Override
    public void getAllBalls(int gameId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<GameObject> getAllGameObjects(int gameId) throws RemoteException {
        return this.objectList;
    }

    @Override
    public ArrayList<GameObject> getChangedGameObjects(int gameId) throws RemoteException {
        return this.changedObjectsList;
    }

    @Override
    public ArrayList<GameObject> getRemovedGamesObjects(int gameId) throws RemoteException {
        return this.removedObjectsList;
    }
    // </editor-fold>

    public RMIGame(int id, int gameTime, boolean powerUps) {
        this.id = id;
        this.gameTime = gameTime;
        this.powerUps = powerUps;
        this.botList = new ArrayList<>();
        this.userList = new ArrayList<>();
        this.objectList = new ArrayList<>();
        this.blockList = new ArrayList<>();
        this.ballList = new ArrayList<>();
        this.paddleList = new ArrayList<>();
        this.changedObjectsList = new ArrayList<>();
        this.removedObjectsList = new ArrayList<>();
        this.destroyableBlockList = new ArrayList<>();
        this.windowSize = new TVector2(Block.standardBlockSize.getX() * 40, Block.standardBlockSize.getY() * 40);

    }

    public ArrayList<IUser> getUserList() {
        return userList;
    }

    private void generatePlayers() {

        try {
            if (userList.get(0) != null) {
                player1 = (User) userList.get(0);
                System.out.println("Fakakayomama");
            }
        }
        catch (IndexOutOfBoundsException ex) {
            cpu1 = new CPU("Bot1", (byte) 1);
            cpu1.setMyPaddle(P1Paddle);

            System.out.println("PAddle for USER!!!!!!");
        }
        try {
            if (userList.get(1) != null) {
                player2 = (User) userList.get(1);
            }
        }
        catch (IndexOutOfBoundsException ex) {
            cpu2 = new CPU("Bot2", (byte) 1);
            cpu2.setMyPaddle(P2Paddle);

            System.out.println("PAddle for USER!!!!!!");
        }

        try {
            if (userList.get(2) != null) {
                player3 = (User) userList.get(2);

            }
        }
        catch (IndexOutOfBoundsException ex) {
            cpu3 = new CPU("Bot3", (byte) 1);
            cpu3.setMyPaddle(P3Paddle);

            System.out.println("PAddle for USER!!!!!!");
        }

        try {
            if (userList.get(3) != null) {
                player4 = (User) userList.get(3);
            }
        }
        catch (IndexOutOfBoundsException ex) {
            cpu4 = new CPU("Bot4", (byte) 1);
            cpu4.setMyPaddle(P4Paddle);

            System.out.println("PAddle for USER!!!!!!");
        }

    }

    /**
     * Loads a map
     *
     * @param mapUrl Location to the map file (*.txt)
     * @return Error message
     */
    public String loadMap(String mapUrl) {
        String text = "";
        ArrayList<String> mapLayout = new ArrayList<>();
        try {
            File file = new File(mapUrl);
            FileInputStream fis = new FileInputStream(mapUrl);
            InputStreamReader in = new InputStreamReader(fis, Charset.forName("UTF-8"));
            char[] buffer = new char[(int) file.length()];
            int n = in.read(buffer);
            //Remove whitespaces
            text = new String(buffer, 0, n).replaceAll("\\s+", "");
            in.close();
            if (text.length() > 1600) {
                throw new IOException();
            }
            //Add each row into a Array of strings
            String mapDesign[][] = new String[40][40];
            int location = 0;
            for (String[] mapDesign1 : mapDesign) {
                for (int c = 0; c < mapDesign1.length; c++) {
                    mapDesign1[c] = text.substring(location, location + 1);
                    location++;
                }
            }

            //Add each row into an ArrayList
            for (int r = 0; r < mapDesign.length; r++) {
                String row = new String();
                for (String mapDesign1 : mapDesign[r]) {
                    row = row + mapDesign1;
                }
                mapLayout.add(row);
            }
            readMap(mapLayout);
        }
        catch (FileNotFoundException ex) {
            System.out.println("File could not be found");
            return "File could not be found";
        }
        catch (IOException IOex) {
            System.out.println("Filesize is incorrect, use 40 rows with 40 characters");
            return "Filesize is incorrect, use 40 rows with 40 characters";
        }
        catch (IllegalArgumentException ifex) {
            System.out.println("File incorrect");
            return "File incorrect";
        }
        catch (RuntimeException ex) {
            System.out.println("Textfile size is incorrect, use 40 rows with 40 characters");
            return "Textfile size is incorrect, use 40 rows with 40 characters";
        }
        return "";

    }

    /**
     * Draw objects onto the panel from the ArrayList
     *
     * @param mapLayout ArrayList of Strings with the mapLayout from the
     * LoadMapMethod
     */
    private void readMap(ArrayList<String> mapLayout) {
        generatePlayers();
        try {
            CollisionChecker.gameObjectsList.clear();
            int SpawnNumber = 0;
            Server server = new Server();
            try {
                // X & Y Positions for the blocks
                int y = 0;
                int x = 0;
                PaddleImage = null;
                // Rowcounter for reading the mapLayout
                int rowcount = 1;
                // Velocity 0 since blocks don't move
                TVector2 velocity = new TVector2(0.0f, 0.0f);
                Paddle.WindowLocation paddleLocation = null;
                // Read all rows of the maplayout
                for (String row : mapLayout) {
                    // Read every number on a row of the maplayout
                    for (int c = 0; c <= row.length() - 1; c++) {
                        x = c * 20;
                        String type = row.substring(c, c + 1);
                        TVector2 newObjectPosition = new TVector2(x, y);
                        TVector2 middleOfScreen = new TVector2((float) windowSize.getX() / 2, (float) windowSize.getY() / 2);
                        float diffX = middleOfScreen.getX() - newObjectPosition.getX();
                        float diffY = middleOfScreen.getY() - newObjectPosition.getY();

                        if (type.equals("4") || type.equals("5")) {

                            if (Math.abs(diffX) > Math.abs(diffY)) {
                                // East of West
                                if (diffX < 0) {
                                    paddleLocation = Paddle.WindowLocation.EAST;
                                }
                                else {
                                    paddleLocation = Paddle.WindowLocation.WEST;
                                }
                            }
                            else {
                                if (diffY < 0) {
                                    paddleLocation = Paddle.WindowLocation.SOUTH;
                                }
                                else {
                                    paddleLocation = Paddle.WindowLocation.NORTH;
                                }
                            }
                        }

                        //Check what type of block needs to be created from input
                        switch (type) {
                            // Create undestructable block
                            case "0": {
                                DestroyImage = ImageIO.read(new FileInputStream("Images/Images/GreyBlock.png"));
                                Block wall = new Block(0, false, null, newObjectPosition, velocity, Block.standardBlockSize, DestroyImage);
                                this.addObject(wall);
                             
                                this.blockList.add(wall);
                                break;
                            }
                            // Create white space
                            case "1": {

                                break;
                            }
                            // Create block without powerup
                            case "2": {

                                normalBlockImage = ImageIO.read(new FileInputStream("Images/Images/YellowBlock.png"));
                                Block noPower = new Block(1, true, null, newObjectPosition, velocity, Block.standardBlockSize, normalBlockImage);
                                this.addObject(noPower);
                                this.destroyableBlockList.add(noPower);
                                break;
                            }
                            // Create block with powerup
                            case "3": {
                                PowerUpImage = ImageIO.read(new FileInputStream("Images/Images/RedBlock.png"));
                                PowerUp power = new PowerUp(1, null);
                                power.getRandomPowerUpType();
                                Block withPower = new Block(10, true, power, newObjectPosition, velocity, Block.standardBlockSize, PowerUpImage);
                                this.addObject(withPower);
                                this.destroyableBlockList.add(withPower);
                                break;
                            }
                            // Create horizontal paddle spawn
                            case "4": {
                                TVector2 size = new TVector2(100f, 20f);
                                // Add human player
                                if (playerAmount == 1) // 2 For bottom paddle to be the player paddle
                                {
                                    PaddleImage = ImageIO.read(new FileInputStream("Images/Images/HorizontalPaddle1.png"));
                                    if (player1 != null) {
                                        P1Paddle = new Paddle(0, newObjectPosition, velocity, size, player1, paddleLocation, PaddleImage);
                                        player1.setPaddle(P1Paddle);
                                        this.addObject(P1Paddle);
                                        this.userList.add(player1);
                                        this.paddleList.add(P1Paddle);
                                        playerAmount++;
                                        System.out.println("P1 " + P1Paddle.getWindowLocation());
                                        break;
                                    }
                                    else {
                                        P1Paddle = new Paddle(0, newObjectPosition, velocity, size, cpu1, paddleLocation, PaddleImage);
                                        cpu1.setMyPaddle(P1Paddle);
                                        this.addObject(P1Paddle);
                                        this.botList.add(cpu1);
                                        this.paddleList.add(P1Paddle);
                                        playerAmount++;
                                        System.out.println("P1 " + P1Paddle.getWindowLocation());
                                        break;
                                    }

                                }

                                else if (playerAmount == 4) {

                                    PaddleImage = ImageIO.read(new FileInputStream("Images/Images/HorizontalPaddle2.png"));
                                    size = new TVector2(100f, 20f);
                                    if (player4 != null) {
                                        P4Paddle = new Paddle(0, newObjectPosition, velocity, size, player4, paddleLocation, PaddleImage);
                                        player4.setPaddle(P4Paddle);
                                        this.addObject(P4Paddle);
                                        this.userList.add(player4);
                                        this.paddleList.add(P4Paddle);
                                        playerAmount++;
                                        System.out.println("P4 " + P4Paddle.getWindowLocation());
                                        break;
                                    }
                                    else {
                                        P4Paddle = new Paddle(0, newObjectPosition, velocity, size, cpu4, paddleLocation, PaddleImage);
                                        cpu4.setMyPaddle(P4Paddle);
                                        this.addObject(P4Paddle);
                                        this.botList.add(cpu4);
                                        this.paddleList.add(P4Paddle);
                                        playerAmount++;
                                        System.out.println("P4 " + P4Paddle.getWindowLocation());
                                        break;
                                    }
                                }

                            }
                            // Create vertical paddle spawn
                            case "5": {
                                TVector2 size = new TVector2(20f, 100f);
                                SpawnNumber++;
                                if (SpawnNumber == 1) {
                                    PaddleImage = ImageIO.read(new FileInputStream("Images/Images/VerticalPaddle.png"));
                                }
                                else if (SpawnNumber == 2) {
                                    PaddleImage = ImageIO.read(new FileInputStream("Images/Images/VerticalPaddle2.png"));
                                }
                                if (playerAmount == 2) {
                                    if (player2 != null) {
                                        P2Paddle = new Paddle(0, newObjectPosition, velocity, size, player2, paddleLocation, PaddleImage);
                                        player2.setPaddle(P2Paddle);
                                        this.addObject(P2Paddle);
                                        this.userList.add(player2);
                                        this.paddleList.add(P2Paddle);
                                        playerAmount++;
                                        System.out.println("P2 " + P2Paddle.getWindowLocation());
                                        break;
                                    }
                                    else {
                                        P2Paddle = new Paddle(0, newObjectPosition, velocity, size, cpu2, paddleLocation, PaddleImage);
                                        cpu2.setMyPaddle(P2Paddle);
                                        this.addObject(P2Paddle);
                                        this.botList.add(cpu2);
                                        this.paddleList.add(P2Paddle);
                                        playerAmount++;
                                        System.out.println("P2 " + P2Paddle.getWindowLocation());
                                        break;
                                    }
                                }
                                else if (playerAmount == 3) {
                                    size = new TVector2(20f, 100f);

                                    if (player3 != null) {
                                        P3Paddle = new Paddle(0, newObjectPosition, velocity, size, player3, paddleLocation, PaddleImage);
                                        player3.setPaddle(P3Paddle);
                                        this.addObject(P3Paddle);
                                        this.userList.add(player3);
                                        this.paddleList.add(P3Paddle);
                                        playerAmount++;
                                        System.out.println("P3 " + P3Paddle.getWindowLocation());
                                        break;
                                    }
                                    else {
                                        P3Paddle = new Paddle(0, newObjectPosition, velocity, size, cpu3, paddleLocation, PaddleImage);
                                        cpu3.setMyPaddle(P3Paddle);
                                        this.addObject(P3Paddle);
                                        this.botList.add(cpu3);
                                        this.paddleList.add(P3Paddle);
                                        playerAmount++;
                                        System.out.println("P3 " + P3Paddle.getWindowLocation());
                                        break;
                                    }
                                }
                            }
                            // Create a ball spawn
                            case "6": {
                                BallImage = ImageIO.read(new FileInputStream("Images/Images/Ball.png"));
                                velocity = generateRandomVelocity();
                                Ball ball = new Ball(null, newObjectPosition, velocity, new TVector2(15f, 15f), BallImage);
                                this.addObject(ball);
                                this.ballList.add(ball);
                                break;
                            }
                        }
                    }
                    rowcount++;
                    y += 20;
                }
            }
            catch (Exception ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        catch (RemoteException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * First Call loadMap !
     */
    public void StartGame() {
        gameTimeInSecondsRemaining = gameTime;
  
        secondsTimer = new Timer();
        secondsTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                gameTimeInSecondsRemaining--;
            }
        }, 0, 1000);
        Timer updateRMITimer = new Timer();
        updateRMITimer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                if (ServerRMI.publisher != null && inProgress) 
                {
                    if(startup < 50)
                    {
                    Block[] blocks = new Block[blockList.size()];
                    blocks = blockList.toArray(blocks);
                    ServerRMI.publisher.inform(this, "getBlocks", null, blocks);
                    startup++;
                    }
                
                    ServerRMI.publisher.inform(this, "getTime", null, gameTimeInSecondsRemaining);

                    Block[] blocks = new Block[destroyableBlockList.size()];
                    blocks = destroyableBlockList.toArray(blocks);
                    ServerRMI.publisher.inform(this, "getDestroys", null, blocks);
                    ServerRMI.publisher.inform(this, "getBalls", null, ballList);
                    ServerRMI.publisher.inform(this, "getPaddles", null, paddleList);
                }
            }
        }, 0, 150);
        inProgress = true;
        gameLoopThread = new Thread(this);
        gameLoopThread.start();
    }

    /**
     * Add a GameObject to the game object list
     *
     * @param object
     */
    public void addObject(GameObject object) {
        this.objectList.add(object);
        CollisionChecker.gameObjectsList.add(object);
    }

    /**
     * removes a gameobject from objectlist or ball from ballList
     *
     * @param object
     */
    public void removeObject(GameObject object) {
        this.objectList.remove(object);
        if (CollisionChecker.gameObjectsList.contains(object)) {
            CollisionChecker.gameObjectsList.remove(object);
        }
        if (object instanceof Ball) {
            ballList.remove((Ball) object);
        }
        if (object instanceof Block) {
            destroyableBlockList.remove((Block) object);
        }
    }

    public void removeBall(Ball ball) {
        for (int i = 0; i < ballList.size(); i++) {
            Ball b = ballList.get(i);
            if (b == ball) {
                ballList.remove(b);
            }
        }
    }

    /**
     * Checks if ball needs to be reset to the middle of the map
     *
     * @param b
     */
    private void checkResetBall(Ball b) {
        Rectangle r = new Rectangle((int) b.getMiddlePosition().getX(), (int) b.getMiddlePosition().getY(), 1, 1);
        for (int oCounter = objectList.size(); oCounter > 0; oCounter--) {
            GameObject s = objectList.get(oCounter - 1);
            if (s instanceof Block) {
                Block block = (Block) s;
                if (block.isDestructable() == false) {
                    if (r.intersects(s.getBounds())) {
                        System.out.println("reset ball!");
                        // Set position to middle of the window.
                        b.setVelocity(generateRandomVelocity());
                        b.setPosition(new TVector2(
                                windowSize.getX() / 2 - (b.getSize().getX() / 2),
                                windowSize.getY() / 2 - (b.getSize().getY() / 2)));
                        // hoeven niet meer objects te checken
                        break;
                    }
                }
            }
        }
    }

    /**
     * Checks if ball exited play and removes paddle from player
     *
     * @param b
     */
    private void checkBallExitedPlay(Ball b) throws Exception {
        int playerNumber = checkExitedBounds(b.getMiddlePosition());
        int maxWidthSize = Math.round(40 * Block.standardBlockSize.getX());
        if (playerNumber != 0) {
            TVector2 blockSize = new TVector2(20, 20);
            DestroyImage = ImageIO.read(new FileInputStream("Images/Images/GreyBlock.png"));
            switch (playerNumber) {
                case 1:
                    P1Paddle.addScore(-P1Paddle.getScore());
                    P1Paddle.setVelocity(TVector2.zero);
                    P1Paddle.setEnabled(false);
                    // fill top side with indestructable blocks
                    for (int i = 0; i < Math.ceil(maxWidthSize / blockSize.getX()); i++) {
                        this.addObject(new Block(0, false, null, new TVector2(i * blockSize.getX(), 0),
                                TVector2.zero, blockSize, DestroyImage));
                    }
                    break;
                case 2:
                    P2Paddle.addScore(-P2Paddle.getScore());
                    P2Paddle.setVelocity(TVector2.zero);
                    P2Paddle.setEnabled(false);
                    if (playerAmount == 2) {
                        // bottom side
                        for (int i = 0; i < Math.ceil(maxWidthSize / blockSize.getX()); i++) {
                            this.addObject(new Block(0, false, null, new TVector2(i * blockSize.getX(),
                                    maxWidthSize - blockSize.getY()),
                                    TVector2.zero, blockSize, DestroyImage));
                        }
                    }
                    else {
                        // left side
                        for (int i = 0; i < Math.ceil(maxWidthSize / blockSize.getY()); i++) {
                            this.addObject(new Block(0, false, null, new TVector2(0, i * blockSize.getY()),
                                    TVector2.zero, blockSize, DestroyImage));
                        }
                    }
                    break;
                case 3:
                    // right side
                    P3Paddle.addScore(-P3Paddle.getScore());
                    P3Paddle.setVelocity(TVector2.zero);
                    P3Paddle.setEnabled(false);
                    for (int i = 0; i < Math.ceil(maxWidthSize / blockSize.getY()); i++) {
                        this.addObject(new Block(0, false, null, new TVector2(maxWidthSize - blockSize.getX(),
                                i * blockSize.getY()),
                                TVector2.zero, new TVector2(25, 25), DestroyImage));
                    }
                    break;
                case 4:
                    P4Paddle.addScore(-P4Paddle.getScore());
                    P4Paddle.setVelocity(TVector2.zero);
                    P4Paddle.setEnabled(false);
                    // bottom side
                    for (int i = 0; i < Math.ceil(maxWidthSize / blockSize.getX()); i++) {
                        this.addObject(new Block(0, false, null, new TVector2(i * blockSize.getX(),
                                maxWidthSize - blockSize.getY()),
                                TVector2.zero, blockSize, DestroyImage));
                    }
                    break;
                default:
                    throw new Exception("Ball exited play index exception");
            }
            if (numberOfPLayersLeft > 0) {
                numberOfPLayersLeft--;
            }
            removeObject(b);
            System.out.println("Ball exited play.");
        }
    }

    /**
     * returns playernumber ( 1 - 4 ) where ball exited play
     *
     * @param vector
     * @return player number ( 1 - 4)
     */
    private int checkExitedBounds(TVector2 vector) {
        if (vector.getX() <= 0) {
            return 2;
        }
        if (vector.getY() <= 0) {
            return 1;
        }
        if (vector.getX() >= windowSize.getX()) {
            return 3;
        }
        if (vector.getY() >= windowSize.getY()) {
            if (playerAmount == 4) {
                return 4;
            }
            return 2;
        }
        return 0;
    }

    /**
     * Checks if the number of paddles is there is 1 left.
     *
     * @return true when if there is 1 paddle left , false if there are more!
     */
    private boolean checkNumberOfPaddles() {
        return paddleList.size() <= 1;
    }

    public TVector2 generateRandomVelocity() {
        Random rand = new Random();
        float x = generateRandomFloat(-Ball.maxSpeed + (Ball.maxSpeed / 8), Ball.maxSpeed - (Ball.maxSpeed / 8), rand);
        float y;
        if (x < 0) {
            y = Ball.maxSpeed - (x * -1);
        }
        else {
            y = Ball.maxSpeed - x;
        }
        TVector2 returnVector = new TVector2(x, y);
        System.out.println("generatedVelocity: " + returnVector.toString());
        return returnVector;
    }

    /**
     * *
     *
     * @param min
     * @param max
     * @param rand
     * @return NOt between -0.1and 0.1f
     */
    private float generateRandomFloat(float min, float max, Random rand) {
        float finalFloat = 0f;
        while (finalFloat > -0.1f && finalFloat < 0.1f) {
            finalFloat = rand.nextFloat() * (max - min) + min;
        }
        return finalFloat;
    }

    @Override
    public void run() {
        // Do while game is started
        while (inProgress) {
            if (!checkGameTime()) {
                inProgress = false;
            }
            long start, elapsed, wait;
            start = System.nanoTime();
            // calls update function on all objects
            tick();

            elapsed = System.nanoTime() - start;
            wait = targetTime - elapsed / 10000;
            if (wait <= 0) {
                wait = 5;
            }

            try {
                // Deze sleep verwijderen ( dit is alleen voor test )
                gameLoopThread.sleep(10);
                gameLoopThread.sleep(wait);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("While exited");
        exitGame();
    }

    /**
     * Checks the game time!
     *
     * @return false if the game has to exit, else true if the game can continue
     */
    private boolean checkGameTime() {
        return gameTimeInSecondsRemaining > 0;
    }

    /**
     * Updates all objects
     */
    public void tick() {
        for (int bCounter = ballList.size(); bCounter > 0; bCounter--) {
            Ball b = ballList.get(bCounter - 1);
            ArrayList<GameObject> listToRemove = b.update();
            for (int i = 0; i < listToRemove.size(); i++) {
                removeObject(listToRemove.get(i));
            }
            checkResetBall(b);
            try {
                checkBallExitedPlay(b);

            }

            catch (Exception ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (CPU c : botList) {
            c.update(this.ballList);
        }
        // TODO: check
//        for (Paddle p : paddleList) {
//                p.update();
//        }

        if (numberOfPLayersLeft == 0) {
            inProgress = false;
        }

        if (checkNumberOfPaddles()) {
            inProgress = false;
        }

    }

    private void exitGame() {
        gameLoopThread.interrupt();
        gameLoopThread = null;
        this.botList.clear();
        this.objectList.clear();
        this.userList.clear();
        this.paddleList.clear();
        this.ballList.clear();
        gc();
        ServerRMI.publisher.inform(this, "getGameOver", null, true);
        System.out.println("Exited game");
    }
}
