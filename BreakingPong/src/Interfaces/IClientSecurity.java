/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import Server.Session;

/**
 *
 * @author Mnesymne
 */
public interface IClientSecurity extends Remote{
    
    
    // Gives the Username and Password to the Login method of IJoin, Returns a Session if IJoin returns true
    public Session login(String UserName,String Password) throws RemoteException;
    
    // Logs the player out and exits the game,sets Session to Null
    public Session logout()throws RemoteException;
    
    // Gives the Information to the createUser method of IJoin, Returns boolean value of IJoin
    // * @param username as String minimal of 6 letters
    // * @param password as String minimal of 6 symbols
    // * @param email as String , must be a valid email meaning it contains a @
    public boolean createUser(String username,String Password,String email)throws RemoteException;
}
