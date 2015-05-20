/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.rmi.RemoteException;

/**
 *
 * @author Mnesymne
 */
public interface IJoin{
    
    /**
     * Description: Checks login credentials to Database, returns true if User/password combination can be found in database
     * @param username
     * @param password
     * @return TRUE if login succeeded - FALSE if login failed.
     * @throws RemoteException 
     */
    public boolean login(String username,String password)throws RemoteException;
    
    /**
     * Pre-condition: User must be logged in.
     * Description: Returns true if user is logged in.
     * @param user The user that is currently using the client
     * @return true if user is logged in
     * @throws RemoteException 
     */
    public boolean logout(IUser user)throws RemoteException;
    
    /**
     * Description: Creates a user with given credentials. Credentials are already checked so shouldn't be needed to check again.
     * @param username
     * @param password
     * @param email
     * @return TRUE if succeeded - FALSE if failed.
     * @throws RemoteException 
     */
    public boolean createUser(String username,String password,String email)throws RemoteException;
}
