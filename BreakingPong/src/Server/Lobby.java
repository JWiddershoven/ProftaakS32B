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
     *
     * @param name
     * @param password
     * @param owner
     * @param maxPlayer
     * @param host
     */
    public Lobby(String name , String password, User owner,  byte maxPlayer, Server host){
               
        
    }
    
    /**
     *
     */
    public void LeaveLobby(){
    
    }
    
    /**
     *
     * @param player
     */
    public void KickPlayer(User player){
        
    }
    
    /**
     *
     * @param message
     */
    public void SendChat(String message){
        
    }
    
    /**
     *
     * @param username
     */
    public void InviteUser(String username){
        
    }
    
    /**
     *
     * @param game
     */
    public void JoinGame(Game game){
        
    }
    
}
