/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import Interfaces.IUser;
import Server.Server;
import Shared.Paddle;
import java.rmi.RemoteException;

/**
 *
 * @author Mnesymne
 */
public class RMIUser implements IUser
{
 //--------------------------------------------//
    private String username, password, email;
    private Paddle paddle;
    private Server selectedServer;
    private int Rating;
    
    //-----------------------------------
    @Override
    public String getPlayerInformation(String username) throws RemoteException {
        String returnValue = "Player: " + username +" has a rating of :" + Rating;
        return returnValue;
    }

    @Override
    public String getUsername(IUser user) {
       return username;
    }
    
}
