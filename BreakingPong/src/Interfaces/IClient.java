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
public interface IClient extends Remote{
    
    /**
     * Pre-condition: User must be logged in.
     * Description: Returns user from the lobby to the Lobby Select screen.
     * @throws RemoteException 
     */
    public void returnToLobby() throws RemoteException;
}
