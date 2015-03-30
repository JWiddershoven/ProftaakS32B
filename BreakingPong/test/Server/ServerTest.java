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
public class ServerTest {

    Server server;

    @Test
    public void testCreation() {
        try {
            server = new Server();
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void testJoinLobby() {

        server = new Server();

        try {
            server.JoinLobby(null);
            fail();
        } catch (IllegalArgumentException ex) {

        }

        server = new Server();

        byte maxPlayers = 4;
        Lobby lobby = new Lobby(1, "Test", null, new User("Testtest", "Testtest", "test@test.test", new Server()), maxPlayers, new Server());

        try {
            server.JoinLobby(lobby);
            server.JoinLobby(lobby);
            fail();
        } catch (IllegalArgumentException ex) {

        }
        server = new Server();

        try {
            server.JoinLobby(lobby);

        } catch (IllegalArgumentException ex) {
            fail();
        }

    }

    @Test
    public void testLogout() {
        server = new Server();

        try {
            server.LogOut(null);
            fail();
        } catch (IllegalArgumentException ex) {

        }

        User user1 = new User("Testtest", "Testtest", "test@test.test", new Server());
        try {
            server.LogOut(user1);
            fail();
        } catch (IllegalArgumentException ex) {

        }

    }
}
