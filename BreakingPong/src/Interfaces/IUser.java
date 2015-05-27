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
     * @param username
     * @return String with Username and Ranking
     * @throws RemoteException 
     */
    public String getPlayerInformation(String username)throws RemoteException;
    
    /**
     * Description: Returns a string with the username
     * @param user Won't be used
     * @return  String with the Username
     */
    public String getUsername(IUser user);
    
}
