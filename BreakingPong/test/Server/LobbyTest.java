/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Shared.User;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author sjorsvanmierlo
 */
public class LobbyTest {

    private Lobby lobby;

    @Test
    public void testCreation() {

        //Arrange
        Server server = new Server();
        int id = 0;
        String name = "test";
        String password = "test";
        User owner = new User("testtest", "testtest", "test@test.test", server);

        byte maxPlayers = 1;

        //Violate name null
        try {
            lobby = new Lobby(id, null, password, owner, maxPlayers, server);
            fail();
        } catch (IllegalArgumentException ex) {

        }

        //Violate name empty
        try {
            lobby = new Lobby(id, " ", password, owner, maxPlayers, server);
            fail();
        } catch (IllegalArgumentException ex) {

        }

        //Violate owner null
        try {
            lobby = new Lobby(id, name, password, null, maxPlayers, server);
            fail();
        } catch (IllegalArgumentException ex) {

        }

        //Violate maxplayers
        try {
            maxPlayers = 0;
            lobby = new Lobby(id, name, password, owner, maxPlayers, server);
            fail();
        } catch (IllegalArgumentException ex) {

        }

        try {
            maxPlayers = 5;
            lobby = new Lobby(id, name, password, owner, maxPlayers, server);
            fail();
        } catch (IllegalArgumentException ex) {

        }

        maxPlayers = 4;

        //Violate host null
        try {
            lobby = new Lobby(id, name, password, owner, maxPlayers, null);
            fail();
        } catch (IllegalArgumentException ex) {

        }

        //Violate password null
        try {
            lobby = new Lobby(id, name, null, owner, maxPlayers, server);

        } catch (IllegalArgumentException ex) {
            fail();
        }

        //Violate password empty        
        try {
            lobby = new Lobby(id, name, " ", owner, maxPlayers, server);

        } catch (IllegalArgumentException ex) {
            fail();
        }

        //Success
        try {
            lobby = new Lobby(id, name, password, owner, maxPlayers, server);
        } catch (IllegalArgumentException ex) {
            fail();
        }

    }

}
