/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import Server.CollisionChecker;
import Server.Server;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private Thread thread;
    private int FPS = 60;
    private long targetTime = 1000 / FPS;

    private ArrayList<Map> selectedMaps;
    private ArrayList<User> userList;
    private ArrayList<CPU> botList;
    private ArrayList<GameObject> objectList;
    private ArrayList<Ball> ballList;

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
        } else
        {
            this.inProgress = true;
        }
        return inProgress;
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
        this.selectedMaps = new ArrayList<>();
        this.setFocusable(true);
        addKeyListener(this);
    }

    /**
     * Adds a CPU player to the game
     *
     * @param botName value of botName as String
     * @param botDifficulty value of Difficulty as Byte
     */
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
        for (CPU bot : botList)
        {
            if (bot.getName().equals(botName))
            {
                botList.remove(bot);
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
     * Get all the Objects from the games object list
     *
     * @return ArrayList<GameObject>
     */
    public ArrayList<GameObject> getObjectList()
    {
        return this.objectList;
    }

    /**
     * Ends a game that currently is in progress. A game can't be ended if it
     * isn't in progress.
     */
    public void endGame()
    {
        this.inProgress = false;
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
            JFrame window = new JFrame();
            window.setSize(819, 848);
            window.setBackground(Color.white);
            window.setLocationRelativeTo(null);
            window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            //Draw the level from the input
            this.drawMap(mapLayout);
            //Add the drawn level to the window and then start the game
            window.setContentPane(this);
            window.setVisible(true);
            this.startGame();
        }

    }

    /**
     * Method to start the game and thread that move the objects
     */
    public void startGame()
    {
        inProgress = true;
        thread = new Thread(this);
        thread.start();
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
            } catch (FileNotFoundException ex)
            {

            } catch (IOException IOex)
            {

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
            } catch (IllegalArgumentException ifex)
            {
                System.out.println("File incorrect");
                return null;
            } catch (RuntimeException ex)
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
                    User player = new User("Test9000", "Test10101", "Testmail@email.com", server);
                    CPU cpu = new CPU("Bob", (byte) 1, this);
                    //Check what type of block needs to be created from input
                    switch (type)
                    {
                        // Create undestructable block
                        case "0":
                        {
                            Block wall = new Block(0, false, null, position, velocity, size, Color.gray);
                            this.addObject(wall);

                            break;
                        }
                        // Create white space
                        case "1":
                        {
                            // Don't do anything to create blank space
                            break;
                        }
                        // Create block without powerup
                        case "2":
                        {
                            Block noPower = new Block(1, true, null, position, velocity, size, Color.yellow);
                            this.addObject(noPower);
                            break;
                        }
                        // Create block with powerup
                        case "3":
                        {
                            PowerUp power = new PowerUp(1, null);
                            power.getRandomPowerUpType();
                            Block withPower = new Block(1, true, power, position, velocity, size, Color.red);
                            this.addObject(withPower);
                            break;
                        }
                        // Create horizontal paddle spawn
                        case "4":
                        {
                            // Add human player
                            if (playerAmount == 2)
                            {
                                size = new TVector2(100f, 20f);
                                Paddle horizontalPaddle = new Paddle(0, position, velocity, size, player, Paddle.windowLocation.SOUTH, Color.green);
                                this.addObject(horizontalPaddle);
                                break;
                            } else // Add CPU player
                            {
                                size = new TVector2(100f, 20f);
                                Paddle horizontalPaddle = new Paddle(0, position, velocity, size, cpu, Paddle.windowLocation.NORTH, Color.green);
                                this.addObject(horizontalPaddle);
                                playerAmount++;
                                break;

                            }

                        }
                        // Create vertical paddle spawn
                        case "5":
                        {
                            size = new TVector2(20f, 100f);
                            Paddle verticalPaddle = new Paddle(0, position, velocity, size, player, Paddle.windowLocation.EAST, Color.green);
                            this.addObject(verticalPaddle);
                            break;
                        }

                        // Create a ball spawn
                        case "6":
                        {
                            size = new TVector2(15f, 15f);
                            velocity = generateRandomVelocity();
                            Ball ball = new Ball(null, position, velocity, size);
                            this.addObject(ball);
                            break;
                        }
                    }
                }
                rowcount++;
                y += 20;
            }
        } catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        for (GameObject o : this.getObjectList())
        {
            //Draw a block
            if (o instanceof Block)
            {
                Block b = (Block) o;
                g.setColor(b.getColor());
                g.fillRect((int) b.getPosition().getX(), (int) b.getPosition().getY(), (int) b.getSize().getX(), (int) b.getSize().getY());
                g.setColor(Color.black);
                g.drawRect((int) b.getPosition().getX(), (int) b.getPosition().getY(), (int) b.getSize().getX(), (int) b.getSize().getY());
            } //Draw a paddle
            else if (o instanceof Paddle)
            {
                Paddle p = (Paddle) o;
                g.setColor(p.getColor());
                g.fillRect((int) p.getPosition().getX(), (int) p.getPosition().getY(), (int) p.getSize().getX(), (int) p.getSize().getY());
                g.setColor(Color.white);
                g.drawRect((int) p.getPosition().getX(), (int) p.getPosition().getY(), (int) p.getSize().getX(), (int) p.getSize().getY());
            } //Draw a ball
            else if (o instanceof Ball)
            {
                Ball b = (Ball) o;
                g.setColor(Color.red);
                g.fillOval((int) b.getPosition().getX(), (int) b.getPosition().getY(), (int) b.getSize().getX(), (int) b.getSize().getY());
                g.setColor(Color.white);
                g.drawOval((int) b.getPosition().getX(), (int) b.getPosition().getY(), (int) b.getSize().getX(), (int) b.getSize().getY());
            }
        }
    }

    @Override
    public void run()
    {
        long start, elapsed, wait;
        // Do while game is started

        for (GameObject o : this.objectList)
        {
            if (o instanceof Ball)
            {
                Ball b = (Ball) o;
                b.startBall();
            }
        }
        while (inProgress)
        {
            start = System.nanoTime();
            //Move objects
            tick();
            // Redraw objects on panel
            repaint();
            elapsed = System.nanoTime() - start;
            wait = targetTime - elapsed / 1000000;
            if (wait <= 0)
            {
                wait = 5;
            }

            try
            {
                thread.sleep(wait);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void tick()
    {
        for (GameObject o : this.objectList)
        {
            //Move all paddles
            if (o instanceof Paddle)
            {
                Paddle p = (Paddle) o;
                p.tick();
            }
        }
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
        System.out.println("Y");
        Boolean inCollisionLeft;
        Boolean inCollisionRight;
        //Get all objects from the game
        for (GameObject o : this.objectList)
        {
            // If object is a paddle
            if (o instanceof Paddle)
            {
                inCollisionLeft = false;
                inCollisionRight = false;
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
        float x = generateRandomFloat(-1f, 1f, rand);
        float y = generateRandomFloat(-1f,1f,rand);
        return new TVector2(x,y);
    }
    
    private float generateRandomFloat(float min, float max, Random rand)
    {
        float finalFloat = 0f;
        while (finalFloat == 0f)
            finalFloat = rand.nextFloat() * (max - min) + min;
        return finalFloat;
    }
}
