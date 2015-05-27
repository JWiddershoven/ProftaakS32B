/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Server.Game;
import Shared.Ball;
import Shared.Block;
import Shared.CPU;
import Shared.GameObject;
import Shared.Paddle;
import Shared.TVector2;
import Shared.User;
import Shared.WhiteSpace;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import static java.lang.System.gc;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Lorenzo
 */
public class GameClient extends JPanel implements Runnable, KeyListener {

    private int gameId;
    private String localUsername;

    private Thread drawThread;
    private boolean inProgress = false;

    private ArrayList<User> userList;
    private ArrayList<CPU> botList;
    public ArrayList<GameObject> objectList;
    private WhiteSpace whiteSpace;
    private JFrame window;
    private final int FPS = 60;
    private final long targetTime = 1000 / FPS;

    private BufferedImage BallImage, DestroyImage, normalBlockImage, PowerUpImage, PaddleImage, WhiteSpaceImage;

    public GameClient() {
        // Needs component for KeyListener
        userList = new ArrayList<>();
        botList = new ArrayList<>();
        objectList = new ArrayList<>();
        addKeyListener(this);
    }

    // <editor-fold defaultstate="collapsed" desc="- - - - - - - - - - - Key pressed - - - - - - - - - - -">>
    @Override
    public void keyTyped(KeyEvent e) {
        // Doesn't need to do anything
    }

    //Keypressed eventhandler
    @Override
    public void keyPressed(KeyEvent e) {
        try {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    ClientGUI.CurrentSession.getServer().moveLeft(gameId, localUsername);
                    break;
                case KeyEvent.VK_RIGHT:
                    ClientGUI.CurrentSession.getServer().moveRight(gameId, localUsername);
                    break;
                case KeyEvent.VK_A:
                    ClientGUI.CurrentSession.getServer().moveLeft(gameId, localUsername);
                    break;
                case KeyEvent.VK_D:
                    ClientGUI.CurrentSession.getServer().moveRight(gameId, localUsername);
                    break;
            }
        }
        catch (RemoteException ex) {
            Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Keyreleased eventhandler
    @Override
    public void keyReleased(KeyEvent e) {
//        //Get all objects from the game
//        for (GameObject o : this.objectList) {
//            // If the object is a paddle
//            if (o instanceof Paddle) {
//                //Stop moving the paddle
//                Paddle p = (Paddle) o;
//                p.keyReleased(e.getKeyCode());
//            }
//        }
    }

    // </editor-fold>
    /**
     * Create the window and thread to draw objects.
     *
     * @param windowSize X, Y
     * @return
     */
    public Thread setupGame(int[] windowSize) {
        try {
            //Create the window
            window = new JFrame();
            window.setSize(windowSize[0], windowSize[1]);
            window.setBackground(Color.white);
            window.setLocationRelativeTo(null);

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
            inProgress = true;
            drawThread = new Thread(this);
            drawThread.start();
        }
        catch (HeadlessException | IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        inProgress = true;
        drawThread = new Thread(this);
        return drawThread;
    }

    @Override
    public void run() {
        // Do while game is started
        while (inProgress) {
            long start, elapsed, wait;
            start = System.nanoTime();

            // Redraw objects on panel
            this.revalidate();
            repaint();

            elapsed = System.nanoTime() - start;
            wait = targetTime - elapsed / 10000;
            if (wait <= 0) {
                wait = 5;
            }

            try {
                drawThread.sleep(wait);
            }
            catch (Exception e) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        System.out.println("While exited");
        exitGame();
    }

    private void exitGame() {
        drawThread.interrupt();
        drawThread = null;
        this.botList.clear();
        this.objectList.clear();
        this.userList.clear();
        gc();
        try {
            ClientGUI.CurrentSession.getServer().leaveGame(gameId, localUsername);
        }
        catch (RemoteException ex) {
            Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Exited game");
    }

    @Override
    public void paintComponent(Graphics g) {
        try {
            ArrayList<GameObject> drawList = new ArrayList<>();
            drawList.addAll(this.objectList);

            //Multiple for loops, order of drawing is wery wery importanté        
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
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
