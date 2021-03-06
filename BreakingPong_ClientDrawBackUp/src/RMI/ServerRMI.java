/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import Client.ClientGUI;
import Helpers.DatabaseHelper;
import Helpers.LoggedinUser;
import Interfaces.IGame;
import Interfaces.ILobby;
import Interfaces.IMap;
import Interfaces.IServer;
import Interfaces.IUser;
import Server.Lobby;
import Server.Server;
import Shared.GameObject;
import Shared.User;
import fontys.observer.BasicPublisher;
import fontys.observer.RemotePropertyListener;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerRMI extends UnicastRemoteObject implements IServer, Remote
{
    public static BasicPublisher publisher;
    public ServerRMI() throws RemoteException
    {
        this.publisher = new BasicPublisher(new String[]{"getBlocks", "getBalls", "getPaddles", "getTime", "getScore"});
        this.ID = 1;
        currentGames.add(this); // Geen create game methode ???
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                
            }
        }, 0, 1500);
    }

    private int ID;
    public ArrayList<IUser> loggedInUsers = new ArrayList<>();
    private ArrayList<ILobby> currentLobbies = new ArrayList<>();
    private ArrayList<IGame> currentGames = new ArrayList<>();

    /**
     * Returns a list of current lobbies.
     * @return an ArrayList with ILobbies.
     */
    public ArrayList<ILobby> getCurrentLobbies()
    {
        return this.currentLobbies;
    }

    /**
     * Kicks a player from a game.
     * @param username the username of the user that will get kicked.
     * @param lobbyID the lobbyID of the lobby.
     * @return true if the user was removed from the game, otherwise false.
     * @throws RemoteException 
     */
    @Override
    public boolean kickPlayer(String username, int lobbyID) throws RemoteException
    {
        boolean kick = false;
        for (ILobby lobby : currentLobbies)
        {
            if (lobby.getLobbyID() == lobbyID)
            {
                lobby.leaveLobby(lobbyID, username);
                kick = true;
            }
        }
        return kick;
    }

    /**
     * Collects the information from each player in an instance of a game.
     * @param gameid the ID of the game.
     * @return an ArrayList containing the information of each player in the specified game.
     * @throws RemoteException 
     */
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

    /**
     * Adds a map to the server.
     * @param map The map object.
     * @throws RemoteException 
     */
    @Override
    public void addMap(IMap map) throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Creates a new lobby with the specified parameters.
     * @param name the lobby name, cannot be empty.
     * @param Password the lobby password.
     * @param Owner the host of the lobby, cannot be empty.
     * @param maxPlayers the maximum amount of players in the lobby. (2/4)
     * @return true if a new lobby has been created, otherwise false.
     * @throws RemoteException 
     */
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

    /**
     * Attempts to put a user in a lobby.
     * @param lobbyid the lobbyid of the lobby that the user wants to join.
     * @param username the username of the user that wants to join a lobby.
     * @return a lobby object if successfull, otherwise null;
     * @throws RemoteException 
     */
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

    /**
     * Attempts to remove a user from a lobby.
     * @param lobbyid the lobbyid of the lobby that the user wishes to leave.
     * @param user the username of the user that wishes to leave a lobby.
     * @return true if successfull, otherwise false.
     * @throws RemoteException 
     */
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

    /**
     * Attempts to delete a lobby.
     * @param lobbyid the lobbyid of the lobby that is going to be removed.
     * @return true if successfull, otherwise false.
     * @throws RemoteException 
     */
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

    /**
     * Sends a chat message 
     * @param message The message that the user wants to send, cannot be empty.
     * @return true if the message was successfully sent, otherwise false.
     * @throws RemoteException 
     */
    @Override
    public boolean sendChat(String message) throws RemoteException
    {
        if (message == null || message.isEmpty())
        {
            throw new IllegalArgumentException("Message cannot be null or empty!");
        }
        return false;
    }

    /**
     * Receives the chat messages.
     * @return an ArrayList of chat messages.
     * @throws RemoteException 
     */
    @Override
    public ArrayList<String> receiveChat() throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Collects the user information from each user in the specified lobby.
     * @param lobbyid the lobbyid 
     * @return an ArrayList containing the information of each player from that lobby.
     * @throws RemoteException 
     */
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

    /**
     * Collects the information of a single user.
     * @param username the username of the user.
     * @return a String containing the information of that user.
     * @throws RemoteException 
     */
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

    /**
     * Attempts to put a player in a game.
     * @param gameid the gameID.
     * @param username the username of the user.
     * @return the IGame object if successfull, otherwise null.
     * @throws RemoteException 
     */
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

    /**
     * Gets the username of a user.
     * @param user the user.
     * @return the username of the user.
     * @throws RemoteException 
     */
    @Override
    public String getUsername(IUser user) throws RemoteException
    {
        return user.getUsername(user);
    }

    /**
     * Attempts to remove a player from a game.
     * @param gameid the gameID.
     * @param username the username of the user.
     * @return true if successfull, otherwise false.
     * @throws RemoteException 
     */
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

    /**
     * Method used to logout a user.
     * @param user the user.
     * @return true if successfull, otherwise false.
     * @throws RemoteException 
     */
    public boolean logout(IUser user) throws RemoteException
    {
        if (user != null)
        {
            this.loggedInUsers.remove(user);
            return true;
        }
        return false;
    }

    /**
     * Attempts to login a user using the paramaters.
     * @param username the username of the user who is trying to login.
     * @param password the password of the user who is trying to login.
     * @return true if successfull, otherwise false.
     * @throws RemoteException 
     */
    public boolean login(String username, String password) throws RemoteException
    {
        LoggedinUser user = null;
        try
        {
            user = DatabaseHelper.loginUser(username, password);
        } catch (IllegalArgumentException exc)
        {
            Logger.getLogger(ServerRMI.class.getName()).log(Level.SEVERE, null, exc);
        }
        
        if (user != null && user.getLoggedIn())
        {
            return true;
        }

        return false;
    }

    /**
     * Attempts to add a user to a lobby.
     * @param username the username of the user.
     * @param lobbyid the lobbyid.
     * @return true if successfull, otherwise false.
     * @throws RemoteException 
     */
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

    /**
     * Gets the ID of the server.
     * @return the ID of the server.
     * @throws RemoteException 
     */
    @Override
    public int getID() throws RemoteException
    {
        return ID;
    }

    /**
     * Gets the lobbyID.
     * @return the lobbyID.
     * @throws RemoteException 
     */
    @Override
    public int getLobbyID() throws RemoteException
    {
        return ID;
    }

    /**
     * Moves the paddle of a user to the left.
     * @param gameId the gameID.
     * @param username the username of the user.
     * @throws RemoteException 
     */
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

    /**
     * Moves the paddle of a user to the right.
     * @param gameId the gameID.
     * @param username the username of the user.
     * @throws RemoteException 
     */
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

    /**
     * Returns a list containing every online user.
     * @return an ArrayList containing online users.
     * @throws RemoteException 
     */
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

    /**
     * Returns a list of all lobbies.
     * @return a list of all lobbies.
     * @throws RemoteException 
     */
    @Override
    public ArrayList<ILobby> getAllLobbies() throws RemoteException
    {
        return this.currentLobbies;
    }

    /**
     * Returns a list of all gameobjects.
     * @param gameId the gameID.
     * @return an ArrayList containing every single gameobject.
     * @throws RemoteException 
     */
    @Override
    public ArrayList<GameObject> getAllGameObjects(int gameId) throws RemoteException
    {
        for (int i = currentGames.size(); i > 0; i--)
        {
            if (currentGames.get(i).getID() == gameId)
            {
                return currentGames.get(i).getAllGameObjects(gameId);
            }
        }
        return null;
    }

    /**
     * Returns a list of all balls.
     * @param gameId the gameID.
     * @throws RemoteException 
     */
    @Override
    public void getAllBalls(int gameId) throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Returns a list of all changed gameobjects.
     * @param gameId the gameID.
     * @return an ArrayList containing every single changed gameobject.
     * @throws RemoteException 
     */
    @Override
    public ArrayList<GameObject> getChangedGameObjects(int gameId) throws RemoteException
    {
        for (int i = currentGames.size(); i > 0; i--)
        {
            if (currentGames.get(i).getID() == gameId)
            {
                return currentGames.get(i).getChangedGameObjects(gameId);
            }
        }
        return new ArrayList<GameObject>();
    }

    /**
     * Returns a list of all deleted objects.
     * @param gameId the gameID.
     * @return an ArrayList containing every single deleted gameobject.
     * @throws RemoteException 
     */
    @Override
    public ArrayList<GameObject> getRemovedGamesObjects(int gameId) throws RemoteException
    {
        for (int i = currentGames.size(); i > 0; i--)
        {
            if (currentGames.get(i).getID() == gameId)
            {
                return currentGames.get(i).getRemovedGamesObjects(gameId);
            }
        }
        return new ArrayList<GameObject>();
    }

    @Override
    public void addListener(RemotePropertyListener listener, String property) throws RemoteException {
        this.publisher.addListener(listener, property);
        System.out.println("Listener addded");
    }

    @Override
    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException {
        this.publisher.removeListener(listener, property);
        System.out.println("Listener removed");
    }

    @Override
    public void createGame(int id, int gameTime, boolean powerUps, ArrayList<IUser> players) throws RemoteException {
        for( int i = currentLobbies.size(); i > 0 ; i--)
        {
            if(currentLobbies.get(i).getLobbyID() == id)
            {
                currentLobbies.get(i).createGame(id, gameTime, powerUps, players);
                System.out.println("GameCreated");
            }
        }
    }
}
