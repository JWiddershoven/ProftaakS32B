/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Mnesymne
 */
public interface IServerSecurity extends Remote {
    
    //Returns the User if user has been AFK for to long.
    public void returnToLobby(ILobby lobby)throws RemoteException;
}
