/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import RMI.ClientRMI;
import Shared.GameObject;
import fontys.observer.RemotePublisher;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Mnesymne
 */
public interface IGame{
    
    /**
     * Pre-condition: User must be in the lobby of the game he/she wants to join.
     * Description: Checks if game exists and has room for another player, adds user to game, returns the IGame of the game.
     * @param game The IGame the user wants to join.
     * @param user The user that is using the client.
     * @return The IGame of the game the user wants to join.
     * @throws RemoteException 
     */
    public IGame joinGame(int gameID , String username) throws RemoteException;
    
    /**
     * Pre-condition: User must be in the game he/she wants to leave.
     * Description: Removed the player from the game
     * @param game The IGame the user wants to leave.
     * @param user The user that is using the client.
     * @return TRUE if succeeded - FALSE if failed.
     * @throws RemoteException 
     */
    public boolean leaveGame(int gameID , String username) throws RemoteException;
    
    /**
     * Pre-condition: User must be in a lobby and cannot be the owner of this lobby.
     * Description: Checks if player is in the game, if so removes player from game and returns him to the lobby. Returns true if player is removed.
     * @param username User to be removed from the game it is in.-=
     * @return TRUE if succeeded - FALSE if failed.
     * @throws RemoteException
     */
    public boolean kickPlayer(String username)throws RemoteException;
    
    /**
     * Pre-condition:
     * Description: Returns a List with Username,Score,ranking of all players in the game.
     * @param gameid The ID of the game the client is in.
     * @return List of Strings of with Username,Score,ranking of all players in the game.
     * @throws RemoteException 
     */
    public ArrayList<String> getPlayersInformation(int gameid)throws RemoteException;
    
    /**
     * Pre-condition: Map is of correct format.
     * Description: Adds a map to the server, checks if map isnt already on server.
     * @param map map made by user that he/she wants to be added to the server.
     * @throws RemoteException 
     */
    public void addMap(IMap map)throws RemoteException;
    
    /**
     * Pre-condition:
     * Description: Returns a List with Username,Score,ranking of all players in the game.
     * @return
     * @throws RemoteException 
     */
    public int getID() throws RemoteException;
    
    /**
     * Pre-condition:
     * Description: Moves player left.
     * @param gameId
     * @param username
     * @throws RemoteException 
     */
    public void moveLeft(int gameId, String username) throws RemoteException;
    /**
     * Pre-condition:
     * Description: Moves player right.
     * @param gameId
     * @param username
     * @throws RemoteException 
     */
    public void moveRight(int gameId, String username) throws RemoteException;
    
    /**
     * Gives back all objects known to this game.
     * @param gameId GameID of the game.
     * @return 
     * @throws RemoteException 
     */
    public ArrayList<GameObject> getAllGameObjects(int gameId) throws RemoteException;
 
    /**
     * 
     * @param gameId
     * @throws RemoteException 
     */
    public void getAllBalls(int gameId) throws RemoteException;
    
    /**
     * Returns all GameObjects that have changed their position or size
     * @param gameId
     * @return ArrayList of GameObjects that changed their position or size.
     * @throws RemoteException 
     */
    public ArrayList<GameObject> getChangedGameObjects(int gameId) throws RemoteException;
    /**
     * Returns all GameObjects that have been removed.
     * @param gameId
     * @return ArrayList of GameObjects that have been removed.
     * @throws RemoteException 
     */
    public ArrayList<GameObject> getRemovedGamesObjects(int gameId) throws RemoteException;
}
