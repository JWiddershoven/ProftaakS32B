/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import Interfaces.ILobby;
import Interfaces.IUser;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mnesymne
 */
public class RMILobby implements ILobby, Serializable {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public IUser getOwner() {
        return owner;
    }

    public void setOwner(IUser owner) {
        this.owner = owner;
    }

    public byte getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(byte maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    private String name;
    private String password;
    private IUser owner;
    private int gameTime = 300;
    private byte maxPlayers;
    private final transient ServerRMI server;
    private transient RMIGame game;
    private ArrayList<IUser> joinedPlayers = new ArrayList<>();

    public RMILobby(ServerRMI pServer) {
        this.server = pServer;
        Thread t = new Thread(new Runnable() {
            Boolean running = true;

            @Override
            public void run() {
                while (running) {
                    try {
                        if (joinedPlayers.size() == maxPlayers) {
                            System.out.println("All players joined, starting game in 3 seconds");
                            Thread.sleep(3000);
                            createGame(id, gameTime, true);
                            running = false;
                        }
                        Thread.sleep(1000);
                    } catch (RemoteException ex) {
                        Logger.getLogger(RMILobby.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(RMILobby.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    @Override
    public boolean leaveLobby(int lobbyid, String username) throws RemoteException {
        
        if (game != null)
        {
            if (!game.leaveGame(game.getID(), username))
            {
                throw new IllegalArgumentException("ERROR: De speler kan niet worden verwijderd uit de game!");
            }
        }
        
        for (IUser user : joinedPlayers)
        {
            if (user.getUsername(user).equals(username))
            {
                joinedPlayers.remove(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean sendChat(int lobbyId, String message) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String> receiveChat() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String> getPlayerInformationFromLobby(int lobbyid) throws RemoteException {
        ArrayList<String> returnvalue = new ArrayList<String>();
        if (joinedPlayers != null && !joinedPlayers.isEmpty()) {
            for (IUser user : joinedPlayers) {
                returnvalue.add(user.getPlayerInformation(user.getUsername(user)));
            }
        }
        return returnvalue;
    }

    @Override
    public boolean addUserToLobby(String username, int lobbyid) throws RemoteException {
        boolean Breturn = false;
        if (joinedPlayers == null) {
            joinedPlayers = new ArrayList<>();
        }

        for (IUser user : joinedPlayers) {
            if (user.getUsername(user).equals(username)) {
                return false;
                //Breturn = false;
            }
        }

        for (IUser user : server.loggedInUsers) {
            if (user.getUsername(user).equals(username)) {
                joinedPlayers.add(user);
                Breturn = true;
            }
        }
        return Breturn;
    }

    @Override
    public int getLobbyID() {
        return this.id;
    }

    @Override
    public String getOwner(int lobbyid) throws RemoteException {
        if (this.owner == null) {
            return "No owner!";
        }
        return this.owner.getUsername(null);
    }

    @Override
    public void createGame(int id, int gameTime, boolean powerUps) throws RemoteException {
        Thread gamethread = new Thread(new Runnable() {
            @Override
            public void run() {
            RMIGame newGame = new RMIGame(id, gameTime, powerUps);
            newGame.settUserList(joinedPlayers);
            game = newGame;
            server.currentGames.add(game);
            game.loadMap("src/RMI/test4x4 - Markt.txt");
            System.out.println("Loaded map.");
            game.StartGame();
            System.out.println("Start game.");
            }
        });
        gamethread.start();
    }

    @Override
    public void setNextOwner() throws RemoteException {
        try {
            this.owner = this.joinedPlayers.get(0);
        } catch (Exception ex) {
            Logger.getLogger(RMILobby.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public String toString() {
        String returnString = id + " - ";
        try {
            if (this.name != null) {
                returnString += this.name + " - ";
            }
            if (this.owner != null) {
                try {
                    returnString += " owner: " + this.owner.getUsername(null) + " ";
                } catch (RemoteException ex) {
                    Logger.getLogger(RMILobby.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (this.joinedPlayers != null) {
                returnString += this.joinedPlayers.size() + "/";
            }
            returnString += this.maxPlayers;
        } catch (Exception ex) {
            Logger.getLogger(RMILobby.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnString;
    }
}
