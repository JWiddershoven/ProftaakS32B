/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Interfaces.IClient;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Jordi
 */
@Deprecated
public class Client extends UnicastRemoteObject implements IClient
{
    
    
    public Client()throws RemoteException
    {
        
    }
    
    /**
     * Pre-condition: User must be logged in.
     * Description: Returns user from the lobby to the Lobby Select screen.
     * @throws RemoteException 
     */
    @Override
    public void returnToLobby() throws RemoteException
    {
        
        
    }
}
