/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import Shared.Game;
import Shared.User;
import java.util.List;

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
    
    private List<User> joinedPlayers;
    
    /**
     * The name of the lobby will be checked that it is not empty
     * There will be checked if there is a password for the lobby
     * The owner can't be empty
     * The maxPlayer byte cannot be greater then 4 or lower than 1
     * THe host can't be empty
     * 
     * @param name The name of the lobby
     * @param password The password of the lobby
     * @param owner The owner of the lobby
     * @param maxPlayer The amount of maximum available players
     * @param host The host of the game
     */
    public Lobby(String name , String password, User owner,  byte maxPlayer, Server host){
               
        
    }
    
    /**
     * The player cannot be empty or null
     * The player leaves the lobby and will be removed from joinedPlayers
     * 
     * @param player The player that leaves the lobby
     */
    public void leaveLobby(User player){
    
    }
    
    /**
     * Player can't be null or empty
     * The player will be kicked
     * The player will be removed from joinedPlayers
     * @param player The player that will be kicked
     */
    public void kickPlayer(User player){
        
    }
    
    /**
     * Message can't be null or empty or have a length of 0
     * The message will be send to the GUI
     * @param message The message of the player
     */
    public void sendChat(String message){
        
    }
    
    /**
     * Username can't be empty or null
     * Username should exist in the database
     * The found user will be invited for the lobby
     * @param username The username of the player that will be invited
     */
    public void inviteUser(String username){
        
    }
    
    /**
     * 
     * @param game
     */
    public void joinGame(Game game){
        
    }
    
}
