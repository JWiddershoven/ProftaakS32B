/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import Interfaces.IGame;
import Interfaces.ILobby;
import Interfaces.IMap;
import Interfaces.IServer;
import Interfaces.IUser;
import Server.Lobby;
import Server.Server;
import Shared.User;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServerRMI implements IServer {

    private ArrayList<IUser> loggedInUsers = new ArrayList<>();
    private ArrayList<ILobby> currentLobbies = new ArrayList<>();

    @Override
    public boolean kickPlayer(IUser user) throws RemoteException {
       if(loggedInUsers != null && loggedInUsers.contains(user))
       {
           loggedInUsers.remove(user);
           return true;
       }
       return false;
    }

    @Override
    public ArrayList<String> getPlayersInformation(IGame game) throws RemoteException {
        return game.getPlayersInformation(game);
    }

    @Override
    public void addMap(IMap map) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean createLobby(String name, String Password, User Owner, Byte maxPlayers, IServer server) throws RemoteException {
       if(name != null && Password != null && Owner != null && maxPlayers != null && server != null)
       {
        Lobby lobby = new Lobby(currentLobbies.size() + 1 ,name,Password,Owner,maxPlayers,(Server)server);
       currentLobbies.add((ILobby)lobby);
       return true;
       }
       return false;
    }

    @Override
    public ILobby joinLobby(ILobby lobby,IUser user) throws RemoteException {
        if(lobby != null && user != null)
        {
            lobby.addUserToLobby(user, lobby);
            return lobby;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public boolean leaveLobby(ILobby lobby,IUser user) throws RemoteException {
        try {
            if(lobby != null)
            {
            lobby.leaveLobby(lobby, user);
            return true;
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ServerRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean removeLobby(ILobby lobby) throws RemoteException {
        if(lobby != null && currentLobbies != null && currentLobbies.size() >= 1)
        {
            if(currentLobbies.contains(lobby))
            {
                currentLobbies.remove(lobby);
                return true;
            }
            else return false;
        }
        else return false;
    }

    @Override
    public boolean sendChat(String message) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String> receiveChat() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String> getPlayerInformationFromLobby(ILobby lobby) throws RemoteException {
        return lobby.getPlayerInformationFromLobby(lobby);        
    }

    @Override
    public String getPlayerInformation(IUser user) throws RemoteException {
       return user.getPlayerInformation(user);
    }

    @Override
    public IGame joinGame(IGame game, IUser user) throws RemoteException {
        return game.joinGame(game, user);        
    }

    @Override
    public String getUsername(IUser user) {
        return user.getUsername(user);
    }

    @Override
    public boolean leaveGame(IGame game, IUser user) throws RemoteException {
        return game.leaveGame(game, user);
    }

    
    public boolean logout(IUser user) throws RemoteException {
        if(user != null)
        {
        this.loggedInUsers.remove(user);
        return true;
        }
        return false;
    }
    
    public boolean login(String username,String password) throws RemoteException
    {
        // do Database stuff
        return false;
    }

    @Override
    public boolean addUserToLobby(IUser user, ILobby lobby) {
        return lobby.addUserToLobby(user, lobby);
    }

  

    
}
