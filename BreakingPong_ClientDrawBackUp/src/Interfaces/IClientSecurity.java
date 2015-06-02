/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Shared.Session;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Mnesymne
 */
public interface IClientSecurity extends Remote{
    
    
    /**
     * Pre-condition: User must be in login screen.
     * Description: Gives the Username and Password to the Login method of IJoin and returns a Session if IJoin returns true.
     * @param UserName
     * @param Password
     * @return Session if IJoin returns true.
     * @throws RemoteException 
     */
    public Session login(String UserName,String Password) throws RemoteException;
    
    
    /**
     * Pre-condition: User must be logged in.
     * Description: Logs the player out, exits the game and sets Session to Null.
     * @param username username of the logged in user.
     * @return TRUE if succeeded - FALSE if failed.
     * @throws RemoteException 
     */
    public Session logout(String username) throws RemoteException;
    
    /**
     * Pre-condition: User must in CreateUser screen.
     * Description: Gives the information to the createUser method of IJoin and returns a boolean value of IJoin.createUser
     * @param username minimal of 6 symbols
     * @param Password minimal of 6 symbols
     * @param email must be a valid email meaning it contains a "@" and a "."
     * @return boolean value of IJoin.createUser
     * @throws RemoteException 
     */
    public boolean createUser(String username,String Password,String email)throws RemoteException;
}
