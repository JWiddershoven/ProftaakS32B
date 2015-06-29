/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Shared.Paddle;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 *
 * @author Mnesymne
 */
public interface IUser extends Serializable{
    
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
     * @throws java.rmi.RemoteException
     */
    public String getUsername(IUser user) throws RemoteException;

    public void setPaddle(Paddle P4Paddle) throws RemoteException;
    
    public Paddle getPaddle() throws RemoteException;
    
}
