/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import Shared.User;
import java.util.ArrayList;
import java.util.List;

/**
 * The server class
 * @author Mnesymne
 */
public class Server {
    
    /*
    List with all the current users
    */
    private List<User> users;
    
    /*
    List with all the current lobby's
    */
    private List<Lobby> lobbys;
    
    /**
     * Constructor
     */
    public Server(){
        users = new ArrayList<>();
        lobbys = new ArrayList<>();
    }
    
    /**
     * Checks if the selected lobby exists 
     * and the lobby is not full 
     * and checks if there is a password 
     * and checks if the password is correct
     * @param lobby The lobby the user wants to join.
     */
    public void JoinLobby(Lobby lobby){
        
    }
    
    /**
     * Logs the current user off 
     */
    public void LogOut(){
        
    }
    
}
