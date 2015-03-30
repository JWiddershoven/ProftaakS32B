/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Shared.Game;
import Shared.User;
import java.util.ArrayList;

/**
 *
 * @author Mnesymne
 */
public class Lobby {

    private int id;

    private String name;

    private String password;

    private User owner;

    private byte maxPlayers;

    private Server host;

    private Game game;

    private ArrayList<User> joinedPlayers;

    /**
     * The name of the lobby will be checked that it is not empty There will be
     * checked if there is a password for the lobby The owner can't be empty The
     * maxPlayer byte cannot be greater then 4 or lower than 1 THe host can't be
     * empty
     *
     * @param id
     * @param name The name of the lobby
     * @param password The password of the lobby
     * @param owner The owner of the lobby
     * @param maxPlayer The amount of maximum available players
     * @param host The host of the game
     */
    public Lobby(int id, String name, String password, User owner, byte maxPlayer, Server host) {

        if (name == null || name.trim().contentEquals("")) {
            throw new IllegalArgumentException("Name is null or empty!");
        }

        if (owner == null) {
            throw new IllegalArgumentException("Owner is null!");
        }

        if (maxPlayer == 0 || maxPlayer > 4) {
            throw new IllegalArgumentException("maxPlayer(s) is out of range!");
        }

        if (host == null) {
            throw new IllegalArgumentException("Host is null!");
        }

        if (password == null || !password.trim().contentEquals("")) {
            this.password = null;
        } else {
            //there is a password
            this.password = password;
        }

        this.id = id;
        this.name = name;
        this.owner = owner;
        this.maxPlayers = maxPlayer;
        this.host = host;
        this.joinedPlayers = new ArrayList<>();
    }

    /**
     * The player cannot be empty or null The player leaves the lobby and will
     * be removed from joinedPlayers
     *
     * @param player The player that leaves the lobby
     */
    public void leaveLobby(User player) {
        if (player == null) {
            throw new IllegalArgumentException("Player is null!");
        }

        //Check if user does exist in the lobby!
        
        if(this.joinedPlayers.contains(player)){
            this.joinedPlayers.remove(player);
        }
        else{
            throw new IllegalArgumentException("Player does not exists!");
        }
    }

    /**
     * Player can't be null or empty The player will be kicked The player will
     * be removed from joinedPlayers
     *
     * @param player The player that will be kicked
     */
    public void kickPlayer(User player) {

        if (player == null) {
            throw new IllegalArgumentException("Player is null!");
        }

        joinedPlayers.remove(player);

        System.out.println("Kicked player : " + player.getUsername());
    }

    /**
     * Message can't be null or empty or have a length of 0 The message will be
     * send to the GUI
     *
     * @param message The message of the player
     */
    public void sendChat(String message) {
        if (message == null || message.trim().equals("")) {
            throw new IllegalArgumentException("Message empty or null!");
        }

        //To be filled in...
    }

    /**
     * Username can't be empty or null Username should exist in the database The
     * found user will be invited for the lobby
     *
     * @param username The username of the player that will be invited
     */
    public void inviteUser(String username) {
        if (username == null || username.contentEquals("")) {
            throw new IllegalArgumentException("Username is null or empty!");
        }

        for (User user : this.joinedPlayers) {
            if (user.getUsername().toLowerCase().contentEquals(username.toLowerCase())) {
                throw new IllegalArgumentException("User is already in the lobby!");
            }
        }

        //To be filled in...
    }

    /**
     * Player can't be null The player will be added to the joined players.
     *
     *
     * @param player THe player that will join the lobby
     */
    public void joinLobby(User player) {

        if (player == null) {
            throw new IllegalArgumentException("Player is null");
        }

        if (joinedPlayers.contains(player)) {
            throw new IllegalArgumentException("User is already in the lobby!");
        }

        this.joinedPlayers.add(player);

    }

    /**
     * Game can't be empty or null The user can join the chosen game
     *
     * @param game The selected game that will be joined
     */
    public void joinGame(Game game) {

        if (game == null) {
            throw new IllegalArgumentException("The game is null!");
        }

        //To be filled in...
    }

}
