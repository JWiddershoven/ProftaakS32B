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
 * @author Lorenzo
 */
public class BlockTest {

    public BlockTest() {

    }

    @Test
    public void testBlock() {
        Block b = new Block(25, false, null, new TVector2(25.0f, 25.0f), new TVector2(0.0f, 0.0f), new TVector2(10.0f, 10.0f));
        
   }

    /**
     * Test of isDestructable method, of class Block.
     */
    @Test
    public void testIsDestructable()
    {
        System.out.println("isDestructable");
        Block instance = null;
        boolean expResult = false;
        boolean result = instance.isDestructable();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDestructable method, of class Block.
     */
    @Test
    public void testSetDestructable()
    {
        System.out.println("setDestructable");
        boolean isDestructable = false;
        Block instance = null;
        instance.setDestructable(isDestructable);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPoints method, of class Block.
     */
    @Test
    public void testGetPoints()
    {
        System.out.println("getPoints");
        Block instance = null;
        int expResult = 0;
        int result = instance.getPoints();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPoints method, of class Block.
     */
    @Test
    public void testSetPoints()
    {
        System.out.println("setPoints");
        int points = 0;
        Block instance = null;
        instance.setPoints(points);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPowerUp method, of class Block.
     */
    @Test
    public void testGetPowerUp()
    {
        System.out.println("getPowerUp");
        Block instance = null;
        PowerUp expResult = null;
        PowerUp result = instance.getPowerUp();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPowerUp method, of class Block.
     */
    @Test
    public void testSetPowerUp()
    {
        System.out.println("setPowerUp");
        PowerUp powerUp = null;
        Block instance = null;
        instance.setPowerUp(powerUp);
        fail("The test case is a prototype.");
    }

    /**
     * Test of destroyObject method, of class Block.
     */
    @Test
    public void testDestroyObject()
    {
        System.out.println("destroyObject");
        Block instance = null;
        instance.destroyObject();
        fail("The test case is a prototype.");
    }
}
