/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lorenzo
 */
public class DatabaseTest {

    @Test
    public void TestCreation() {
        try {
            Database db = new Database();
        } catch (Exception ex) {
            fail("Constructor crashed");
        }
    }

}
