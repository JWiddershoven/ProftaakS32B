/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import Server.CollisionChecker;
import Server.Server;
import Shared.Paddle.windowLocation;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.gc;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Mnesymne
 */
public class Game extends JPanel implements Runnable, KeyListener
{

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

    /**
     * Getter of id
     *
     * @return id as int
     */
    public int getId()
    {
        return id;
    }

    /**
     * Setter of id
     *
     * @param id value of id as int
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * Getter of gameTime
     *
     * @return gameTime as int
     */
    public int getGameTime()
    {
        return gameTime;
    }

    /**
     * Setter of gameTime
     *
     * @param gameTime value of gameTime as int
     */
    public void setGameTime(int gameTime)
    {
        this.gameTime = gameTime;
    }

    /**
     * Getter of powerUps
     *
     * @return powerUps as boolean
     */
    public boolean getPowerUps()
    {
        return powerUps;
    }

    /**
     * Setter of powerUps
     *
     * @param powerUps value of powerUps as boolean
     */
    public void setPowerUps(boolean powerUps)
    {
        this.powerUps = powerUps;
    }

    /**
     * Getter of inProgress
     *
     * @return inProgress as boolean
     */
    public boolean getInProgress()
    {
        return this.inProgress;
    }

    /**
     * Setter of inProgress
     *
     * @param setValue value of inProgress as boolean
     * @return value of inProgress
     */
    public boolean setInProgress(boolean setValue)
    {
        if (setValue == true)
        {
            this.inProgress = false;
        }
        else
        {
            this.inProgress = true;
        }
        return inProgress;
    }

    public int getNumberOfPlayers()
    {
        return selectedmap.getPlayerAmount();
    }

    public ArrayList<User> getHumanPlayers()
    {
        return this.userList;
    }

    public ArrayList<CPU> getCPUPlayers()
    {
        return this.botList;
    }

    public ArrayList<Paddle> getPaddles()
    {
        return this.paddleList;
    }

    public void removePaddle(Paddle p1)
    {
        for (int i = paddleList.size() - 1; i > 0; i--)
        {
            Paddle p = paddleList.get(i);
            if (p.getWindowLocation() == p1.getWindowLocation())
            {
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
     */
    public Game(int id, int gameTime, boolean powerUps)
    {
        this.id = id;
        this.gameTime = gameTime;
        this.powerUps = powerUps;
        this.botList = new ArrayList<>();
        this.userList = new ArrayList<>();
        this.objectList = new ArrayList<>();
        this.ballList = new ArrayList<>();
        this.paddleList = new ArrayList<>();
        this.setFocusable(true);
        addKeyListener(this);
    }

    /**
     * Adds a CPU player to the game
     *
     * @param botName value of botName as String
     * @param botDifficulty value of Difficulty as Byte
     */
    //TODO: Verwijderen?
    public void addBot(String botName, Byte botDifficulty)
    {
        TVector2 standardSize = new TVector2(25, 10);
        TVector2 position = new TVector2(50, 50);
        TVector2 velocity = new TVector2(10, 10);
        //paddle1 = new Paddle(10, position, velocity, standardSize, player1, Paddle.windowLocation.NORTH);
        //CPU newBot = new CPU(botName,botDifficulty,,this);
        //botList.add(newBot);
    }

    /**
     * Removes a CPU player from the game
     *
     * @param botName value of botName as String
     */
    public void removeBot(String botName)
    {
        for (int i = 0; i < botList.size(); i++)
        {
            CPU c = botList.get(i);
            if (c.getName().equals(botName))
            {
                botList.remove(c);
            }
        }
    }

    public void removePlayer(String playerName)
    {
        for (int i = 0; i < userList.size(); i++)
        {
            User u = userList.get(i);
            if (u.getUsername().equals(playerName))
            {
                userList.remove(u);
            }
        }
    }

    public void removeBall(Ball ball)
    {
        for (int i = 0; i < ballList.size(); i++)
        {
            Ball b = ballList.get(i);
            if (b == ball)
            {
                ballList.remove(b);
            }
        }
    }

    /**
     * Add a GameObject to the game object list
     *
     * @param object
     */
    public void addObject(GameObject object)
    {
        this.objectList.add(object);
        CollisionChecker.gameObjectsList.add(object);
    }

    /**
     * removes a gameobject from objectlist
     *
     * @param object
     */
    public void removeObject(GameObject object)
    {
        this.objectList.remove(object);
        if (CollisionChecker.gameObjectsList.contains(object))
        {
            CollisionChecker.gameObjectsList.remove(object);
        }
        if (object instanceof Ball)
        {
            ballList.remove((Ball) object);
        }
    }

    /**
     * Get all the Objects from the games object list
     *
     * @return ArrayList<GameObject>
     */
    public ArrayList<GameObject> getObjectList()
    {
        return this.objectList;
    }

    public ArrayList<Ball> getBallList()
    {
        return ballList;
    }

    /**
     * Create the window, draw the objects and start the game
     */
    public void setupGame()
    {
        //Open file dialog and save input
        ArrayList<String> mapLayout = this.loadMap();
        if (mapLayout != null)
        {
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
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            window.addWindowListener(new java.awt.event.WindowAdapter()
//            {
//                @Override
//                public void windowClosing(java.awt.event.WindowEvent windowEvent)
//                {
//                    System.out.println("inProgress set false");
//                    inProgress = false;
//                }
//            });
            whiteSpace = new WhiteSpace(TVector2.zero, TVector2.zero,
                    new TVector2(window.getWidth() + 10, window.getHeight() + 10), Color.WHITE);
            this.startGame();
        }

    }

    /**
     * Method to start the game and thread that move the objects
     */
    public void startGame()
    {
        inProgress = true;
        gameLoopThread = new Thread(this);
        gameLoopThread.start();
    }

    /**
     * Opens a filedialog where the user can select a .txt file to be loaded
     * into a ArrayList<String>
     *
     * @return ArrayList<String> loaded map file as ArrayList.
     */
    public ArrayList<String> loadMap()
    {
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
        if (code == JFileChooser.APPROVE_OPTION)
        {
            try
            {
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
            }
            catch (FileNotFoundException ex)
            {
                System.out.println("File could not be found");
                return null;
            }
            catch (IOException IOex)
            {
                System.out.println("File is incorrect");
                return null;
            }

            try
            {
                //Add each row into a Array of strings
                String mapDesign[][] = new String[40][40];
                int location = 0;
                for (String[] mapDesign1 : mapDesign)
                {
                    for (int c = 0; c < mapDesign1.length; c++)
                    {
                        mapDesign1[c] = text.substring(location, location + 1);
                        location++;
                    }
                }

                //Add each row into an ArrayList
                for (int r = 0; r < mapDesign.length; r++)
                {
                    String row = new String();
                    for (String mapDesign1 : mapDesign[r])
                    {
                        row = row + mapDesign1;
                    }
                    mapLayout.add(row);
                }
            }
            catch (IllegalArgumentException ifex)
            {
                System.out.println("File incorrect");
                return null;
            }
            catch (RuntimeException ex)
            {
                System.out.println("Textfile size is incorrect, use 40 rows with 40 characters");
                return null;
            }
        }
        return mapLayout;
    }

    /**
     * Draw objects onto the panel from the ArrayList
     *
     * @param mapLayout ArrayList<String> with the mapLayout from the
     * LoadMapMethod
     */
    public void drawMap(ArrayList<String> mapLayout)
    {
        try
        {
            // X & Y Positions for the blocks
            int y = 0;
            int x = 0;
            int playerAmount = 1;
            // Rowcounter for reading the mapLayout
            int rowcount = 1;
            TVector2 size;
            // Velocity 0 since blocks don't move
            TVector2 velocity = new TVector2(0.0f, 0.0f);
            windowLocation hLocation = null;
            windowLocation vLocation = null;
            // Read all rows of the maplayout
            for (String row : mapLayout)
            {
                // Read every number on a row of the maplayout
                for (int c = 0; c <= row.length() - 1; c++)
                {
                    size = new TVector2(20f, 20f);
                    x = c * 20;
                    String type = row.substring(c, c + 1);
                    TVector2 position = new TVector2(x, y);
                    Server server = new Server();
                    if (position.getX() > 0 && position.getX() < (this.getSize().getWidth() / 2))
                    {
                        vLocation = windowLocation.WEST;
                    }
                    else if (position.getX() > (this.getSize().getWidth() / 2))
                    {
                        vLocation = windowLocation.EAST;
                    }
                    else if (position.getX() > 0 && position.getY() < (this.getSize().getHeight() / 2))
                    {
                        hLocation = windowLocation.NORTH;
                    }
                    else if (position.getY() > (this.getSize().getHeight() / 2))
                    {
                        hLocation = windowLocation.SOUTH;
                    }

                    //Check what type of block needs to be created from input
                    switch (type)
                    {
                        // Create undestructable block
                        case "0":
                        {
                            Block wall = new Block(0, false, null, position, velocity, new TVector2(25, 25), Color.GRAY);
                            this.addObject(wall);

                            break;
                        }
                        // Create white space
                        case "1":
                        {
                            break;
                        }
                        // Create block without powerup
                        case "2":
                        {
                            Block noPower = new Block(1, true, null, position, velocity, size, Color.YELLOW);
                            this.addObject(noPower);
                            break;
                        }
                        // Create block with powerup
                        case "3":
                        {
                            PowerUp power = new PowerUp(1, null);
                            power.getRandomPowerUpType();
                            Block withPower = new Block(10, true, power, position, velocity, size, Color.RED);
                            this.addObject(withPower);
                            break;
                        }
                        // Create horizontal paddle spawn
                        case "4":
                        {
                            // Add human player
                            if (playerAmount == 1) // 2 For bottom paddle to be the player paddle
                            {
                                size = new TVector2(100f, 20f);
                                User player = new User("Test9000", "Test10101", "Testmail@email.com", server);
                                Paddle horizontalPaddle = new Paddle(0, position, velocity, size, player, hLocation, Color.GREEN);
                                player.setPaddle(horizontalPaddle);
                                this.addObject(horizontalPaddle);
                                this.userList.add(player);
                                this.paddleList.add(horizontalPaddle);
                                playerAmount++;
                                System.out.println(horizontalPaddle.getWindowLocation());
                                break;
                            }
                            else // Add CPU player
                            {
                                size = new TVector2(100f, 20f);
                                CPU cpubot = new CPU("Computer" + playerAmount + "(easy)", Byte.MIN_VALUE, this);
                                Paddle horizontalPaddle = new Paddle(0, position, velocity, size, cpubot, hLocation, Color.BLUE);
                                cpubot.setMyPaddle(horizontalPaddle);
                                this.addObject(horizontalPaddle);
                                this.paddleList.add(horizontalPaddle);
                                this.botList.add(cpubot);
                                playerAmount++;
                                System.out.println(horizontalPaddle.getWindowLocation());
                                break;
                            }

                        }
                        // Create vertical paddle spawn
                        case "5":
                        {
                            size = new TVector2(20f, 100f);
                            CPU cpubot = new CPU("Computer" + playerAmount + "(easy)", Byte.MIN_VALUE, this);
                            Paddle verticalPaddle = new Paddle(0, position, velocity, size, cpubot, vLocation, Color.BLUE);
                            this.addObject(verticalPaddle);
                            this.paddleList.add(verticalPaddle);
                            this.botList.add(cpubot);
                            playerAmount++;
                            cpubot.setMyPaddle(verticalPaddle);
                            System.out.println(verticalPaddle.getWindowLocation());
                            break;
                        }

                        // Create a ball spawn
                        case "6":
                        {
                            size = new TVector2(15f, 15f);
                            velocity = generateRandomVelocity();
                            Ball ball = new Ball(null, position, velocity, size, this, Color.RED);
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
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        ArrayList<GameObject> drawList = new ArrayList<>();
        drawList.addAll(this.getObjectList());
        //Multiple for loops, order of drawing is wery wery importanté

        // Draw whitespace
        g.setColor(whiteSpace.getColor());
        g.fillRect((int) whiteSpace.getPosition().getX(),
                (int) whiteSpace.getPosition().getY(),
                (int) whiteSpace.getSize().getX(), (int) whiteSpace.getSize().getY());

        for (int i = drawList.size() - 1; i >= 0; i--)
        {
            GameObject o = drawList.get(i);
            //Draw a block
            if (o instanceof Block)
            {
                Block b = (Block) o;
                g.setColor(b.getColor());
                g.fillRect((int) b.getPosition().getX(), (int) b.getPosition().getY(), (int) b.getSize().getX(), (int) b.getSize().getY());
                g.setColor(Color.black);
                g.drawRect((int) b.getPosition().getX(), (int) b.getPosition().getY(), (int) b.getSize().getX(), (int) b.getSize().getY());
                drawList.remove(i);

            }
        }
        for (int i = drawList.size() - 1; i >= 0; i--)
        {
            GameObject o = drawList.get(i);
            //Draw a ball
            if (o instanceof Ball)
            {
                Ball b = (Ball) o;
                g.setColor(Color.red);
                g.fillOval((int) b.getPosition().getX(), (int) b.getPosition().getY(), (int) b.getSize().getX(), (int) b.getSize().getY());
                g.setColor(Color.white);
                g.drawOval((int) b.getPosition().getX(), (int) b.getPosition().getY(), (int) b.getSize().getX(), (int) b.getSize().getY());
            } //Draw a paddle
            else if (o instanceof Paddle)
            {
                Paddle p = (Paddle) o;
                g.setColor(p.getColor());
                g.fillRect((int) p.getPosition().getX(), (int) p.getPosition().getY(), (int) p.getSize().getX(), (int) p.getSize().getY());
                g.setColor(Color.white);
                g.drawRect((int) p.getPosition().getX(), (int) p.getPosition().getY(), (int) p.getSize().getX(), (int) p.getSize().getY());
            }
        }
        String scoreText = "";
        int ypos = 25;
        Font scoreFont = new Font("arial", Font.BOLD, 16);
        Font paddleFont = new Font("arial", Font.BOLD, 12);
        for (User u : this.userList)
        {
            g.setFont(scoreFont);
            g.setColor(Color.WHITE);
            scoreText = u.getUsername() + ": " + u.getPaddle().getScore();
            g.drawString(scoreText, 10, ypos);
            g.setFont(paddleFont);
            g.setColor(Color.BLACK);
            float posX = u.getPaddle().getMiddlePosition().getX() - (u.getUsername().length() * 3);
            g.drawString(u.getUsername(), (int) posX,
                    (int) (u.getPaddle().getMiddlePosition().getY() - 15f));
            ypos += 25;

        }
        for (CPU c : this.botList)
        {
            g.setFont(scoreFont);
            g.setColor(Color.WHITE);
            scoreText = c.getName() + ": " + c.getMyPaddle().getScore();
            g.drawString(scoreText, 10, ypos);
            g.setFont(paddleFont);
            g.setColor(Color.BLACK);
            float posX = c.getMyPaddle().getMiddlePosition().getX() - (c.getName().length() * 3);
            g.drawString(c.getName(), (int) posX,
                    (int) (c.getMyPaddle().getMiddlePosition().getY() - 15f));
            ypos += 25;

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
    public void run()
    {
        // Do while game is started
        while (inProgress)
        {
            long start, elapsed, wait;
            start = System.nanoTime();
            // calls update function on all objects
            tick();

            // Redraw objects on panel
            this.revalidate();
            repaint();

            elapsed = System.nanoTime() - start;
            wait = targetTime - elapsed / 10000;
            if (wait <= 0)
            {
                wait = 5;
            }

            try
            {
                gameLoopThread.sleep(wait);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        System.out.println("While exited");
        exitGame();
    }

    private void exitGame()
    {
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

    public void tick()
    {
        ArrayList<GameObject> objectsToRemove = new ArrayList<GameObject>();
        for (Ball b : ballList)
        {
            b.update();
            Rectangle r = new Rectangle((int) b.getMiddlePosition().getX(), (int) b.getMiddlePosition().getY(), 1, 1);
            for (GameObject s : objectList)
            {
                if (s instanceof Block)
                {
                    Block block = (Block) s;
                    if (block.isDestructable() == false)
                    {
                        if (r.intersects(s.getBounds()))
                        {
                            System.out.println("reset ball!");
                            b.setPosition(b.spawnPos);
                        }
                    }
                }
            }
            if (checkExitedBounds(b.getMiddlePosition()))
            {
                // SPELER IS AF
                // GET CLOSEST PADDLE
                System.out.println("Ball exited play.");
                objectsToRemove.add(b);
            }
        }
        for (CPU c : botList)
        {
            c.update();
        }
        for (Paddle p : paddleList)
        {
            p.update();
        }
        for (GameObject o : objectsToRemove)
        {
            removeObject(o);
        }
    }

    private boolean checkExitedBounds(TVector2 vector)
    {
        if (vector.getX() < 0)
        {
            return true;
        }
        if (vector.getY() < 0)
        {
            return true;
        }
        if (vector.getX() > window.getSize().width)
        {
            return true;
        }
        if (vector.getY() > window.getSize().height)
        {
            return true;
        }
        return false;
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        // Doesn't need to do anything
    }

    //Keypressed eventhandler
    @Override
    public void keyPressed(KeyEvent e)
    {
        //Get all objects from the game
        for (GameObject o : this.objectList)
        {
            // If object is a paddle
            if (o instanceof Paddle)
            {
                Paddle p = (Paddle) o;
                p.keyPressed(e.getKeyCode());
            }
        }
    }

    //Keyreleased eventhandler
    @Override
    public void keyReleased(KeyEvent e)
    {
        //Get all objects from the game
        for (GameObject o : this.objectList)
        {
            // If the object is a paddle
            if (o instanceof Paddle)
            {
                //Stop moving the paddle
                Paddle p = (Paddle) o;
                p.keyReleased(e.getKeyCode());
            }
        }
    }

    public TVector2 generateRandomVelocity()
    {
        Random rand = new Random();
        float x = generateRandomFloat(-Ball.maxSpeed + (Ball.maxSpeed / 8), Ball.maxSpeed - (Ball.maxSpeed / 8), rand);
        float y;
        if (x < 0)
        {
            y = Ball.maxSpeed - (x * -1);
        }
        else
        {
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
    private float generateRandomFloat(float min, float max, Random rand)
    {
        float finalFloat = 0f;
        while (finalFloat > -0.1f && finalFloat < 0.1f)
        {
            finalFloat = rand.nextFloat() * (max - min) + min;
        }
        return finalFloat;
    }
}
