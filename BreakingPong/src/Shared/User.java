/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import Interfaces.IUser;
import Server.Server;
import java.rmi.RemoteException;

/**
 *
 * @author Mnesymne
 */
public class User implements IUser
{

    //--------------------------------------------//
    private String username, password, email;
    private Paddle paddle;
    private Server selectedServer;
    private int rating;
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
    public User(String username, String password, String email, Server selectedServer)
    {
        if (username == null || username.trim().isEmpty())
        {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (password == null || password.trim().isEmpty())
        {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (email == null || email.trim().isEmpty())
        {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (!(email.contains("@") && email.contains(".")))
        {
            throw new IllegalArgumentException("Email is not of correct format");
        }
        if (selectedServer == null)
        {
            throw new IllegalArgumentException("Server cannot be null");
        }
        if (username.length() < 6)
        {
            throw new IllegalArgumentException("Username must be at least 6 characters");
        }
        if (password.length() < 6)
        {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }
        this.username = username;
        this.password = password;
        this.email = email;
        this.selectedServer = selectedServer;
        this.rating = 0;

    }

    /**
     * Getter of rating
     *
     * @return rating as int.
     */
    public int getRating()
    {
        return rating;
    }

    /**
     * Setter of rating If end rating is lower then 0, become 0. Else new rating
 is old rating - rating.
     *
     * @param Change
     */
    public void setRating(int Change)
    {
        if (this.rating - Change > 0)
        {
            this.rating -= Change;
        }
        else
        {
            this.rating = 0;
        }
    }

    /**
     * Getter of Paddle
     *
     * @return a Paddle
     */
    public Paddle getPaddle()
    {
        return paddle;
    }

    /**
     * Getter of selectedServer
     *
     * @return the selectedServer
     */
    public Server getSelectedServer()
    {
        return selectedServer;
    }

    /**
     * Setter of selectedServer
     *
     * @param selectedServer as Server
     */
    public void setSelectedServer(Server selectedServer)
    {
        if (selectedServer != null && selectedServer != this.selectedServer)
        {
            this.selectedServer = selectedServer;
        }
    }

    /**
     * Getter of Username
     *
     * @return username as String
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Setter of Username username must contain more then 6 symbols, and must be
     * different than current username.
     *
     * @param username as String
     */
    public void setUsername(String username)
    {
        if (username != null && !username.isEmpty() && username.length() >= 6)
        {
            this.username = username;
        }
        else
        {
            throw new IllegalArgumentException("Username cannot be null,empty and has to be longer than 6.");
        }
    }

    /**
     * Getter of Password
     *
     * @return password as String
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Setter of Password password must contain more then 6 symbols , and must
     * be different than current password.
     *
     * @param password
     */
    public void setPassword(String password)
    {
        if (password != null && !password.isEmpty() && password.length() >= 6)
        {
            this.password = password;
        }
        else
        {
            throw new IllegalArgumentException("Password cannot be null,empty and has to be longer than 6.");
        }
    }

    /**
     * Getter of email
     *
     * @return email as String.
     */
    public String getEmail()
    {
        return email;
    }

    public void setPaddle(Paddle p)
    {
        if (this.paddle == null)
        {
            this.paddle = p;
        }
    }

    /**
     * Setter of email Must be a valid email, containing a @ and f.ex
     *
     * @param email
     */
    public void setEmail(String email)
    {
        if (email != null && !email.isEmpty() && email.contains("@"))
        {
            if (email.substring(0, email.indexOf("@")).length() > 0 && email.substring(email.indexOf("@", email.indexOf(".com"))).length() >= 1)
            {
                this.email = email;
            }
        }
        else
        {
            throw new IllegalArgumentException("E-mail cannot be null,empty and has to have use the format : Name@provider.com");
        }
    }

    /**
     * Return username
     * @return username
     */
    @Override
    public String toString()
    {
        return username;
    }

    /**
     * Description: Returns a string with Username and Ranking
     * @param username
     * @return String with Username and Ranking
     * @throws RemoteException 
     */
    @Override
    public String getPlayerInformation(String username) throws RemoteException {
       return this.username + " - " + this.rating;
    }

    /**
     * Description: RMI - Returns a string with the username
     * @return  String with the Username
     */
    @Override
    public String getUsername(IUser user) {
        return this.username;
    }
}
