/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lorenzo
 */
public class GameClient extends Component implements KeyListener {

    private int gameId;
    private String username;

    public GameClient() {
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
        try {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    ClientGUI.CurrentSession.getServer().moveLeft(gameId, username);
                    break;
                case KeyEvent.VK_RIGHT:
                    ClientGUI.CurrentSession.getServer().moveRight(gameId, username);
                    break;
                case KeyEvent.VK_A:
                    ClientGUI.CurrentSession.getServer().moveLeft(gameId, username);
                    break;
                case KeyEvent.VK_D:
                    ClientGUI.CurrentSession.getServer().moveRight(gameId, username);
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

    
    
    
    // TODO: DRAW
}
