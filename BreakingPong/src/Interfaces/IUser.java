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
public interface IUser {
    
    // Returns a string with Username,Ranking
    public String getPlayerInformation()throws RemoteException;
}
