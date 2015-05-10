/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import Interfaces.IClientSecurity;
import Interfaces.ILobby;
import Interfaces.IServerSecurity;
import Shared.Session;

/**
 *
 * @author Jordi
 */
public class Security extends UnicastRemoteObject implements IClientSecurity, IServerSecurity
{
    public Security() throws RemoteException 
    {
        
    }
    
    @Override
    public Session login(String username, String password) throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public Session logout() throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public boolean createUser(String username, String password, String email) throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void returnToLobby(ILobby lobby) throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
