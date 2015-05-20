/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

/**
 *
 * @author sjorsvanmierlo
 */
public class LoggedinUser {
    
    private final boolean loggedIn;
    private final String username;
    private final String password;
    private final String email;
    private final double rating;
    
    public LoggedinUser(boolean loggedIn){
        this.loggedIn = loggedIn;
        this.username = "";
        this.password = "";
        this.email = "";
        this.rating = 0.0;
    }
    
    public LoggedinUser(boolean loggedIn, String username, String password, String email, double rating){
        this.loggedIn = loggedIn;
        this.username = username;
        this.password = password;
        this.email = email;
        this.rating = rating;
    }
    
    public boolean getLoggedIn(){
        return this.loggedIn;
    }
    
    public String getUsername(){
        return this.username;
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public String getEmail(){
        return this.email;
    }
    
    public double getRating(){
        return this.rating;
    }
    
    
    
}
