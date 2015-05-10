/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Shared.Map;
import Shared.User;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The server class
 *
 * @author Mnesymne
 */
public class Server {

    /*
     List with all the current onlineUsers
     */
    private final List<User> onlineUsers;

    /*
     List with all the current lobby's
     */
    private List<Lobby> lobbys;

    /*
     List with all the current onlineUsers
     */
    public List<User> getOnlineUsers() {
        return onlineUsers;
    }

    /*
     List with all the current lobby's
     */
    public List<Lobby> getLobbys() {
        return lobbys;
    }

    /**
     * The maps that are on server
     */
    private final ObservableList<Map> mappenObserableList = FXCollections.observableArrayList();

    /**
     * The maps that are on server
     *
     * @return ObservableList of type Map
     */
    public ObservableList<Map> getMappenObservableList() {
        return mappenObserableList;
    }

    /**
     * Constructor
     */
    public Server() {
        onlineUsers = new ArrayList<>();
        lobbys = new ArrayList<>();

//TODO: hieronder verwijderen als mappen inlezen werkt.
        mappenObserableList.add(new Map("testmap", 4, new int[10][10]));
    }

    /**
     * Checks if the selected lobby exists and the lobby is not full and checks
     * if there is a password and checks if the password is correct
     *
     * @param lobby The lobby the user wants to join.
     * @return TRUE when succesfully joined lobby.
     */
    public boolean JoinLobby(Lobby lobby) {

        if (lobby == null) {
            throw new IllegalArgumentException("Lobby is null!");
        }

        if (!this.lobbys.contains(lobby)) {
            this.lobbys.add(lobby);
            System.out.println("Joined lobby!");
            return true;
        } else {
            throw new IllegalArgumentException("Lobby already exists!");
        }
    }

    /**
     * 
     * @param name
     * @param password
     * @param owner
     * @param maxPlayer
     * @param host
     * @return Newly created lobby
     */
    public Lobby CreateLobby(String name, String password, User owner, byte maxPlayer, Server host) {
        int id = lobbys.size() + 1;
        
        if(name == null || name.isEmpty())
            throw new IllegalArgumentException("Name is empty or null");
        
        if(owner == null)
            throw new IllegalArgumentException("Owner is null");
        
        if(host == null)
            throw new IllegalArgumentException("Host is null");
        
        
        Lobby lobby = new Lobby(id, name, password, owner, maxPlayer, host);
        lobbys.add(lobby);
        return lobby;
    }

    /**
     * Logs the current user off
     *
     * @param user The user
     */
    public void LogOut(User user) {

        if (user == null) {
            throw new IllegalArgumentException("User is null!");
        }

        if (this.onlineUsers.contains(user)) {
            this.onlineUsers.remove(user);
            System.out.println(user.getUsername() + " logged out!");
        } else {
            throw new IllegalArgumentException("User does not exist!");
        }

    }
    
    /**
     * Add a user to the online user collection
     * @param user The user that logged in
     */
    public void addUser(User user){
        if(user == null)
            throw new IllegalArgumentException();
        
        onlineUsers.add(user);
    }
    
    /**
     * Delete the map from the list of maps.
     * @param map The map that will be deleted from the list of maps, cannot be null.
     */
    public void deleteMap(Map map)
    {
        if (map != null && mappenObserableList.contains(map))
        {
            this.mappenObserableList.remove(map);
        } 
        else
        {
            throw new IllegalArgumentException("Map cannot be null and map must exist.");
        }
    }


}
