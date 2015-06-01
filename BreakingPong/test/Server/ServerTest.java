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
public class ServerTest {

    Server server;

    @Test
    public void testCreation() {
        try {
            server = new Server();
        }
        catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void testCreateLobby() throws RemoteException {
        server = new Server();
        server.loggedInUsers.add(new User("test123", "test123", "test@mail.com"));
        try {
            if (!server.createLobby("test123", "test123", "username", (byte) 2)) {
                fail("Failed to create lobby");
            }
        }
        catch (Exception ex) {
            fail(ex.getMessage());
        }

    }

    @Test
    public void testJoinLobby() throws RemoteException {

        server = new Server();
        try {
            if (!server.createLobby("test", "test", "username", (byte) 2)) {
                fail("Failed to create lobby");
            }
        }
        catch (Exception ex) {
            fail(ex.getMessage());
        }

        try {
            server.joinLobby(1, "username2");
        }
        catch (IllegalArgumentException ex) {
            fail(ex.getMessage());
        }
        server.joinLobby(2, "username2");
//        fail("Deze lobby bestaat niet");

    }

    @Test
    public void testLogout() throws RemoteException {
        server = new Server();

        try {
            server.logout(new User("test","test", "es@bl.nl"));
            fail("user does not exist");
        }
        catch (IllegalArgumentException ex) {

        }

        User user1 = new User("Testtest", "Testtest", "test@test.test");
        try {
            server.logout(user1);
        }
        catch (IllegalArgumentException ex) {

            fail(ex.getMessage());
        }

    }
}
