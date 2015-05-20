/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import Interfaces.IJoin;
import Interfaces.IServer;
import Interfaces.IUser;
import Shared.User;
import java.rmi.RemoteException;

/**
 *
 * @author Mnesymne
 */
public class RMIJoin implements IJoin{
    
    private ServerRMI server;

    @Override
    public boolean login(String username, String password) throws RemoteException {
        if(username != null && !username.isEmpty() && password != null && !password.isEmpty())
        {
            //server.login(username,password);
            return true;
        }
        else throw new IllegalArgumentException();
    }

    @Override
    public boolean logout(IUser user) throws RemoteException {
        if(user != null)
        {
        server.logout(user);
        return true;
        }
        return false;
    }

    

    @Override
    public boolean createUser(String username, String password, String email) throws RemoteException {
        if(password.length() >= 6 && email.contains("@") && email.contains("."))
        {
       // IUser user = new User(username,password,email,server);
            return true;
        }
        else throw new IllegalAccessError();
         
    }


    
}
