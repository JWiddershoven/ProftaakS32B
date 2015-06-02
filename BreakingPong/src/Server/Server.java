/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Helpers.DatabaseHelper;
import Interfaces.IGame;
import Interfaces.ILobby;
import Interfaces.IMap;
import Interfaces.IServer;
import Interfaces.IUser;
import RMI.RMILobby;
import RMI.ServerRMI;
import Shared.GameObject;
import Shared.User;
import fontys.observer.RemotePropertyListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The server class
 *
 * @author Mnesymne
 * @Deprecated NIET MEER GEBRUIKEN VOOR RMI
 */

@Deprecated
public class Server extends UnicastRemoteObject  implements IServer
{
//
//    /*
//     List with all the current onlineUsers
//     */
//    private final List<User> onlineUsers;
//
//    /*
//     List with all the current lobby's
//     */
//    private List<Lobby> lobbys;
//
//    /*
//     List with all the current onlineUsers
//     */
//    public ArrayList<IUser> getOnlineUsers() {
//        return this.loggedInUsers;
//    }
//
//    /*
//     List with all the current lobby's
//     */
//    public List<Lobby> getLobbys() {
//        return lobbys;
//    }
//
//    private Lobby getLobby(int Id) {
//        for (Lobby lobby : lobbys) {
//            if (lobby.getId() == Id) {
//                return lobby;
//            }
//        }
//
//        return null;
//    }
//
//    private User getUser(String username) {
//        User owner = null;
//        for (User u : this.onlineUsers) {
//            if (u.getUsername().equals(username)) {
//                owner = u;
//            }
//        }
//
//        return owner;
//    }
//
//    private int getNewLobbyId() {
//        return this.lobbys.size() + 1;
//    }
//
//    /**
//     * The maps that are on server
//     */
//    private final ObservableList<Map> mappenObserableList = FXCollections.observableArrayList();
//
//    /**
//     * The maps that are on server
//     *
//     * @return ObservableList of type Map
//     */
//    public ObservableList<Map> getMappenObservableList() {
//        return mappenObserableList;
//    }
//
//    /**
//     * Constructor
//     *
//     * @throws java.rmi.RemoteException
//     */
//    public Server() throws RemoteException {
//        onlineUsers = new ArrayList<>();
//        lobbys = new ArrayList<>();
//
////TODO: hieronder verwijderen als mappen inlezen werkt.
//        mappenObserableList.add(new Map("testmap", 4, new int[10][10]));
//    }
//
//    /**
//     * Checks if the selected lobby exists and the lobby is not full and checks
//     * if there is a password and checks if the password is correct
//     *
//     * @param lobby The lobby the user wants to join.
//     * @return TRUE when successfully joined lobby.
//     */
//    public boolean JoinLobby(Lobby lobby) {
//
//        if (lobby == null) {
//            throw new IllegalArgumentException("Lobby is null!");
//        }
//
//        if (!this.lobbys.contains(lobby)) {
//            this.lobbys.add(lobby);
//            System.out.println("Joined lobby!");
//            return true;
//        } else {
//            throw new IllegalArgumentException("Lobby already exists!");
//        }
//    }
//
//    /**
//     * Logs the current user off
//     *
//     * @param user The user
//     */
//    public void LogOut(User user) {
//
//        if (user == null) {
//            throw new IllegalArgumentException("User is null!");
//        }
//
//        if (this.onlineUsers.contains(user)) {
//            this.onlineUsers.remove(user);
//            System.out.println(user.getUsername() + " logged out!");
//        } else {
//            throw new IllegalArgumentException("User does not exist!");
//        }
//
//    }
//
//    /**
//     * Add a user to the online user collection
//     *
//     * @param user The user that logged in
//     */
//    public void addUser(User user) {
//        if (user == null) {
//            throw new IllegalArgumentException();
//        }
//
//        onlineUsers.add(user);
//    }
//
//    /**
//     * Delete the map from the list of maps.
//     *
//     * @param map The map that will be deleted from the list of maps, cannot be
//     * null.
//     */
//    public void deleteMap(Map map) {
//        if (map != null && mappenObserableList.contains(map)) {
//            this.mappenObserableList.remove(map);
//        } else {
//            throw new IllegalArgumentException("Map cannot be null and map must exist.");
//        }
//    }

    //----------------------RMI---------------------
    private int ID;
    public ArrayList<IUser> loggedInUsers;
    private ArrayList<ILobby> currentLobbies;
    private ArrayList<IGame> currentGames;

    public Server(int port) throws RemoteException {
        super(port);
        this.loggedInUsers = new ArrayList<>();
        this.currentGames = new ArrayList<>();
        this.currentLobbies = new ArrayList<>();
    }

    public Server() throws RemoteException {
        this.loggedInUsers = new ArrayList<>();
        this.currentGames = new ArrayList<>();
        this.currentLobbies = new ArrayList<>();
    }

    @Override
    public boolean kickPlayer(String username, int lobbyID) throws RemoteException {
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

    @Override
    public ArrayList<String> getPlayersInformationInGame(int gameid) throws RemoteException {
        ArrayList<String> returnValue = new ArrayList<>();
        for (IGame game : currentGames) {
            if (game.getID() == gameid) {
                returnValue = game.getPlayersInformationInGame(gameid);
            }
        }
        return returnValue;
    }

    @Override
    public void addMap(IMap map) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean createLobby(String name, String Password, String Owner, Byte maxPlayers) throws RemoteException {
//        if (name != null && Password != null && Owner != null && maxPlayers != null) {
//            for (IUser user : loggedInUsers) {
//                if (user.getUsername(user).equals(name)) {
//                    Lobby lobby = new Lobby(currentLobbies.size() + 1, name, Password, (User) user, maxPlayers);
//                    currentLobbies.add((ILobby) lobby);
//                    return true;
//                }
//            }
//        }
        return true;
    }

    @Override
    public ILobby joinLobby(int lobbyid, String username) throws RemoteException {
        ILobby returnValue = null;
        for (ILobby lobby : currentLobbies) {
            if (lobby.getLobbyID() == lobbyid) {
                lobby.addUserToLobby(username, lobbyid);
                returnValue = lobby;
            }
        }
        return returnValue;
    }

    @Override
    public boolean leaveLobby(int lobbyid, String user) throws RemoteException {

        boolean check = false;
        for (ILobby lobby : currentLobbies) {
            if (lobby.getLobbyID() == lobbyid) {
                lobby.leaveLobby(lobbyid, user);
                check = true;
            }
        }
        return check;
    }

    @Override
    public boolean removeLobby(int lobbyid) throws RemoteException {
        for (ILobby lobby : currentLobbies) {
            if (lobby.getLobbyID() == lobbyid) {
                currentLobbies.remove(lobby);
                return true;
            }
        }
        return false;
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
    public ArrayList<String> getPlayerInformationFromLobby(int lobbyid) throws RemoteException {
        ArrayList<String> returnValue = null;
        for (ILobby lobby : currentLobbies) {
            if (lobby.getLobbyID() == lobbyid) {
                returnValue = lobby.getPlayerInformationFromLobby(lobbyid);
            }
        }
        return returnValue;
    }

    @Override
    public String getPlayerInformation(String username) throws RemoteException {
        String returnValue = null;
        for (IUser user : loggedInUsers) {
            if (user.getUsername(user).equals(username)) {
                returnValue = user.getPlayerInformation(username);
            }
        }
        return returnValue;
    }

    @Override
    public void joinGame(int gameid, String username) throws RemoteException {
        //IGame returnValue = null;
        for (IGame game : currentGames) {
            if (game.getID() == gameid) {
                //returnValue = 
                        game.joinGame(gameid, username);
            }
        }
        //return returnValue;
    }

    @Override
    public String getUsername(IUser user) throws RemoteException {
        return user.getUsername(user);
    }

    @Override
    public boolean leaveGame(int gameid, String username) throws RemoteException {
        boolean returnValue = false;
        for (IGame game : currentGames) {
            if (game.getID() == gameid) {
                returnValue = game.leaveGame(gameid, username);
            }
        }
        return returnValue;
    }

    public boolean logout(IUser user) throws RemoteException {
        if (user != null) {
            this.loggedInUsers.remove(user);
            return true;
        }
        return false;
    }
    
    public boolean logout(String username){
       
        return false;
    }
    
    

    public boolean login(String username, String password) throws RemoteException {
        // do Database stufftest@

        return this.loggedInUsers.add((IUser) new User(username, password, "test@test.com"));
    }

    @Override
    public boolean addUserToLobby(String username, int lobbyid) throws RemoteException {
        boolean check = false;
        for (ILobby lobby : currentLobbies) {
            if (lobby.getLobbyID() == lobbyid) {
                for (IUser user : loggedInUsers) {
                    if (user.getUsername(user).equals(username)) {
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
    public int getID() {
        return 0;
    }

    @Override
    public int getLobbyID() {
        return 0;
    }

    public String createUser(String username, String email, String password) throws RemoteException {
        if (username == null || username.trim().isEmpty()) {
            return "Username cannot be empty.";
        }
        if (password == null || password.isEmpty()) {
            return "Password cannot be empty.";
        }
        if (email == null || email.trim().isEmpty()) {
            return "Email address cannot be empty.";
        }
        if (!(email.contains("@") && email.contains("."))) {
            return "Email address is not of correct format.";
        }
        if (username.length() < 6) {
            return "Username must be at least 6 characters";
        }
        if (password.length() < 6) {
            return "Password must be at least 6 characters";
        }

        try {
            boolean dbActionWorked = DatabaseHelper.registerUser(username, password, email);
            System.out.println("DBworked = " + dbActionWorked);

            Shared.User newUser = new Shared.User(username, password, email);

            this.loggedInUsers.add((IUser) newUser);
        } catch (SQLException ex) {
            return "Username is already taken";
        }
        return "";
    }

    

    @Override
    public void moveLeft(int gameId, String username) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void moveRight(int gameId, String username) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String> getOnlineUsers() throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<ILobby> getAllLobbies() throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<GameObject> getAllGameObjects(int gameId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void getAllBalls(int gameId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<GameObject> getChangedGameObjects(int gameId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<GameObject> getRemovedGamesObjects(int gameId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addListener(RemotePropertyListener listener, String property) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public String getOwner(int lobbyid) throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void createGame(int id, int gameTime, boolean powerUps) throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
