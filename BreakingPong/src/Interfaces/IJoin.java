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
    
    //Checks login credentials to Database, returns true if User/password combination can be found in database
    public boolean login(String Username,String Password)throws RemoteException;
    
    //Returns true if player is logged in
    public boolean logout()throws RemoteException;
    
    //Creates a user with given credentials. Credentials are already checked so shouldn't be needed to check again.
    public boolean createUser(String Username,String Password,String Email)throws RemoteException;
}
