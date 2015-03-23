/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import Shared.User;
import java.util.ArrayList;

/**
 *
 * @author Mnesymne
 */
public class Administration {
    
    private Server server;
    
    private ArrayList<User> Users;
    
    /**
     * The administration constructor
     */
    public Administration(){
        this.server = new Server();
        this.Users = new ArrayList<User>();
    }
    
    /**
     * FIll in the username and password
     * You can login on to the server.
     * 
     * @param UserName The username of the user
     * @param Password The password of the user
     */
    public void Login(String UserName, String Password){
        
    }
}
