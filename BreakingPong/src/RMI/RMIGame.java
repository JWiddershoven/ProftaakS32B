/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import Interfaces.IGame;
import Interfaces.IMap;
import Interfaces.IUser;
import Shared.Ball;
import Shared.CPU;
import Shared.GameObject;
import Shared.Map;
import Shared.Paddle;
import Shared.User;
import Shared.WhiteSpace;
import java.awt.image.BufferedImage;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author Mnesymne
 */
public class RMIGame implements IGame{
    
    private int id;
    private int gameTime;
    private boolean powerUps;
    private boolean inProgress = false;
    private Thread gameLoopThread;
    private final int FPS = 60;
    private final long targetTime = 1000 / FPS;
    private Map selectedmap;

    //private ArrayList<Map> selectedMaps;
    private ArrayList<IUser> userList;
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


    @Override
    public boolean leaveGame(IGame game , IUser user) throws RemoteException {
       if(this.userList.contains(user))
       {
           this.userList.remove(user);
           return true;
       }
       return false;
    }

    @Override
    public boolean kickPlayer(IUser user) throws RemoteException {
        if(this.userList.contains(user))
        {
            this.userList.remove(user);
            return true;
        }
        return false;        
    }

    @Override
    public void addMap(IMap map) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IGame joinGame(IGame game, IUser user) throws RemoteException {
       if(this.userList != null)
       {
       this.userList.add(user);
       }
       return this;
    }

    @Override
    public ArrayList<String> getPlayersInformation(IGame game) throws RemoteException {
       ArrayList<String> returnvalue = new ArrayList<>();
       if(this.userList != null)
       {
           for(IUser user : this.userList)
           {
               returnvalue.add(user.getPlayerInformation(user));
           }
       }
       return returnvalue;
    }
}
