/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Shared.User;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Mnesymne
 */
public interface ILobby{
    
    /**
     * Pre-condition: Name must be unique and the owner does not own a lobby yet.
     * Description: Create a new Lobby, checks if name isnt already in use, if Owner doesn't have a game already and if maxPlayers is a valid value.If lobby is created adds lobby to the list
     of lobbies. Returns true if lobby is created.
     * @param name name of the lobby
     * @param Password password of the lobby ( can be left empty )
     * @param Owner Owner of the lobby / creator
     * @param maxPlayers maximum amount of players that can play.
     * @return TRUE if succeeded - FALSE if failed.
     * @throws RemoteException 
     */
    public boolean createLobby(String name,String Password,User Owner,Byte maxPlayers) throws RemoteException;
    
    /**
     * Pre-condition: Lobby must exist, must be enough space for a new player and the lobby is not ingame.
     * Description: Checks if lobby has room, and if player isn't already in it. Returns the ILobby.
     * @param lobby The lobby the player wants to join.
     * @return The new ILobby
     * @throws RemoteException 
     */
    public ILobby joinLobby(ILobby lobby) throws RemoteException;
    
    /**
     * Pre-condition: Player mus be in a lobby.
     * Description: Checks if player is inside the lobby, Removes player. Returns true if player is removed.
     * @return TRUE if succeeded - FALSE if failed.
     * @throws RemoteException 
     */
    public boolean leaveLobby()throws RemoteException;
    
    /**
     * Pre-condition: Lobby must exist.
     * Description: Checks if lobby exists. If it exists checks if user is owner. If both are true removes all leftover players to lobbySelect and deletes the Lobby. Returns true when successfull.
     * @param lobby The lobby to be removed.
     * @return TRUE if succeeded - FALSE if failed.
     * @throws RemoteException 
     */
    public boolean removeLobby(ILobby lobby)throws RemoteException;
    
    /**
     * Description: Checks if chat doesn't have illegal words, returns true if message is send.
     * @param message The message the player wants to send.
     * @return TRUE if succeeded - FALSE if failed.
     * @throws RemoteException 
     */
    public boolean sendChat(String message)throws RemoteException;
    
    /**
     * Description: Calls method on server to get all send chats. returns list of received messages.
     * @return List of received messages
     * @throws RemoteException 
     */
    public ArrayList<String> receiveChat()throws RemoteException;
    
    /**
     * Description: Returns a List with Username,ranking of all players in the game.
     * @return List of Username and Ranking of all players in the game.
     * @throws RemoteException 
     */
    public ArrayList<String> getPlayerInformationFromLobby()throws RemoteException;
}
