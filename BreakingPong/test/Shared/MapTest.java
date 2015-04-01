/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jelle
 * TO-DO: deleteMap()
 */
public class MapTest {

    @Test
    public void testMap() {
        /**
         * Creates a new Map object with a name, playerAmount and layout.
         */
        int[][] layout = new int[5][];
        Map m;
        // @param name The name of the map. Name cannot be an empty string.
        try {
            m = new Map("Cube World", 4, layout);
        } catch (IllegalArgumentException exc) {

        }

        try {
            m = new Map("", 4, layout);
            fail();
        } catch (IllegalArgumentException exc) {

        }

        // @param playerAmount The amount of players of the map. playerAmount has to
        // be 2 or 4.
        try {
            m = new Map("Cube World", 6, layout);
            fail();
        } catch (IllegalArgumentException exc) {

        }

        // @param layout The layout of the map. Must contain at least one item.
        try {
            m = new Map("Cube World", 4, null);
            fail();
        } catch (IllegalArgumentException exc) {

        }

        try {
            int[][] layout2 = new int[0][];
            m = new Map("Cube World", 2, layout2);
            fail();
        } catch (IllegalArgumentException exc) {

        }

    }

    @Test
    public void testUpdateMap() {
        /**
         * Updates the map object.
         */
        int[][] layout = new int[5][];
        Map m;

        // @param name The name of the map. Name cannot be an empty string.
        try {
            m = new Map("Cube World", 4, layout);
            m.updateMap("Blokjes World", 4, layout);
            assertEquals("Blokjes World", m.getName());
        } catch (IllegalArgumentException exc) {

        }

        try {
            m = new Map("Cube World", 4, layout);
            m.updateMap("", 4, layout);
            fail();
        } catch (IllegalArgumentException exc) {

        }

        // @param layout The layout of the map. Must contain at least one item.
        try {
            int[][] layout2 = new int[3][];
            m = new Map("Cube World", 4, layout);
            m.updateMap("Cube World", 4, layout2);
            assertEquals(3, m.getLayout().length);
        } catch (IllegalArgumentException exc) {

        }
        
        try {
            m = new Map("Cube World", 4, layout);
            m.updateMap("Cube World", 4, null);
            fail();
        } catch (IllegalArgumentException exc) {

        }

        try {
            int[][] layout2 = new int[0][];
            m = new Map("Cube World", 4, layout);
            m.updateMap("Cube World", 4, layout2);
            fail();
        } catch (IllegalArgumentException exc) {

        }
        
        // @param playerAmount The amount of players of the map. playerAmount has to be 2 or 4.
         try {
            m = new Map("Cube World", 2, layout);
            m.updateMap("Cube World", 4, layout);
            assertEquals(4, m.getPlayerAmount());
        } catch (IllegalArgumentException exc) {
            
        }
        
        try {
            m = new Map("Cube World", 2, layout);
            m.updateMap("Cube World", 6, layout);
            fail();
        } catch (IllegalArgumentException exc) {
            
        }
    }
}
