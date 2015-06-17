/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import Helpers.StaticConstants;
import Interfaces.IClientSecurity;
import Interfaces.IServer;
import Interfaces.IServerSecurity;
import RMI.RMIGame;
import RMI.ServerRMI;
import Server.Administration;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lorenzo
 */
public class SecurityRMI extends UnicastRemoteObject implements IServerSecurity, IClientSecurity {

    private final Administration _administration;

    /**
     *
     * @throws RemoteException
     */
    public SecurityRMI() throws RemoteException {
        _administration = new Administration();
    }

    /**
     * Pre-condition: User must be in a lobby. Description: Returns user from
     * the lobby to the Lobby Select screen if the user has been AFK for too
     * long.
     *
     * @throws RemoteException
     */
    @Override
    public void returnToLobbySelect() throws RemoteException {

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Pre-condition: User must be in login screen. Description: Gives the
     * Username and Password to the Login method of IJoin and returns a Session
     * if IJoin returns true.
     *
     * @param UserName
     * @param Password
     * @return Session if IJoin returns true.
     * @throws RemoteException
     */
    @Override
    public Session login(String UserName, String Password) throws RemoteException {
        Session session = null;
        try {
            IServer server = (IServer) Naming.lookup(StaticConstants.SERVER_RMI_STRING);
                    
            if (server.login(UserName, Password))
            {
               session = new Session(UserName, (IServer) server);
            }
            //_administration.login(UserName, Password);

        }
        catch (NotBoundException | MalformedURLException ex)
        {
            Logger.getLogger(SecurityRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return session;
    }

    /**
     * Pre-condition: User must be logged in.
     * Description: Logs the player out, exits the game and sets Session to Null.
     * @param username username of the logged in user.
     * @return TRUE if succeeded - FALSE if failed.
     * @throws RemoteException 
     */
    @Override
    public Session logout(String username) throws RemoteException {        
        //_administration.getServer().logout();
        return null;
    }

    /**
     * Pre-condition: User must in CreateUser screen. Description: Gives the
     * information to the createUser method of IJoin and returns a boolean value
     * of IJoin.createUser
     *
     * @param username minimal of 6 symbols
     * @param Password minimal of 6 symbols
     * @param email must be a valid email meaning it contains a "@" and a "."
     * @return boolean value of IJoin.createUser
     * @throws RemoteException
     */
    @Override
    public boolean createUser(String username, String Password, String email) throws RemoteException {
        String errorMessage = _administration.getServer().createUser(username, email, Password);
        if (errorMessage != null)
            return false;
        return true;
    }

}
