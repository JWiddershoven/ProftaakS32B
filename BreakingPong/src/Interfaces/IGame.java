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
public interface IGame {
    
    //Checks if game exists and has room for another player, adds user to game, returns the IGame of the game.
    public IGame joinGame(IGame game) throws RemoteException;
    
    //Returns true if player is in game, also removes player from game if its true;
    public boolean leaveGame(IGame game) throws RemoteException;
    
    //Checks if the game exists, if so removes all players from the game and then deletes the game. Returns true if game is deleted
    public boolean removeGame(IGame game) throws RemoteException;
    
    //Checks if player is in the game, if so removes player from game and returns him to the lobby. Returns true if player is removed.
    public boolean kickPlayer(IUser user)throws RemoteException;
    
    //Returns a List with Username,Score,ranking of all players in the game.
    public ArrayList<String> getPlayerInformation()throws RemoteException;
    
    //Adds a map to the server, checks if map isnt already on server.
    public void addMap(IMap map)throws RemoteException;
}
