/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Shared.User;
import java.rmi.RemoteException;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author sjorsvanmierlo
 */
public class LobbyTest
{

    private Lobby lobby;

    @Test
    public void testCreation() throws RemoteException
    {

        //Arrange
        Server server;

        server = new Server();
        int id = 0;
        String name = "test";
        String password = "test";
        User owner = new User("testtest", "testtest", "test@test.test", server);

        byte maxPlayers = 1;

        //Violate name null
        try
        {
            lobby = new Lobby(id, null, password, owner, maxPlayers);
            fail();
        }
        catch (IllegalArgumentException ex)
        {

        }

        //Violate name empty
        try
        {
            lobby = new Lobby(id, " ", password, owner, maxPlayers);
            fail();
        }
        catch (IllegalArgumentException ex)
        {

        }

        //Violate owner null
        try
        {
            lobby = new Lobby(id, name, password, null, maxPlayers);
            fail();
        }
        catch (IllegalArgumentException ex)
        {

        }

        //Violate maxplayers
        try
        {
            maxPlayers = 0;
            lobby = new Lobby(id, name, password, owner, maxPlayers);
            fail();
        }
        catch (IllegalArgumentException ex)
        {

        }

        try
        {
            maxPlayers = 5;
            lobby = new Lobby(id, name, password, owner, maxPlayers);
            fail();
        }
        catch (IllegalArgumentException ex)
        {

        }

        maxPlayers = 4;

        //Violate host null
        try
        {
            lobby = new Lobby(id, name, password, owner, maxPlayers);
            fail();
        }
        catch (IllegalArgumentException ex)
        {

        }

        //Violate password null
        try
        {
            lobby = new Lobby(id, name, null, owner, maxPlayers);

        }
        catch (IllegalArgumentException ex)
        {
            fail();
        }

        //Violate password empty        
        try
        {
            lobby = new Lobby(id, name, " ", owner, maxPlayers);

        }
        catch (IllegalArgumentException ex)
        {
            fail();
        }

        //Success
        try
        {
            lobby = new Lobby(id, name, password, owner, maxPlayers);

        }
        catch (IllegalArgumentException ex)
        {
            fail();
        }

    }

    @Test
    public void testJoinLobby() throws RemoteException
    {
        byte maxPlayers = 4;
        lobby = new Lobby(1, "Test", null, new User("Testtest", "Testtest", "test@test.test", new Server()), maxPlayers);

        try
        {
            lobby.joinLobby(null);
            fail();
        }
        catch (IllegalArgumentException ex)
        {

        }

        try
        {
            User user1 = new User("Testtest", "Testtest", "test@test.test", new Server());

            lobby.joinLobby(user1);
            lobby.joinLobby(user1);

            fail();
        }
        catch (IllegalArgumentException ex)
        {

        }
    }

    @Test
    public void testLeaveLobby() throws RemoteException
    {
        byte maxPlayers = 4;
        lobby = new Lobby(1, "Test", null, new User("Testtest", "Testtest", "test@test.test", new Server()), maxPlayers);

        try
        {
            lobby.leaveLobby(null);
            fail();
        }
        catch (IllegalArgumentException ex)
        {

        }

        User user = new User("Testtest", "Testtest", "Test@test.test", new Server());

        try
        {
            lobby.leaveLobby(user);
            fail();
        }
        catch (IllegalArgumentException ex)
        {

        }

        try
        {
            lobby.joinLobby(user);
            lobby.leaveLobby(user);
        }
        catch (IllegalArgumentException ex)
        {
            fail();
        }

    }

    @Test
    public void testKickLobby() throws RemoteException
    {
        byte maxPlayers = 4;
        lobby = new Lobby(1, "Test", null, new User("Testtest", "Testtest", "test@test.test", new Server()), maxPlayers);

        try
        {
            lobby.kickPlayer(null);
            fail();
        }
        catch (IllegalArgumentException ex)
        {

        }

        try
        {
            User user = new User("Testtest", "Testtest", "Test@test.test", new Server());

            lobby.joinLobby(user);
            lobby.kickPlayer(user);

        }
        catch (Exception ex)
        {
            fail();
        }

    }

    @Test
    public void testSendChat() throws RemoteException
    {
        byte maxPlayers = 4;
        lobby = new Lobby(1, "Test", null, new User("Testtest", "Testtest", "test@test.test", new Server()), maxPlayers);

        try
        {
            lobby.sendChat(null);
            fail();
        }
        catch (IllegalArgumentException ex)
        {

        }

        try
        {
            lobby.sendChat(" ");
            fail();
        }
        catch (IllegalArgumentException ex)
        {

        }

    }

    @Test
    public void testInviteUser() throws RemoteException
    {
        byte maxPlayers = 4;
        lobby = new Lobby(1, "Test", null, new User("Testtest", "Testtest", "test@test.test", new Server()), maxPlayers);

        try
        {
            lobby.inviteUser(null);
            fail();
        }
        catch (IllegalArgumentException ex)
        {

        }

    }

    @Test
    public void testJoinGame() throws RemoteException
    {
        byte maxPlayers = 4;
        lobby = new Lobby(1, "Test", null, new User("Testtest", "Testtest", "test@test.test", new Server()), maxPlayers);

        try
        {
            lobby.joinGame(null);
            fail();
        }
        catch (IllegalArgumentException ex)
        {

        }
    }

}
