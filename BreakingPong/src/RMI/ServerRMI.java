/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import Helpers.DatabaseHelper;
import Helpers.LoggedinUser;
import Interfaces.IGame;
import Interfaces.ILobby;
import Interfaces.IMap;
import Interfaces.IServer;
import Interfaces.IUser;
import Shared.GameObject;
import Shared.User;
import fontys.observer.BasicPublisher;
import fontys.observer.RemotePropertyListener;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerRMI extends UnicastRemoteObject implements IServer, Remote {

    public static BasicPublisher publisher;

    private int ID;
    private int gameID;
    public ArrayList<IUser> loggedInUsers = new ArrayList<>();
    private ArrayList<IUser> oldLoggedInUsers = new ArrayList<>();
    private String[] usernames;
    private ArrayList<ILobby> currentLobbies = new ArrayList<>();
    private ArrayList<ILobby> oldLobbies = new ArrayList<>();
    private String[] lobbynames;
    private ArrayList<IGame> currentGames = new ArrayList<>();

    private int nextLobbyId = 0;

    public ServerRMI() throws RemoteException {
        String[] array = new String[]{"getPlayers", "getLobbys", "lobbyselectChat", "GetCurrentPaddles",
            "getBlocks", "getBalls", "getPaddles", "getTime", "getScore", "getGameOver", "getDestroys", "getChanged"};

        this.publisher = new BasicPublisher(array);
        this.ID = 1;
        //fillWithTestData();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    checkLoggedInUsers();
                    checkCurrentLobbys();
                }
                catch (RemoteException ex) {
                    Logger.getLogger(ServerRMI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }, 0, 1000);
    }

    private void fillWithTestData() {
        RMILobby lobby = new RMILobby(this);
        lobby.setId(currentLobbies.size() + 1);
        lobby.setMaxPlayers((byte) 4);
        lobby.setName("Test Lobby");
        RMIUser user = new RMIUser("test user", "password", "test@gmai.com", 20);
        lobby.setOwner((RMIUser) user);
        currentLobbies.add(lobby);
        loggedInUsers.add(user);
    }

    /**
     * Returns a list of current lobbies.
     *
     * @return an ArrayList with ILobbies.
     */
    public ArrayList<ILobby> getCurrentLobbies() {
        return this.currentLobbies;
    }

    /**
     * Kicks a player from a game.
     *
     * @param username the username of the user that will get kicked.
     * @param lobbyID the lobbyID of the lobby.
     * @return true if the user was removed from the game, otherwise false.
     * @throws RemoteException
     */
    @Override
    public boolean kickPlayer(String username, int lobbyID) throws RemoteException {
        boolean kick = false;
        for (ILobby lobby : currentLobbies) {
            if (lobby.getLobbyID() == lobbyID) {
                sendChat(lobbyID, username + " has been kicked.\n");
                this.leaveLobby(lobbyID, username);
                System.out.println("Kicked " + username);
                kick = true;
                break;
            }
        }
        return kick;
    }

    /**
     * Creates a new user and adds it to the database.
     *
     * @param username the username of the user. Must be at least 6 characters
     * and cannot be empty.
     * @param email the email of the user. Must contain a dot (.) and @. Cannot
     * be empty.
     * @param password the password of the user. Must be at least 6 characters
     * and cannot be empty.
     * @return
     * @throws RemoteException
     */
    @Override
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
        }
        catch (SQLException ex) {
            Logger.getLogger(ServerRMI.class.getName()).log(Level.SEVERE, null, ex);
            return "Username is already taken";
        }
        return "";
    }

    /**
     * Collects the information from each player in an instance of a game.
     *
     * @param gameid the ID of the game.
     * @return an ArrayList containing the information of each player in the
     * specified game.
     * @throws RemoteException
     */
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

    /**
     * Adds a map to the server.
     *
     * @param map The map object.
     * @throws RemoteException
     */
    @Override
    public void addMap(IMap map) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Creates a new lobby with the specified parameters.
     *
     * @param name the lobby name, cannot be empty.
     * @param Password the lobby password.
     * @param Owner the host of the lobby, cannot be empty.
     * @param maxPlayers the maximum amount of players in the lobby. (2/4)
     * @return true if a new lobby has been created, otherwise false.
     * @throws RemoteException
     */
    @Override
    public ILobby createLobby(String name, String Password, String Owner, Byte maxPlayers) throws RemoteException {
        if (name != null && Password != null && Owner != null && maxPlayers != null) {
            for (IUser user : loggedInUsers) {
                if (user.getUsername(user).equals(Owner)) {
                    RMILobby lobby = new RMILobby(this);
                    lobby.setId(nextLobbyId);
                    nextLobbyId++;
                    lobby.setMaxPlayers(maxPlayers);
                    lobby.setName(name);
                    if (!Password.equals("")) {
                        lobby.setPassword(Password);
                    }
                    lobby.setOwner(user);
                    currentLobbies.add((ILobby) lobby);
                    publisher.addProperty("getChat" + Integer.toString(lobby.getId()));
                    publisher.addProperty("getLobbyPlayers" + Integer.toString(lobby.getId()));
                    publisher.addProperty("getLobbyStarted" + Integer.toString(lobby.getId()));
                    //currentLobbies.get(currentLobbies.size() - 1).addUserToLobby(user.getUsername(user), lobby.getId());
                    joinLobby(lobby.getId(), user.getUsername(null));
                    return lobby;
                }
            }
        }
        return null;
    }

    /**
     * Attempts to put a user in a lobby.
     *
     * @param lobbyid the lobbyid of the lobby that the user wants to join.
     * @param username the username of the user that wants to join a lobby.
     * @return a lobby object if successfull, otherwise null;
     * @throws RemoteException
     */
    @Override
    public ILobby joinLobby(int lobbyid, String username) throws RemoteException {
        ILobby returnValue = null;
        for (ILobby lobby : currentLobbies) {
            if (lobby.getLobbyID() == lobbyid) {
                lobby.addUserToLobby(username, lobbyid);
                returnValue = lobby;
                publisher.inform(1, "getLobbyPlayers" + Integer.toString(lobbyid), null, lobby.getPlayerInformationFromLobby(lobbyid));
                break;
            }
        }
        return returnValue;
    }

    /**
     * Attempts to remove a user from a lobby.
     *
     * @param lobbyid the lobbyid of the lobby that the user wishes to leave.
     * @param user the username of the user that wishes to leave a lobby.
     * @return true if successfull, otherwise false.
     * @throws RemoteException
     */
    @Override
    public boolean leaveLobby(int lobbyid, String user) throws RemoteException {
        ILobby lobbyToRemove = null;
        boolean check = false;
        try {
            for (ILobby lobby : currentLobbies) {
                if (lobby.getLobbyID() == lobbyid) {
                    lobby.leaveLobby(lobbyid, user);
                    if (lobby.getOwner(0).equals(user) && lobby.getPlayerInformationFromLobby(0).size() > 0) {
                        // er zijn nog players in de game
                        lobby.setNextOwner();
                        System.out.println("New lobby owner " + lobby.getOwner(0));
                    }
                    else if (lobby.getPlayerInformationFromLobby(0).isEmpty()) {
                        // remove lobby
                        lobbyToRemove = lobby;
                    }
                    else {
                        sendChat(lobbyid, user + " has left the game.\n");
                    }
                    check = true;
                    publisher.inform(1, "getLobbyPlayers" + Integer.toString(lobbyid), null, lobby.getPlayerInformationFromLobby(lobbyid));
                    break;
                }
            }
            if (lobbyToRemove != null) {
                System.out.println("No players left in lobby - removing lobby.");
                currentLobbies.remove(lobbyToRemove);
            }
        }
        catch (Exception ex) {
            Logger.getLogger(ServerRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

    /**
     * Attempts to delete a lobby.
     *
     * @param lobbyid the lobbyid of the lobby that is going to be removed.
     * @return true if successfull, otherwise false.
     * @throws RemoteException
     */
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

    /**
     * Sends a chat message
     *
     * @param message The message that the user wants to send, cannot be empty.
     * @return true if the message was successfully sent, otherwise false.
     * @throws RemoteException
     */
    @Override
    public boolean sendChat(int lobbyId, String message) throws RemoteException {
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty!");
        }
        publisher.inform(1, "getChat" + Integer.toString(lobbyId), null, message);
        return true;
    }

    /**
     * Receives the chat messages.
     *
     * @return an ArrayList of chat messages.
     * @throws RemoteException
     */
    @Override
    public ArrayList<String> receiveChat() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Collects the user information from each user in the specified lobby.
     *
     * @param lobbyid the lobbyid
     * @return an ArrayList containing the information of each player from that
     * lobby.
     * @throws RemoteException
     */
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

    /**
     * Collects the information of a single user.
     *
     * @param username the username of the user.
     * @return a String containing the information of that user.
     * @throws RemoteException
     */
    @Override
    public String getPlayerInformation(String username) throws RemoteException {
        for (IUser user : loggedInUsers) {
            if (user.getUsername(user).equals(username)) {
                return user.getPlayerInformation(username);
            }
        }
        return null;
    }

    /**
     * Attempts to put a player in a game.
     *
     * @param gameid the gameID.
     * @param username the username of the user.
     * @throws RemoteException
     */
    @Override
    public void joinGame(int gameid, String username) throws RemoteException {
        //IGame returnValue = null;
        for (IGame game : currentGames) {
            if (game.getID() == gameid) {
                //returnValue = 
                //RMIGame newGame = (RMIGame) game;
                game.joinGame(gameid, username);
                break;
            }
        }
        //return returnValue;
    }

    /**
     * Gets the username of a user.
     *
     * @param user the user.
     * @return the username of the user.
     * @throws RemoteException
     */
    @Override
    public String getUsername(IUser user) throws RemoteException {
        return user.getUsername(user);
    }

    /**
     * Attempts to remove a player from a game.
     *
     * @param gameid the gameID.
     * @param username the username of the user.
     * @return true if successfull, otherwise false.
     * @throws RemoteException
     */
    @Override
    public boolean leaveGame(int gameid, String username) throws RemoteException {
        IGame gameToRemove = null;
        boolean returnValue = false;
        for (IGame game : currentGames) {
            if (game.getID() == gameid) {
                returnValue = game.leaveGame(gameid, username);
                if (game.getPlayersInformationInGame(gameid).isEmpty()) {
                    gameToRemove = game;
                    // remove game
                }
            }
        }
        if (gameToRemove != null) {
            currentGames.remove(gameToRemove);
        }
        return returnValue;
    }

    /**
     * Method used to logout a user.
     *
     * @param username the username of the user.
     * @return true if successfull, otherwise false.
     * @throws RemoteException
     */
    @Override
    public boolean logout(String username) throws RemoteException {
        for (IUser user : loggedInUsers) {
            if (user.getUsername(null).equals(username)) {
                this.loggedInUsers.remove(user);

                System.out.println("User: " + username + " has logged out.");

                publisher.inform(this, "getPlayers", null, username);

                return true;
            }
        }
        return false;
    }

    /**
     * Attempts to login a user using the paramaters.
     *
     * @param username the username of the user who is trying to login.
     * @param password the password of the user who is trying to login.
     * @return true if successfull, otherwise false.
     * @throws RemoteException
     */
    @Override
    public boolean login(String username, String password) throws RemoteException {
        try {
            LoggedinUser lUser = DatabaseHelper.loginUser(username, password);

            if (lUser.getLoggedIn()) {
                User user = new User(lUser.getUsername(), lUser.getPassword(), lUser.getEmail());
                user.setRating(lUser.getRating());
                loggedInUsers.add((IUser) user);
                System.out.println(user.getUsername());
                System.out.println(user.getEmail());
                System.out.println(user.getRating());
                System.out.println("Logging in user " + username);
                return true;
            }
            System.out.println("Failed to login user " + username);
            return false;
        }
        catch (IllegalArgumentException exc) {
            Logger.getLogger(ServerRMI.class.getName()).log(Level.SEVERE, null, exc);
            return false;
        }
    }

    /**
     * Attempts to add a user to a lobby.
     *
     * @param username the username of the user.
     * @param lobbyid the lobbyid.
     * @return true if successfull, otherwise false.
     * @throws RemoteException
     */
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

    /**
     * Gets the ID of the server.
     *
     * @return the ID of the server.
     * @throws RemoteException
     */
    @Override
    public int getID() throws RemoteException {
        return ID;
    }

    /**
     * Gets the lobbyID.
     *
     * @return the lobbyID.
     * @throws RemoteException
     */
    @Override
    public int getLobbyID() throws RemoteException {
        return ID;
    }

    /**
     * Moves the paddle of a user to the left.
     *
     * @param gameId the gameID.
     * @param username the username of the user.
     * @throws RemoteException
     */
    @Override
    public void moveLeft(int gameId, String username) throws RemoteException {
        for (int i = currentGames.size() - 1; i >= 0; i--) {
            if (currentGames.get(i).getID() == gameId) {
                currentGames.get(i).moveLeft(gameId, username);
            }
        }
    }

    /**
     * Moves the paddle of a user to the right.
     *
     * @param gameId the gameID.
     * @param username the username of the user.
     * @throws RemoteException
     */
    @Override
    public void moveRight(int gameId, String username) throws RemoteException {
        for (int i = currentGames.size() - 1; i >= 0; i--) {
            if (currentGames.get(i).getID() == gameId) {
                currentGames.get(i).moveRight(gameId, username);
            }
        }
    }

    /**
     * Returns a list containing every online user.
     *
     * @return an ArrayList containing online users.
     * @throws RemoteException
     */
    @Override
    public ArrayList<String> getOnlineUsers() throws RemoteException {
        ArrayList<String> onlineUsers = new ArrayList<>();

        for (IUser user : loggedInUsers) {
            onlineUsers.add(user.getPlayerInformation(user.getUsername(user)));
        }

        return onlineUsers;
    }

    /**
     * Returns a list of all lobbies.
     *
     * @return a list of all lobbies.
     * @throws RemoteException
     */
    @Override
    public ArrayList<ILobby> getAllLobbies() throws RemoteException {
        return this.currentLobbies;
    }

    /**
     * Returns a list of all gameobjects.
     *
     * @param gameId the gameID.
     * @return an ArrayList containing every single gameobject.
     * @throws RemoteException
     */
    @Override
    public ArrayList<GameObject> getAllGameObjects(int gameId) throws RemoteException {
        for (int i = currentGames.size() - 1; i >= 0; i--) {
            if (currentGames.get(i).getID() == gameId) {
                return currentGames.get(i).getAllGameObjects(gameId);
            }
        }
        return null;
    }

    /**
     * Returns a list of all balls.
     *
     * @param gameId the gameID.
     * @throws RemoteException
     */
    @Override
    public void getAllBalls(int gameId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Returns a list of all changed gameobjects.
     *
     * @param gameId the gameID.
     * @return an ArrayList containing every single changed gameobject.
     * @throws RemoteException
     */
    @Override
    public ArrayList<GameObject> getChangedGameObjects(int gameId) throws RemoteException {
        for (int i = currentGames.size() - 1; i >= 0; i--) {
            if (currentGames.get(i).getID() == gameId) {
                return currentGames.get(i).getChangedGameObjects(gameId);
            }
        }
        return new ArrayList<GameObject>();
    }

    /**
     * Returns a list of all deleted objects.
     *
     * @param gameId the gameID.
     * @return an ArrayList containing every single deleted gameobject.
     * @throws RemoteException
     */
    @Override
    public ArrayList<GameObject> getRemovedGamesObjects(int gameId) throws RemoteException {
        for (int i = currentGames.size() - 1; i >= 0; i--) {
            if (currentGames.get(i).getID() == gameId) {
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
    public String getOwner(int lobbyid) throws RemoteException {
        for (ILobby lobby : currentLobbies) {
            if (lobby.getLobbyID() == lobbyid) {
                return lobby.getOwner(lobbyid);
            }
        }
        return null;
    }

    @Override
    public void createGame(int id, int gameTime, boolean powerUps) throws RemoteException {
        for (int i = currentLobbies.size() - 1; i >= 0; i--) {
            if (currentLobbies.get(i).getLobbyID() == id) {
                currentLobbies.get(i).createGame(id, gameTime, powerUps);
                System.out.println("GameCreated");
            }
        }

    }

    public void checkLoggedInUsers() throws RemoteException {
        if (this.loggedInUsers != this.oldLoggedInUsers) {
            usernames = new String[loggedInUsers.size()];
            for (int i = 0; i < loggedInUsers.size(); i++) {
                usernames[i] = loggedInUsers.get(i).getUsername(null);
            }
            publisher.inform(this, "getPlayers", null, this.loggedInUsers);
            oldLoggedInUsers = loggedInUsers;
        }
    }

    public void checkCurrentLobbys() throws RemoteException {
        if (this.currentLobbies != this.oldLobbies) {
            lobbynames = new String[currentLobbies.size()];
            for (int i = 0; i < currentLobbies.size(); i++) {
                lobbynames[i] = currentLobbies.get(i).getLobbyID() + ": " + currentLobbies.get(i).getOwner(currentLobbies.get(i).getLobbyID());
            }
            // TODO: inform lobbynames?
            publisher.inform(this, "getLobbys", null, currentLobbies);
            oldLobbies = currentLobbies;
        }
    }

    @Override
    public void setNextOwner() throws RemoteException {
        // hoeft niet geimplementeerd te worden.
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sendLobbySelectChat(String message) throws RemoteException {
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty!");
        }
        try {
            publisher.inform(1, "lobbyselectChat", null, message);
        }
        catch (Exception ex) {
            Logger.getLogger(ServerRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getLobbyOwnerUsername(int lobbyId) throws RemoteException {
        for (int i = 0; i < currentLobbies.size(); i++) {
            if (currentLobbies.get(i).getLobbyID() == lobbyId) {
                return currentLobbies.get(i).getOwner(lobbyId);
            }
        }
        return "NO LOBBY OWNER.";
    }

    @Override
    public void startGame(int lobbyId) throws RemoteException {
        new Thread(() -> {
            try {
                sendChat(lobbyId, "Server: Game starting in 5...\n");
                Thread.sleep(1000);
                sendChat(lobbyId, "Server: Game starting in 4...\n");
                Thread.sleep(1000);
                sendChat(lobbyId, "Server: Game starting in 3...\n");
                Thread.sleep(1000);
                sendChat(lobbyId, "Server: Game starting in 2...\n");
                Thread.sleep(1000);
                sendChat(lobbyId, "Server: Game starting in 1...\n");
                Thread.sleep(1000);
                publisher.inform(1, "getLobbyStarted" + Integer.toString(lobbyId), false, true);
                Thread.sleep(2000);
                for (int i = 0; i < currentLobbies.size(); i++) {
                    if (currentLobbies.get(i).getLobbyID() == lobbyId) {
                        currentLobbies.get(i).createGame(lobbyId, 300, true);
                        break;
                    }
                }
            }
            catch (RemoteException ex) {
                Logger.getLogger(ServerRMI.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (InterruptedException ex) {
                Logger.getLogger(ServerRMI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }

}
