/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;


import fontys.observer.RemotePublisher;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Mnesymne
 */

public interface IServer extends Remote,IGame,ILobby,IUser,RemotePublisher
{
    
   /**
     * Pre-condition: Name must be unique and the owner does not own a lobby yet.
     * Description: Create a new Lobby, checks if name isnt already in use, if Owner doesn't have a game already and if maxPlayers is a valid value.If lobby is created adds lobby to the list
     of lobbies. Returns true if lobby is created.
     * @param name name of the lobby
     * @param Password password of the lobby ( can be left empty )
     * @param username Owner of the lobby / creator
     * @param maxPlayers maximum amount of players that can play.
     * @return TRUE if succeeded - FALSE if failed.
     * @throws RemoteException 
     */
    public boolean createLobby(String name,String Password,String username,Byte maxPlayers) throws RemoteException;
    
    /**
     * Pre-condition: Lobby must exist, must be enough space for a new player and the lobby is not ingame.
     * Description: Checks if lobby has room, and if player isn't already in it. Returns the ILobby.
     * @param lobbyid The lobby the player wants to join.
     * @param username The username of the player trying to join.
     * @return The new ILobby
     * @throws RemoteException 
     */
    public ILobby joinLobby(int lobbyid,String username) throws RemoteException;
    
    /**
     * Pre-condition: Lobby must exist.
     * Description: Checks if lobby exists. If it exists checks if user is owner. If both are true removes all leftover players to lobbySelect and deletes the Lobby. Returns true when successfull.
     * @param lobbyid The lobby to be removed.
     * @return TRUE if succeeded - FALSE if failed.
     * @throws RemoteException 
     */
    public boolean removeLobby(int lobbyid)throws RemoteException;
    /**
     * Returns an ArrayList of online users.
     * @return an ArrayList of online users.
     * @throws RemoteException 
     */
    public ArrayList<String> getOnlineUsers() throws RemoteException;
    
    /**
     * Returns an ArrayList of the lobbies.
     * @return an ArrayList of the lobbies.
     * @throws RemoteException
     */
    public ArrayList<ILobby> getAllLobbies() throws RemoteException;
    
    public boolean login(String username, String password) throws RemoteException;
}
