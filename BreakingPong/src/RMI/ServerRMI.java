/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import Client.ClientGUI;
import Interfaces.IGame;
import Interfaces.ILobby;
import Interfaces.IMap;
import Interfaces.IServer;
import Interfaces.IUser;
import Server.Lobby;
import Server.Server;
import Shared.User;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerRMI extends UnicastRemoteObject implements IServer, Remote
{

    public ServerRMI() throws RemoteException
    {
        this.ID = 1;
    }

    private int ID;
    public ArrayList<IUser> loggedInUsers = new ArrayList<>();
    private ArrayList<ILobby> currentLobbies = new ArrayList<>();
    private ArrayList<IGame> currentGames = new ArrayList<>();

    public ArrayList<ILobby> getCurrentLobbies()
    {
        return this.currentLobbies;
    }

    @Override
    public boolean kickPlayer(String username) throws RemoteException
    {
        boolean returnValue = false;
        for (IUser user : loggedInUsers)
        {
            if (user.getUsername(user).equals(username))
            {
                loggedInUsers.remove(user);
                returnValue = true;
            }
        }
        return returnValue;
    }

    @Override
    public ArrayList<String> getPlayersInformation(int gameid) throws RemoteException
    {
        ArrayList<String> returnValue = new ArrayList<>();
        for (IGame game : currentGames)
        {
            if (game.getID() == gameid)
            {
                returnValue = game.getPlayersInformation(gameid);
            }
        }
        return returnValue;
    }

    @Override
    public void addMap(IMap map) throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean createLobby(String name, String Password, String Owner, Byte maxPlayers) throws RemoteException
    {
        if (name != null && Password != null && Owner != null && maxPlayers != null)
        {
            for (IUser user : loggedInUsers)
            {
                if (user.getUsername(user).equals(Owner))
                {
                    Lobby lobby = new Lobby(currentLobbies.size() + 1, name, Password, (User) user, maxPlayers);
                    currentLobbies.add((ILobby) lobby);
                    ClientGUI.joinedLobby = lobby;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public ILobby joinLobby(int lobbyid, String username) throws RemoteException
    {
        ILobby returnValue = null;
        for (ILobby lobby : currentLobbies)
        {
            if (lobby.getLobbyID() == lobbyid)
            {
                lobby.addUserToLobby(username, lobbyid);
                returnValue = lobby;
            }
        }
        return returnValue;
    }

    @Override
    public boolean leaveLobby(int lobbyid, String user) throws RemoteException
    {

        boolean check = false;
        for (ILobby lobby : currentLobbies)
        {
            if (lobby.getLobbyID() == lobbyid)
            {
                lobby.leaveLobby(lobbyid, user);
                check = true;
            }
        }
        return check;
    }

    @Override
    public boolean removeLobby(int lobbyid) throws RemoteException
    {
        for (ILobby lobby : currentLobbies)
        {
            if (lobby.getLobbyID() == lobbyid)
            {
                currentLobbies.remove(lobby);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean sendChat(String message) throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String> receiveChat() throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String> getPlayerInformationFromLobby(int lobbyid) throws RemoteException
    {
        ArrayList<String> returnValue = null;
        for (ILobby lobby : currentLobbies)
        {
            if (lobby.getLobbyID() == lobbyid)
            {
                returnValue = lobby.getPlayerInformationFromLobby(lobbyid);
            }
        }
        return returnValue;
    }

    @Override
    public String getPlayerInformation(String username) throws RemoteException
    {
        for (IUser user : loggedInUsers)
        {
            if (user.getUsername(user).equals(username))
            {
                return user.getPlayerInformation(username);
            }
        }
        return null;
    }

    @Override
    public IGame joinGame(int gameid, String username) throws RemoteException
    {
        IGame returnValue = null;
        for (IGame game : currentGames)
        {
            if (game.getID() == gameid)
            {
                returnValue = game.joinGame(gameid, username);
            }
        }
        return returnValue;
    }

    @Override
    public String getUsername(IUser user) throws RemoteException
    {
        return user.getUsername(user);
    }

    @Override
    public boolean leaveGame(int gameid, String username) throws RemoteException
    {
        boolean returnValue = false;
        for (IGame game : currentGames)
        {
            if (game.getID() == gameid)
            {
                returnValue = game.leaveGame(gameid, username);
            }
        }
        return returnValue;
    }

    public boolean logout(IUser user) throws RemoteException
    {
        if (user != null)
        {
            this.loggedInUsers.remove(user);
            return true;
        }
        return false;
    }

    public boolean login(String username, String password) throws RemoteException
    {
        // do Database stuff
        return false;
    }

    @Override
    public boolean addUserToLobby(String username, int lobbyid) throws RemoteException
    {
        boolean check = false;
        for (ILobby lobby : currentLobbies)
        {
            if (lobby.getLobbyID() == lobbyid)
            {
                for (IUser user : loggedInUsers)
                {
                    if (user.getUsername(user).equals(username))
                    {
                        lobby.addUserToLobby(username, lobbyid);
                        check = true;
                        break;
                    }
                }
            }
        }
        return check;
    }

    @Override
    public int getID() throws RemoteException
    {
        return ID;
    }

    @Override
    public int getLobbyID() throws RemoteException
    {
        return ID;
    }

    @Override
    public void moveLeft(int gameId, String username) throws RemoteException
    {
        for (int i = currentGames.size(); i > 0; i--)
        {
            if (currentGames.get(i).getID() == gameId)
            {
                currentGames.get(i).moveLeft(gameId, username);
            }
        }
    }

    @Override
    public void moveRight(int gameId, String username) throws RemoteException
    {
        for (int i = currentGames.size(); i > 0; i--)
        {
            if (currentGames.get(i).getID() == gameId)
            {
                currentGames.get(i).moveRight(gameId, username);
            }
        }
    }

    @Override
    public ArrayList<String> getOnlineUsers() throws RemoteException
    {
        ArrayList<String> onlineUsers = new ArrayList<>();
        
        for (IUser user : loggedInUsers)
        {
            onlineUsers.add(user.getPlayerInformation(user.getUsername(user)));
        }
        
        return onlineUsers;
    }

    @Override
    public ArrayList<ILobby> getAllLobbies() throws RemoteException
    {
         return this.currentLobbies;
    }

}
