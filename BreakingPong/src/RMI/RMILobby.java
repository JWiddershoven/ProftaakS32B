/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import Interfaces.ILobby;
import Interfaces.IServer;
import Interfaces.IUser;
import Server.Lobby;
import Server.Server;
import Server.Game;
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

    private ServerRMI host;

    private Game game;

    private ArrayList<IUser> joinedPlayers;
            
    @Override
    public boolean leaveLobby(int lobbyid ,String username) throws RemoteException {
       for(IUser user : joinedPlayers)
       {
           if(user.getUsername(user).equals(username))
           {
               joinedPlayers.remove(user);
               return true;
           }
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
    public ArrayList<String> getPlayerInformationFromLobby(int lobbyid) throws RemoteException {
        ArrayList<String> returnvalue = new ArrayList<String>();
        if(joinedPlayers != null && !joinedPlayers.isEmpty())
        {
            for(IUser user : joinedPlayers)
            {
                returnvalue.add(user.getPlayerInformation(user.getUsername(user)));
            }
        }
        return returnvalue;
    }

    @Override
    public boolean addUserToLobby(String username, int lobbyid) {
       boolean Breturn = false;
       for(IUser user: joinedPlayers)
       {
           if(user.getUsername(user).equals(username))
           {
               Breturn = false;
           }
       }
       
       for(IUser user: host.loggedInUsers)
       {
           if(user.getUsername(user).equals(username))
           {
               joinedPlayers.add(user);
               Breturn = true;
           }
     }
       return Breturn;
    }

    @Override
    public int getLobbyID() {
        return this.id;
    }
    
}
