/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Shared.Block;
import Shared.Paddle;
import Shared.PowerUp;
import Shared.TVector2;
import Shared.User;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Jordi
 */
public class GenerateMap extends JFrame {

    
    public GenerateMap()
    {
       loadGame(); 
    }
            
    public void loadGame()
    {
        this.setSize(819,848);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GameGraphics graphics = new GameGraphics();
        graphics.loadMapFile();
        this.setContentPane(graphics);
    }
    
    class GameGraphics extends JPanel
    {
        ArrayList<Block> blockList = new ArrayList<>();
        ArrayList<Paddle> paddleList = new ArrayList<>();
        public GameGraphics()
        {
            this.setPreferredSize(new Dimension(800,800));
        }
        
        public void loadMapFile()
        {
            File file = null;
            JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.home")));
            chooser.setDialogTitle("Select mapfile");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Textfile", "txt");
            chooser.setFileFilter(filter);
            int code = chooser.showOpenDialog(this);
            try
            {
                if (code == JFileChooser.APPROVE_OPTION)
                {
                    File selectedFile = chooser.getSelectedFile();
                    String fileName = selectedFile.getName();
                    FileInputStream fis = new FileInputStream(selectedFile);
                    InputStreamReader in = new InputStreamReader(fis, Charset.forName("UTF-8"));
                    char[] buffer = new char[(int) selectedFile.length()];
                    int n = in.read(buffer);
                    String text = new String(buffer, 0, n).replaceAll("\\s+", "");
                    in.close();
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
                    ArrayList<String> mapLayout = new ArrayList<>();
                    for (int r = 0; r < mapDesign.length; r++)
                    {
                        String row = new String();
                        for (String mapDesign1 : mapDesign[r])
                        {
                            row = row + mapDesign1;
                        }
                        mapLayout.add(row);
                    }
                    int rowcount = 1;
                    int y = 0;
                    int x = 0;
                    TVector2 size;
                    TVector2 velocity = new TVector2(0.0f, 0.0f);
                    for (String row : mapLayout)
                    {
                        for (int c = 0; c <= row.length() -1; c++)
                        {
                            size = new TVector2(20f, 20f);
                            x = c * 20;
                            String type = row.substring(c,c +1);
                            TVector2 position = new TVector2(x,y);
                            User player = new User("Test", "Test", "Testmail", null);
                            switch (type)
                            {
                                // Create undestructable block
                                case "0":
                                {
                                    Block wall = new Block(0, false, null,position, velocity, size, Color.gray);
                                    blockList.add(wall);
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
                                    blockList.add(noPower);
                                    break;
                                }
                                // Create block with powerup
                                case "3":
                                {
                                    PowerUp power = new PowerUp(1, null);
                                    power.getRandomPowerUpType();
                                    Block withPower = new Block(1, true, power , position, velocity, size, Color.red);
                                    blockList.add(withPower);
                                    break;
                                }
                                // Create horizontal paddle spawn
                                case "4":
                                {
                                    size = new TVector2(100f,20f);
                                    Paddle horizontalPaddle = new Paddle(0, position, velocity, size, player, Paddle.windowLocation.WEST, Color.green);
                                    paddleList.add(horizontalPaddle);
                                    break;
                                }
                                // Create vertical paddle spawn
                                case "5":
                                {
                                    size = new TVector2(20f, 100f);
                                    Paddle verticalPaddle = new Paddle(0, position, velocity, size, player, Paddle.windowLocation.NORTH, Color.green);
                                    paddleList.add(verticalPaddle);
                                    break;
                                }
                            }
                        }
                        rowcount++;
                        y+= 20;
                    }
                }
            }
            catch(Exception ex)
            {
                System.out.println(ex.getMessage());
            }
        }
        @Override
        public void paintComponent(Graphics g)
        {
            blockList.stream().forEach((b) ->
            {
                g.setColor(b.getColor());
                g.fillRect((int)b.getPosition().getX(), (int)b.getPosition().getY(), (int)b.getSize().getX(), (int)b.getSize().getY());
                g.setColor(Color.black);
                g.drawRect((int)b.getPosition().getX(), (int)b.getPosition().getY(), (int)b.getSize().getX(), (int)b.getSize().getY());
            });
            
            paddleList.stream().forEach((p) ->
            {
                g.setColor(p.getColor());
                g.fillRect((int)p.getPosition().getX(), (int)p.getPosition().getY(), (int)p.getSize().getX(), (int)p.getSize().getY());
                g.setColor(Color.black);
                g.drawRect((int)p.getPosition().getX(), (int)p.getPosition().getY(), (int)p.getSize().getX(), (int)p.getSize().getY());
            });
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GenerateMap().setVisible(true);
            }
        });
    }
}    
