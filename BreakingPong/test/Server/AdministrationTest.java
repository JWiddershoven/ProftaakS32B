/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Server.Administration.IncorrectLoginDataException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author sjorsvanmierlo
 */
public class AdministrationTest {

    private Administration administration;

    @Test
    public void testCreation() {
        try
        {
            //Success
            administration = new Administration();
        }
        catch (RemoteException ex)
        {
            Logger.getLogger(AdministrationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testLogin() {

        try
        {
            administration = new Administration();
        }
        catch (RemoteException ex)
        {
            Logger.getLogger(AdministrationTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Empty string
        String username = "Test";
        String password = "Test";

        try {
            administration.login(username, null);
            fail();
        } catch (IllegalArgumentException | IncorrectLoginDataException ex) {

        }

        try {
            administration.login(null, password);
            fail();
        } catch (IllegalArgumentException | IncorrectLoginDataException ex) {

        }

        try {
            administration.login(null, null);
            fail();
        } catch (IllegalArgumentException | IncorrectLoginDataException ex) {

        }

        try {
            administration.login("", password);
            fail();
        } catch (IllegalArgumentException | IncorrectLoginDataException ex) {

        }

        try {
            administration.login(username, "");
            fail();
        } catch (IllegalArgumentException | IncorrectLoginDataException ex) {

        }

        try {
            administration.login(" ", password);
            fail();
        } catch (IllegalArgumentException | IncorrectLoginDataException ex) {

        }

        try {
            administration.login(username, " ");
            fail();
        } catch (IllegalArgumentException | IncorrectLoginDataException ex) {

        }

    }

}
