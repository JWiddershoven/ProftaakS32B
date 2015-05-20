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
public interface IUser{
    
    /**
     * Description: Returns a string with Username and Ranking
     * @return String with Username and Ranking
     * @throws RemoteException 
     */
    public String getPlayerInformation(IUser user)throws RemoteException;
    
    /**
     * Description: Returns a string with the username
     * @return  String with the Username
     */
    public String getUsername(IUser user);
    
}
