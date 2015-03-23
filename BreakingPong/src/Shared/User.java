/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Shared;

import Server.Server;

/**
 *
 * @author Mnesymne
 */
public class User {
    
    //--------------------------------------------//
    private String username , password , email;
    private Paddle paddle;
    private Server selectedServer;
    //-------------------------------------------//

    /**
     * Constructor
     * @param username as String minimal of 6 letters
     * @param password as String minimal of 6 symbols
     * @param email as String , must be a valid email meaning it contains a @ and f.ex  .com
     * @param selectedServer as Server, this is the server the user is currently in.
     */
    public User(String username, String password, String email, Server selectedServer) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.selectedServer = selectedServer;
    }

    /**
     * Getter of Paddle
     * @return a Paddle
     */
    public Paddle getPaddle() {
        return paddle;
    }

    /**
     * Getter of selectedServer
     * @return the selectedServer
     */
    public Server getSelectedServer() {
        return selectedServer;
    }

    /**
     * Setter of selectedServer
     * @param selectedServer as Server
     */
    public void setSelectedServer(Server selectedServer) {
        this.selectedServer = selectedServer;
    }

    /**
     * Getter of Username
     * @return  username as String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter of Username
     * username must contain more then 6 symbols, and must be different than current username.
     * @param username  as String
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter of Password
     * @return password as String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter of Password
     * password must contain more then 6 symbols , and must be different than current password.
     * @param password 
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter of email
     * @return email as String.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter of email
     * Must be a valid email, containing a @ and f.ex 
     * @param email 
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    
}
