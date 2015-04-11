/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jordi
 */
public class GameTest {
    private Game firstGame;
    public GameTest() {
    }
    
    @Before
    public void setUp() 
    {
        firstGame = new Game(1,180,false);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testGetGameID()
    {
        assertEquals("Game ID isn't correct", 1,firstGame.getId());
    }
    
    @Test
    public void testSetGameID()
    {
        firstGame.setId(2);
        assertEquals("Game ID hasn't changed", 2, firstGame.getId());
    }
    
    @Test
    public void testGetGameTime()
    {
        assertEquals("Gametime isn't correct", 180, firstGame.getGameTime());
    }
    
    @Test
    public void testSetGameTime()
    {
        firstGame.setGameTime(60);
        assertEquals("Gametime hasn't changed", 60, firstGame.getGameTime());
    }
    
    @Test
    public void testGetPowerUps()
    {
        assertFalse("Power-ups isn't correct", firstGame.getPowerUps());
    }
    
    @Test
    public void testSetPowerUps()
    {
        try
        {
            firstGame.setPowerUps(true);
        }
        catch(IllegalArgumentException iaex)
        {
            fail("Power-ups hasn't been set correctly");
        }
        
        try
        {
            firstGame.setPowerUps(false);
        }
        catch(IllegalArgumentException iaex)
        {
            fail("Power-ups hasn't been set correctly");
        }
    }
    
    @Test
    public void testAddBot()
    {
        try
        {
            byte b = 1;
            firstGame.addBot("TestBot",b );
        }
        catch(IllegalArgumentException iaex)
        {
            fail("Can't add TestBot to game");
        }
    }
    
    @Test
    public void testRemoveBot()
    {
        try
        {
            firstGame.removeBot("TestBot");
        }catch(IllegalArgumentException iaex)
        {
            fail("TestBot hasn't been removed");
        }
    }
}
