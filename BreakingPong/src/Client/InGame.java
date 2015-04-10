/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Jordi
 */
public class InGame extends JPanel implements Runnable{
    
    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1680;
    
    private Thread thread;
    private boolean isRunning = false;
    private int FPS = 60;
    private long targetTime = 1000 / FPS;
    
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("BreakingPong");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(new InGame(), BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public InGame()
    {
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        start();
    }
    public void start()
    {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }
    @Override
    public void run()
    {
        long start, elapsed, wait;
        
        while(isRunning)
        {
            start = System.nanoTime();
            
            tick();
            repaint();
            
            elapsed = System.nanoTime() - start;
            wait = targetTime - elapsed / 1000000;
            if(wait <= 0)
            {
                wait = 5;
            }
            
            try
            {
                Thread.sleep(wait);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    private void tick()
    {
        System.out.println("Running");
    }
}
