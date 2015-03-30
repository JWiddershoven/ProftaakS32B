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
    private String username, password, email;
    private Paddle paddle;
    private Server selectedServer;
    private int Rating;
    //-------------------------------------------//

    /**
     * Constructor
     *
     * @param username as String minimal of 6 letters
     * @param password as String minimal of 6 symbols
     * @param email as String , must be a valid email meaning it contains a @
     * and f.ex .com
     * @param selectedServer as Server, this is the server the user is currently
     * in.
     */
    public User(String username, String password, String email, Server selectedServer) {
        if (!username.isEmpty() && username != null && !password.isEmpty() && password != null && !email.isEmpty() && email != null
                && selectedServer != null) {
            if (username.length() >= 6 && password.length() >= 6 && email.contains("@") && email.contains(".")) {
                this.username = username;
                this.password = password;
                this.email = email;
                this.selectedServer = selectedServer;
                this.Rating = 0;
            } else {
                throw new IllegalArgumentException();
            }
        }
    }

    /**
     * Getter of Rating
     * @return  rating as int.
     */
    public int getRating() {
        return Rating;
    }

    /**
     * Setter of Rating
     * If end Rating is lower then 0, become 0. Else new Rating is old Rating - rating.
     * @param Change 
     */
    public void setRating(int Change) {
        if (this.Rating - Change > 0) {
            this.Rating -= Change;
        } else {
            this.Rating = 0;
        }
    }

    /**
     * Getter of Paddle
     *
     * @return a Paddle
     */
    public Paddle getPaddle() {
        return paddle;
    }

    /**
     * Getter of selectedServer
     *
     * @return the selectedServer
     */
    public Server getSelectedServer() {
        return selectedServer;
    }

    /**
     * Setter of selectedServer
     *
     * @param selectedServer as Server
     */
    public void setSelectedServer(Server selectedServer) {
        if (selectedServer != null && selectedServer != this.selectedServer) {
            this.selectedServer = selectedServer;
        }
    }

    /**
     * Getter of Username
     *
     * @return username as String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter of Username username must contain more then 6 symbols, and must be
     * different than current username.
     *
     * @param username as String
     */
    public void setUsername(String username) {
        if (!username.isEmpty() && username != null && username.length() >= 6) {
            this.username = username;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Getter of Password
     *
     * @return password as String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter of Password password must contain more then 6 symbols , and must
     * be different than current password.
     *
     * @param password
     */
    public void setPassword(String password) {
        if (!password.isEmpty() && password.length() >= 6 && password != null) {
            this.password = password;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Getter of email
     *
     * @return email as String.
     */
    public String getEmail() {
        return email;
    }
    
    public void setPaddle(Paddle p)
    {
        if(this.paddle == null)
        {
            this.paddle = p;
        }        
    }

    /**
     * Setter of email Must be a valid email, containing a @ and f.ex
     *
     * @param email
     */
    public void setEmail(String email) {
        if (!email.isEmpty() && email != null && email.contains("@")) {
            this.email = email;
        } else {
            throw new IllegalArgumentException();
        }
    }

}
