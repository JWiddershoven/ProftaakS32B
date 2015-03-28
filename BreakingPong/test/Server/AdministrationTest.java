/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

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
        //Success
        administration = new Administration();
    }

    @Test
    public void testLogin() {

        administration = new Administration();

        //Empty string
        String username = "Test";
        String password = "Test";

        try {
            administration.login(username, null);
            fail();
        } catch (IllegalArgumentException ex) {

        }

        try {
            administration.login(null, password);
            fail();
        } catch (IllegalArgumentException ex) {

        }

        try {
            administration.login(null, null);
            fail();
        } catch (IllegalArgumentException ex) {

        }

        try {
            administration.login("", password);
            fail();
        } catch (IllegalArgumentException ex) {

        }

        try {
            administration.login(username, "");
            fail();
        } catch (IllegalArgumentException ex) {

        }

        try {
            administration.login(" ", password);
            fail();
        } catch (IllegalArgumentException ex) {

        }

        try {
            administration.login(username, " ");
            fail();
        } catch (IllegalArgumentException ex) {

        }

    }

}
