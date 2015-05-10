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
public interface IClient  extends Remote{
    
    // Returns the User to the lobby if this user currently isn't in it.
    public void ReturnToLobby() throws RemoteException;
}
