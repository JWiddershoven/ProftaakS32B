/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Server.Lobby;
import Shared.User;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Mnesymne
 */
public interface ILobby {
    
    //Create a new Lobby, checks if name isnt already in use, if Owner doesn't have a game already and if maxPlayers is a valid value.If lobby is created adds lobby to the list
    // of lobbies. Returns true if lobby is created.
    public boolean addLobby(String name,String Password,User Owner,Byte maxPlayers) throws RemoteException;
    
    //Checks if lobby has room, and if player isn't already in it. Returns the ILobby.
    public ILobby joinLobby() throws RemoteException;
    
    //Checks if player is inside the lobby, Removes player. Returns true if player is removed.
    public boolean leaveLobby()throws RemoteException;
    
    //Checks if lobby exists. If it exists checks if user is owner. If both are true removes all leftover players to lobbySelect and deletes the Lobby. Returns true when successfull.
    public boolean removeLobby()throws RemoteException;
    
    //Checks if chat doesn't have illegal words, returns true if message is send.
    public boolean SendChat(String Message)throws RemoteException;
    
    //Calls method on server to get all send chats. returns list of received strings.
    public ArrayList<String> receiveChat()throws RemoteException;
    
    //Returns a List with Username,ranking of all players in the game.
    public ArrayList<String> getPlayerInformationFromLobby()throws RemoteException;
}
