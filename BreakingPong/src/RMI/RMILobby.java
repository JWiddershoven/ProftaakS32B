/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import Client.ClientGUI;
import Interfaces.ILobby;
import Interfaces.IUser;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Mnesymne
 */
public class RMILobby implements ILobby, Serializable
{

    private int id;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public IUser getOwner()
    {
        return owner;
    }

    public void setOwner(RMIUser owner)
    {
        this.owner = owner;
    }

    public byte getMaxPlayers()
    {
        return maxPlayers;
    }

    public void setMaxPlayers(byte maxPlayers)
    {
        this.maxPlayers = maxPlayers;
    }

    private String name;

    private String password;

    private transient IUser owner;

    private byte maxPlayers;

    private transient ServerRMI host;

    private transient RMIGame game;

    private ArrayList<IUser> joinedPlayers = new ArrayList<>();
  
    
    public RMILobby(ServerRMI  server)
    {
        host = server;
    }
    
    @Override
    public boolean leaveLobby(int lobbyid, String username) throws RemoteException
    {
        for (IUser user : joinedPlayers)
        {
            if (user.getUsername(user).equals(username))
            {
                joinedPlayers.remove(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean sendChat(String message) throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String> receiveChat() throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String> getPlayerInformationFromLobby(int lobbyid) throws RemoteException
    {
        ArrayList<String> returnvalue = new ArrayList<String>();
        if (joinedPlayers != null && !joinedPlayers.isEmpty())
        {
            for (IUser user : joinedPlayers)
            {
                returnvalue.add(user.getPlayerInformation(user.getUsername(user)));
            }
        }
        return returnvalue;
    }

    @Override
    public boolean addUserToLobby(String username, int lobbyid) throws RemoteException
    {
        boolean Breturn = false;
        
        if(joinedPlayers == null)
            joinedPlayers = new ArrayList<>();
        
//        for (IUser user : joinedPlayers)
//        {
//            if (user.getUsername(user).equals(username))
//            {
//                return false;
//                //Breturn = false;
//            }
//        }
        
       
        for (IUser user : host.loggedInUsers)
        {
            if (user.getUsername(user).equals(username))
            {
                joinedPlayers.add(user);
                Breturn = true;
            }
        }
        return Breturn;
    }

    @Override
    public int getLobbyID()
    {
        return this.id;
    }

    @Override
    public String getOwner(int lobbyid) throws RemoteException
    {
        if (this.owner == null)
            return "No owner!";
        return this.owner.getUsername(null);
    }

    @Override
    public void createGame(int id, int gameTime, boolean powerUps) throws RemoteException
    {
        RMIGame newGame = new RMIGame(id, gameTime,powerUps);
        this.game = newGame;
    }
}
