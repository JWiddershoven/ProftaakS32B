/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Shared.Ball;
import Shared.Block;
import Shared.CPU;
import Shared.GameObject;
import Shared.Map;
import Shared.Paddle;
import Shared.Paddle.WindowLocation;
import Shared.PowerUp;
import Shared.TVector2;
import Shared.User;
import Shared.WhiteSpace;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Mnesymne
 */
public class Game extends JPanel implements Runnable {

    int playerAmount = 1;

    private int id;
    private int gameTime;
    private boolean powerUps;
    private boolean inProgress = false;
    private Thread gameLoopThread;
    private final int FPS = 60;
    private final long targetTime = 1000 / FPS;
    private Map selectedmap;

    //private ArrayList<Map> selectedMaps;
    private ArrayList<User> userList;
    private ArrayList<CPU> botList;
    public ArrayList<GameObject> objectList;
    private ArrayList<Ball> ballList;
    private ArrayList<Paddle> paddleList;

    private WhiteSpace whiteSpace;
    private JFrame window;
    private User player1, player2, player3, player4;
    private CPU cpu1, cpu2, cpu3, cpu4;
    private Paddle P1Paddle, P2Paddle, P3Paddle, P4Paddle;
    private int numberOfPLayersLeft = 4;
    private BufferedImage BallImage, DestroyImage, normalBlockImage, PowerUpImage, PaddleImage, WhiteSpaceImage;

    /**
     * Getter of id
     *
     * @return id as int
     */
    public int getId() {
        return id;
    }

    /**
     * Setter of id
     *
     * @param id value of id as int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter of gameTime
     *
     * @return gameTime as int
     */
    public int getGameTime() {
        return gameTime;
    }

    /**
     * Setter of gameTime
     *
     * @param gameTime value of gameTime as int
     */
    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }

    /**
     * Getter of powerUps
     *
     * @return powerUps as boolean
     */
    public boolean getPowerUps() {
        return powerUps;
    }

    /**
     * Setter of powerUps
     *
     * @param powerUps value of powerUps as boolean
     */
    public void setPowerUps(boolean powerUps) {
        this.powerUps = powerUps;
    }

    /**
     * Getter of inProgress
     *
     * @return inProgress as boolean
     */
    public boolean getInProgress() {
        return this.inProgress;
    }

    /**
     * Setter of inProgress
     *
     * @param setValue value of inProgress as boolean
     * @return value of inProgress
     */
    public boolean setInProgress(boolean setValue) {
        if (setValue == true) {
            this.inProgress = false;
        }
        else {
            this.inProgress = true;
        }
        return inProgress;
    }

    public int getNumberOfPlayers() {
        return selectedmap.getPlayerAmount();
    }
    
    /**
     * Returns standard 40*blocksize by 40*blocksize
     * @return 
     */
    public TVector2 getGameSize()
    {
     return new TVector2(40 * Block.standardBlockSize.getX(), 
             40*Block.standardBlockSize.getY());   
    }

    public ArrayList<User> getHumanPlayers() {
        return this.userList;
    }

    public ArrayList<CPU> getCPUPlayers() {
        return this.botList;
    }

    public ArrayList<Paddle> getPaddles() {
        return this.paddleList;
    }

    public void removePaddle(Paddle p1) {
        for (int i = paddleList.size() - 1; i > 0; i--) {
            Paddle p = paddleList.get(i);
            if (p.getWindowLocation() == p1.getWindowLocation()) {
                paddleList.remove(p);
            }
        }
    }

    /**
     * Constructor of game
     *
     * @param id value of id as int
     * @param gameTime value of gameTime as int
     * @param powerUps value of powerUps as int
     * @param players
     */
    public Game(int id, int gameTime, boolean powerUps, ArrayList<User> players) {
        this.id = id;
        this.gameTime = gameTime;
        this.powerUps = powerUps;
        this.botList = new ArrayList<>();
        this.userList = new ArrayList<>();
        this.objectList = new ArrayList<>();
        this.ballList = new ArrayList<>();
        this.paddleList = new ArrayList<>();
        this.setFocusable(true);

        try {
            if (players.get(0) != null) {
                player1 = players.get(0);
            }
        }
        catch (IndexOutOfBoundsException ex) {
            cpu1 = new CPU("Bot1", (byte) 1, this);
            cpu1.setMyPaddle(P1Paddle);
        }

        try {
            if (players.get(1) != null) {
                player2 = players.get(1);
            }
        }
        catch (IndexOutOfBoundsException ex) {
            cpu2 = new CPU("Bot2", (byte) 1, this);
        }
        try {
            if (players.get(2) != null) {
                player3 = players.get(2);
            }
        }
        catch (IndexOutOfBoundsException ex) {
            cpu3 = new CPU("Bot3", (byte) 1, this);
        }
        try {
            if (players.get(3) != null) {
                player4 = players.get(3);
            }
        }
        catch (IndexOutOfBoundsException ex) {
            cpu4 = new CPU("Bot4", (byte) 1, this);
        }

    }

    /**
     * Adds a CPU player to the game
     *
     * @param botName value of botName as String
     * @param botDifficulty value of Difficulty as Byte
     */
    //TODO: Verwijderen?
    public void addBot(String botName, Byte botDifficulty) {
        TVector2 standardSize = new TVector2(25, 10);
        TVector2 position = new TVector2(50, 50);
        TVector2 velocity = new TVector2(10, 10);
        //paddle1 = new Paddle(10, position, velocity, standardSize, player1, Paddle.WindowLocation.NORTH);
        //CPU newBot = new CPU(botName,botDifficulty,,this);
        //botList.add(newBot);
    }

//    /**
//     * Removes a CPU player from the game
//     *
//     * @param botName value of botName as String
//     */
//    public void removeBot(String botName) {
//        for (int i = 0; i < botList.size(); i++) {
//            CPU c = botList.get(i);
//            if (c.getName().equals(botName)) {
//                botList.remove(c);
//            }
//        }
//    }
//
//    public void removePlayer(String playerName) {
//        for (int i = userList.size(); i > 0; i--) {
//            User u = userList.get(i - 1);
//            if (u.getUsername().equals(playerName)) {
//                userList.remove(u);
//            }
//        }
//    }

    public void removeBall(Ball ball) {
        for (int i = 0; i < ballList.size(); i++) {
            Ball b = ballList.get(i);
            if (b == ball) {
                ballList.remove(b);
            }
        }
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
     * removes a gameobject from objectlist
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
    }

    /**
     * Get all the Objects from the games object list
     *
     * @return ArrayList of GameObjects
     */
    public ArrayList<GameObject> getObjectList() {
        return this.objectList;
    }

    public ArrayList<Ball> getBallList() {
        return ballList;
    }

    /**
     * Create the window, draw the objects and start the game
     *
     * @return
     */
    public Thread setupGame() {
        //Open file dialog and save input
        ArrayList<String> mapLayout = this.loadMap();
        if (mapLayout != null) {
            try {
                //Create the window
                window = new JFrame();
                window.setSize(819, 848);
                window.setBackground(Color.white);
                window.setLocationRelativeTo(null);
                //Draw the level from the input
                this.drawMap(mapLayout);

                //Add the drawn level to the window and then start the game
                window.setContentPane(this);
                window.setVisible(true);
                window.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        System.out.println("inProgress set false");
                        inProgress = false;
                    }
                });
                WhiteSpaceImage = ImageIO.read(new FileInputStream("Images/Images/WhiteSpaceImage.jpg"));
                whiteSpace = new WhiteSpace(TVector2.zero, TVector2.zero,
                        new TVector2(window.getWidth() + 10, window.getHeight() + 10), WhiteSpaceImage);
                Thread.sleep(5000);
                this.startGame();
            }
            catch (IOException | InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        inProgress = true;
        gameLoopThread = new Thread(this);
        return gameLoopThread;
    }

    /**
     * Method to start the game and thread that move the objects
     */
    public void startGame() {
        inProgress = true;
        gameLoopThread = new Thread(this);
        gameLoopThread.start();
    }

    /**
     * Opens a filedialog where the user can select a .txt file to be loaded
     * into a ArrayList of Strings
     *
     * @return ArrayList of Strings loaded map file as ArrayList.
     */
    public ArrayList<String> loadMap() {
        CollisionChecker.gameObjectsList.clear();
        // Open file dialog
        File file = null;
        JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.home")));
        chooser.setDialogTitle("Select mapfile");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Textfile", "txt");
        chooser.setFileFilter(filter);
        int code = chooser.showOpenDialog(this);
        ArrayList<String> mapLayout = new ArrayList<>();
        String text = "";
        if (code == JFileChooser.APPROVE_OPTION) {
            try {
                //Read the selected file
                File selectedFile = chooser.getSelectedFile();
                String fileName = selectedFile.getName();
                FileInputStream fis = new FileInputStream(selectedFile);
                InputStreamReader in = new InputStreamReader(fis, Charset.forName("UTF-8"));
                char[] buffer = new char[(int) selectedFile.length()];
                int n = in.read(buffer);
                //Remove whitespaces
                text = new String(buffer, 0, n).replaceAll("\\s+", "");
                in.close();
                if (text.length() > 1600) {
                    throw new IOException();
                }
            }
            catch (FileNotFoundException ex) {
                System.out.println("File could not be found");
                return null;
            }
            catch (IOException IOex) {
                System.out.println("Filesize is incorrect, use 40 rows with 40 characters");
                return null;
            }

            try {
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
            }
            catch (IllegalArgumentException ifex) {
                System.out.println("File incorrect");
                return null;
            }
            catch (RuntimeException ex) {
                System.out.println("Textfile size is incorrect, use 40 rows with 40 characters");
                return null;
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Selecteer een map!", "Error", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
        return mapLayout;
    }

    /**
     * Draw objects onto the panel from the ArrayList
     *
     * @param mapLayout ArrayList of Strings with the mapLayout from the
     * LoadMapMethod
     */
    public void drawMap(ArrayList<String> mapLayout) {

        try {

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
                WindowLocation paddleLocation = null;
                // Read all rows of the maplayout
                for (String row : mapLayout) {
                    // Read every number on a row of the maplayout
                    for (int c = 0; c <= row.length() - 1; c++) {
                        x = c * 20;
                        String type = row.substring(c, c + 1);
                        TVector2 newObjectPosition = new TVector2(x, y);
                        TVector2 middleOfScreen = new TVector2((float) window.getWidth() / 2, (float) window.getHeight() / 2);
                        float diffX = middleOfScreen.getX() - newObjectPosition.getX();
                        float diffY = middleOfScreen.getY() - newObjectPosition.getY();

                        if (type.equals("4") || type.equals("5")) {

                            if (Math.abs(diffX) > Math.abs(diffY)) {
                                // East of West
                                if (diffX < 0) {
                                    paddleLocation = WindowLocation.EAST;
                                }
                                else {
                                    paddleLocation = WindowLocation.WEST;
                                }
                            }
                            else {
                                if (diffY < 0) {
                                    paddleLocation = WindowLocation.SOUTH;
                                }
                                else {
                                    paddleLocation = WindowLocation.NORTH;
                                }
                            }
                        }

                        //Check what type of block needs to be created from input
                        switch (type) {
                            // Create undestructable block
                            case "0": {

                                DestroyImage = ImageIO.read(new FileInputStream("Images/Images/GreyBlock.png"));
                                Block wall = new Block(0, false, null, newObjectPosition, velocity, new TVector2(25, 25), DestroyImage);
                                this.addObject(wall);

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
                                break;
                            }
                            // Create block with powerup
                            case "3": {
                                PowerUpImage = ImageIO.read(new FileInputStream("Images/Images/RedBlock.png"));
                                PowerUp power = new PowerUp(1, null);
                                power.getRandomPowerUpType();
                                Block withPower = new Block(10, true, power, newObjectPosition, velocity, Block.standardBlockSize, PowerUpImage);
                                this.addObject(withPower);
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
                                Ball ball = new Ball(null, newObjectPosition, velocity, new TVector2(15f, 15f), this, BallImage);
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
                System.out.println("ERROR in drawMap: " + ex.getMessage());
            }
        }
        catch (RemoteException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        try {
            ArrayList<GameObject> drawList = new ArrayList<>();
            drawList.addAll(this.getObjectList());
        //Multiple for loops, order of drawing is wery wery importanté

            // Draw whitespace
//        if(WhiteSpaceImage == null)
//        {
//        g.setColor(Color.white);
//        g.fillRect((int) whiteSpace.getPosition().getX(),
//                (int) whiteSpace.getPosition().getY(),
//                (int) whiteSpace.getSize().getX(), (int) whiteSpace.getSize().getY());
//        }
//        else
//        {
            //}
            // <editor-fold defaultstate="collapsed" desc="- - - - - - - - - - - Draw Objects - - - - - - - - - - -">>
            if (whiteSpace != null) {
                g.drawImage(WhiteSpaceImage, (int) whiteSpace.getPosition().getX(), (int) whiteSpace.getPosition().getY(), this);
            }

            for (int i = drawList.size() - 1; i >= 0; i--) {
                GameObject o = drawList.get(i);
                //Draw a block
                if (o instanceof Block) {
                    Block b = (Block) o;
                    if (b.isDestructable()) {
                        if (b.getPowerUp() != null) {
                            if (b.getImage() == null) {
                                g.setColor(b.getColor());
                                g.fillRect((int) b.getPosition().getX(), (int) b.getPosition().getY(), (int) b.getSize().getX(), (int) b.getSize().getY());
                                g.setColor(Color.black);
                                g.drawRect((int) b.getPosition().getX(), (int) b.getPosition().getY(), (int) b.getSize().getX(), (int) b.getSize().getY());
                                drawList.remove(i);
                            }
                            else {
                                g.drawImage(b.getImage(), (int) b.getPosition().getX(), (int) b.getPosition().getY(), this);
                            }
                        }
                        else {
                            if (b.getImage() == null) {
                                g.setColor(b.getColor());
                                g.fillRect((int) b.getPosition().getX(), (int) b.getPosition().getY(), (int) b.getSize().getX(), (int) b.getSize().getY());
                                g.setColor(Color.black);
                                g.drawRect((int) b.getPosition().getX(), (int) b.getPosition().getY(), (int) b.getSize().getX(), (int) b.getSize().getY());
                                drawList.remove(i);
                            }
                            else {
                                g.drawImage(b.getImage(), (int) b.getPosition().getX(), (int) b.getPosition().getY(), this);
                            }
                        }
                    }
                    else {
                        if (b.getImage() == null) {
                            g.setColor(b.getColor());
                            g.fillRect((int) b.getPosition().getX(), (int) b.getPosition().getY(), (int) b.getSize().getX(), (int) b.getSize().getY());
                            g.setColor(Color.black);
                            g.drawRect((int) b.getPosition().getX(), (int) b.getPosition().getY(), (int) b.getSize().getX(), (int) b.getSize().getY());
                            drawList.remove(i);
                        }
                        else {
                            g.drawImage(b.getImage(), (int) b.getPosition().getX(), (int) b.getPosition().getY(), this);
                        }
                    }
                }
            }

            for (int i = drawList.size() - 1; i >= 0; i--) {
                GameObject o = drawList.get(i);
                //Draw a ball
                if (o instanceof Ball) {
                    Ball b = (Ball) o;
                    if (BallImage == null) {
                        g.setColor(Color.red);
                        g.fillOval((int) b.getPosition().getX(), (int) b.getPosition().getY(), (int) b.getSize().getX(), (int) b.getSize().getY());
                        g.setColor(Color.white);
                        g.drawOval((int) b.getPosition().getX(), (int) b.getPosition().getY(), (int) b.getSize().getX(), (int) b.getSize().getY());
                    }
                    else {
                        g.drawImage(BallImage, (int) b.getPosition().getX(), (int) b.getPosition().getY(), this);
                    }

                } //Draw a paddle
                else if (o instanceof Paddle) {
                    Paddle p = (Paddle) o;
                    PaddleImage = (BufferedImage) p.getImage();
                    if (PaddleImage == null) {

                        g.setColor(p.getColor());
                        g.fillRect((int) p.getPosition().getX(), (int) p.getPosition().getY(), (int) p.getSize().getX(), (int) p.getSize().getY());
                        g.setColor(Color.white);
                        g.drawRect((int) p.getPosition().getX(), (int) p.getPosition().getY(), (int) p.getSize().getX(), (int) p.getSize().getY());
                    }
                    else {
                        g.drawImage(p.getImage(), (int) p.getPosition().getX(), (int) p.getPosition().getY(), this);
                    }
                }
            }
            // </editor-fold>

            String scoreText = "";
            int ypos = 25;
            Font scoreFont = new Font("arial", Font.BOLD, 16);
            Font paddleFont = new Font("arial", Font.BOLD, 12);
            Graphics2D g2d = (Graphics2D) g;
            g.setColor(Color.WHITE);
            for (int uCounter = this.userList.size(); uCounter > 0; uCounter--) {
                User u = userList.get(uCounter - 1);
                Paddle p = u.getPaddle();
                g.setFont(scoreFont);
                scoreText = u.getUsername() + ": " + u.getPaddle().getScore();
                g.drawString(scoreText, 10, ypos);
                g.setFont(paddleFont);
                TVector2 drawNameLocation = TVector2.zero;
                float posX = u.getPaddle().getMiddlePosition().getX() - (u.getUsername().length() * 3);
//                AffineTransform af = g2d.getTransform();
                switch (p.getWindowLocation()) {
                    case EAST:
//                        af.setToRotation(0);
//                        g2d.setTransform(af);
                        drawNameLocation = new TVector2(p.getMiddlePosition().getX() - 85f, p.getMiddlePosition().getY());
                        break;
                    case WEST:
//                        af.setToRotation(0);
//                        g2d.setTransform(af);
                        drawNameLocation = new TVector2(p.getMiddlePosition().getX() + 25f, p.getMiddlePosition().getY());
                        break;
                    case NORTH:
//                        af.setToRotation(0);
//                        g2d.setTransform(af);
                        drawNameLocation = new TVector2(p.getMiddlePosition().getX() - 25f, p.getMiddlePosition().getY() + 25f);
                        break;
                    case SOUTH:
//                        af.setToRotation(0);
//                        g2d.setTransform(af);
                        drawNameLocation = new TVector2(p.getMiddlePosition().getX() - 25f, p.getMiddlePosition().getY() - 25f);
                        break;
                }
                g2d.drawString(p.getWindowLocation().toString() + "  " + u.getUsername(), (int) drawNameLocation.getX(), (int) drawNameLocation.getY());
                ypos += 25;

            }
            for (int counter = this.botList.size(); counter > 0; counter--) {
                CPU c = this.botList.get(counter - 1);
                Paddle p = c.getMyPaddle();
                g.setFont(scoreFont);
                scoreText = c.getName() + ": " + c.getMyPaddle().getScore();
                g.drawString(scoreText, 10, ypos);
                g.setFont(paddleFont);
                float posX = c.getMyPaddle().getMiddlePosition().getX() - (c.getName().length() * 3);
                TVector2 drawNameLocation = TVector2.zero;
                //AffineTransform af = new AffineTransform();
                switch (p.getWindowLocation()) {
                    case EAST:
//                        af.setToRotation(-Math.PI);
//                        g2d.setTransform(af);
                        drawNameLocation = new TVector2(p.getMiddlePosition().getX() - 85f, p.getMiddlePosition().getY());
                        break;
                    case WEST:
//                        af.setToRotation(0);
//                        g2d.setTransform(af);
                        drawNameLocation = new TVector2(p.getMiddlePosition().getX() - 25f, p.getMiddlePosition().getY());
                        break;
                    case NORTH:
//                        af.setToRotation(0);
//                        g2d.setTransform(af);
                        drawNameLocation = new TVector2(p.getMiddlePosition().getX() - 25f, p.getMiddlePosition().getY() + 15f);
                        break;
                    case SOUTH:
//                        af.setToRotation(0);
//                        g2d.setTransform(af);
                        drawNameLocation = new TVector2(p.getMiddlePosition().getX() - 25f, p.getMiddlePosition().getY() - 15f);
                        break;
                }
                g2d.drawString(p.getWindowLocation().toString() + "  " + c.getName(), (int) drawNameLocation.getX(), (int) drawNameLocation.getY());
                ypos += 25;

            }
        }
        catch (Exception ex) {
            System.out.println("PaintError: " + ex.getMessage());
        }
    }
    // <editor-fold defaultstate="collapsed" desc="- - - - - - - - - - - paintComponent nog sneller meer geheugen - - - - - - - - - - -">
    // Lorenzo: Ik heb geprobeerd op te lossen / te verlichten, maar lukte niet.
    /*
     @Override
     public void paintComponent(Graphics g)
     {
     ArrayList<GameObject> toDrawBlockList = new ArrayList<>();
     ArrayList<GameObject> toDrawPaddleList = new ArrayList<>();
     ArrayList<GameObject> toDrawBallList = new ArrayList<>();

     // Multiple for loops, order of drawing is wery wery importanté
     // Loop backwards in case objects get removed
     for (int i = this.objectList.size() - 1; i < 0; i--)
     {
     GameObject go = this.objectList.get(i);
     // Draw a whitespace
     if (go instanceof WhiteSpace)
     {
     WhiteSpace w = (WhiteSpace) go;
     g.setColor(w.getColor());
     g.fillRect((int) w.getPosition().getX(), (int) w.getPosition().getY(), (int) w.getSize().getX(), (int) w.getSize().getY());
     }
     else
     {
     if (this.objectList.get(i) instanceof Block)
     {
     toDrawBlockList.add((Block) go);
     }
     else if (this.objectList.get(i) instanceof Paddle)
     {
     toDrawPaddleList.add((Paddle) go);
     }
     else if (this.objectList.get(i) instanceof Ball)
     {
     toDrawBallList.add((Ball) go);
     }
     }
     }
     // Draw blocks
     for (GameObject go : toDrawBlockList)
     {
     if (go instanceof Block)
     {
     Block b = (Block) go;
     g.setColor(b.getColor());
     g.fillRect((int) b.getPosition().getX(), (int) b.getPosition().getY(), (int) b.getSize().getX(), (int) b.getSize().getY());
     g.setColor(Color.black);
     g.drawRect((int) b.getPosition().getX(), (int) b.getPosition().getY(), (int) b.getSize().getX(), (int) b.getSize().getY());
     }
     }
     // Draw paddles
     for (GameObject go : toDrawPaddleList)
     {
     if (go instanceof Paddle)
     {
     Paddle p = (Paddle) go;
     g.setColor(p.getColor());
     g.fillRect((int) p.getPosition().getX(), (int) p.getPosition().getY(), (int) p.getSize().getX(), (int) p.getSize().getY());
     g.setColor(Color.white);
     g.drawRect((int) p.getPosition().getX(), (int) p.getPosition().getY(), (int) p.getSize().getX(), (int) p.getSize().getY());
     }

     }
     // Draw Balls
     for (GameObject go : toDrawBallList)
     {
     if (go instanceof Ball)
     {
     Ball b = (Ball) go;
     g.setColor(Color.red);
     g.fillOval((int) b.getPosition().getX(), (int) b.getPosition().getY(), (int) b.getSize().getX(), (int) b.getSize().getY());
     g.setColor(Color.white);
     g.drawOval((int) b.getPosition().getX(), (int) b.getPosition().getY(), (int) b.getSize().getX(), (int) b.getSize().getY());
     }
     }
     }
    
     */

    // </editor-fold>
    @Override
    public void run() {
        // Do while game is started
        while (inProgress) {
            long start, elapsed, wait;
            start = System.nanoTime();
            // calls update function on all objects
            tick();

            // Redraw objects on panel
            this.revalidate();
            repaint();

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

    private void exitGame() {
        gameLoopThread.interrupt();
        gameLoopThread = null;
        this.botList.clear();
        this.objectList.clear();
        this.userList.clear();
        this.paddleList.clear();
        this.ballList.clear();
        gc();
        System.out.println("Exited game");
    }

    public void tick() {
        for (int bCounter = ballList.size(); bCounter > 0; bCounter--) {
            Ball b = ballList.get(bCounter - 1);
            b.update();
            checkResetBall(b);
            try {
                checkBallExitedPlay(b);
            }
            catch (Exception ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (CPU c : botList) {
            c.update();
        }
        // TODO: check
//        for (Paddle p : paddleList) {
//                p.update();
//        }

        if (numberOfPLayersLeft == 0) {
            try {
                Thread.sleep(100);
                window.dispose();
            }
            catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
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
                                window.getWidth() / 2 - (b.getSize().getX() / 2),
                                window.getHeight() / 2 - (b.getSize().getY() / 2)));
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
        if (vector.getX() >= window.getSize().width) {
            return 3;
        }
        if (vector.getY() >= window.getSize().height) {
            if (playerAmount == 4) {
                return 4;
            }
            return 2;
        }
        return 0;
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
}
