/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import Interfaces.IGame;
import Interfaces.IMap;
import Interfaces.IUser;
import Server.Game;
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
 * VOOR DE SERVER
 *
 * @author Mnesymne
 */
public class RMIGame implements IGame {

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

    
    
    // <editor-fold defaultstate="collapsed" desc="- - - - - - - - - - - I n t e r f a c e s - - - - - - - - - - -">>
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
    public boolean kickPlayer(String username) throws RemoteException {

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
    public IGame joinGame(int gameid, String username) throws RemoteException {
        boolean check = false;
        IUser Juser = null;
        for (IUser user : userList) {
            if (!user.getUsername(user).equals(username)) {
                Juser = user;
                check = false;
            }
            else {
                check = true;
            }
        }
        if (!check) {
            userList.add(Juser);
            return this;
        }
        return null;
    }

    @Override
    public ArrayList<String> getPlayersInformation(int game) throws RemoteException {
        ArrayList<String> returnvalue = new ArrayList<>();
        if (this.userList != null) {
            for (IUser user : this.userList) {
                returnvalue.add(user.getPlayerInformation(user.getUsername(user)));
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
            if (p.getPlayer().getUsername().equals(username)) {
                p.Move(Paddle.Direction.LEFT);
                break;
            }
        }
    }

    @Override
    public void moveRight(int gameId, String username) throws RemoteException {
        for (int i = paddleList.size(); i > 0; i--) {
            Paddle p = paddleList.get(i);
            if (p.getPlayer().getUsername().equals(username)) {
                p.Move(Paddle.Direction.RIGHT);
                break;
            }
        }
    }

    // </editor-fold>
    
    
    public RMIGame(int id, int gameTime, boolean powerUps, ArrayList<User> players) {
        this.id = id;
        this.gameTime = gameTime;
        this.powerUps = powerUps;
        this.botList = new ArrayList<>();
        this.userList = new ArrayList<>();
        this.objectList = new ArrayList<>();
        this.ballList = new ArrayList<>();
        this.paddleList = new ArrayList<>();
        
        generateBotPlayers(players);
    }

    private void generateBotPlayers(ArrayList<User> players) {
        try {
            if (players.get(0) != null) {
                player1 = players.get(0);
            }
        }
        catch (IndexOutOfBoundsException ex) {
            cpu1 = new CPU("Bot1", (byte) 1);
            cpu1.setMyPaddle(P1Paddle);
        }

        try {
            if (players.get(1) != null) {
                player2 = players.get(1);
            }
        }
        catch (IndexOutOfBoundsException ex) {
            cpu2 = new CPU("Bot2", (byte) 1);
        }
        try {
            if (players.get(2) != null) {
                player3 = players.get(2);
            }
        }
        catch (IndexOutOfBoundsException ex) {
            cpu3 = new CPU("Bot3", (byte) 1);
        }
        try {
            if (players.get(3) != null) {
                player4 = players.get(3);
            }
        }
        catch (IndexOutOfBoundsException ex) {
            cpu4 = new CPU("Bot4", (byte) 1);
        }
    }

}
