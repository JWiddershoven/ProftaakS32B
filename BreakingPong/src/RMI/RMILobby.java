/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import Interfaces.ILobby;
import Interfaces.IUser;
import Server.Lobby;
import Server.Server;
import Shared.Game;
import Shared.User;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Mnesymne
 */
public class RMILobby implements ILobby{
    
    private int id;

    private String name;

    private String password;

    private User owner;

    private byte maxPlayers;

    private Server host;

    private Game game;

    private ArrayList<IUser> joinedPlayers;
            
    @Override
    public boolean leaveLobby(ILobby lobby ,IUser user) throws RemoteException {
       if(this.joinedPlayers.contains(user))
       {
           this.joinedPlayers.remove(user);
           return true;
       }
       return false;
    }


    @Override
    public boolean sendChat(String message) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String> receiveChat() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String> getPlayerInformationFromLobby(ILobby lobby) throws RemoteException {
        ArrayList<String> returnvalue = new ArrayList<String>();
        if(joinedPlayers != null && !joinedPlayers.isEmpty())
        {
            for(IUser user : joinedPlayers)
            {
                returnvalue.add(user.getPlayerInformation(user));
            }
        }
        return returnvalue;
    }

    @Override
    public boolean addUserToLobby(IUser user, ILobby lobby) {
       
        if(user != null && !joinedPlayers.contains(user))
        {
            joinedPlayers.add(user);
            return true;
        }
        else return false;
    }
    
}
