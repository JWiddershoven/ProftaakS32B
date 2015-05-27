/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Shared.Paddle;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Lorenzo
 */
public class GameClient extends Component implements KeyListener {
    
    private int gameId;
    private String username;
    
    public GameClient()
    {
        // Needs component for KeyListener
        addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Doesn't need to do anything
    }

    //Keypressed eventhandler
    @Override
    public void keyPressed(KeyEvent e) {
        
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_LEFT:  break;
            case KeyEvent.VK_RIGHT: break;
            case KeyEvent.VK_W: break;
            case KeyEvent.VK_D: break;
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
    
}
