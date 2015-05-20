/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Mnesymne
 */
public interface ILobby{
    
   
    
    /**
     * Pre-condition: Player mus be in a lobby.
     * Description: Checks if player is inside the lobby, Removes player. Returns true if player is removed.
     * @param user The user that is using the client.
     * @return TRUE if succeeded - FALSE if failed.
     * @throws RemoteException 
     */
    public boolean leaveLobby(ILobby lobby,IUser user)throws RemoteException;   
    
    
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
    public ArrayList<String> getPlayerInformationFromLobby(ILobby lobby)throws RemoteException;
    
    public boolean addUserToLobby(IUser user, ILobby lobby);
    
}
