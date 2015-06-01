package Shared;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Server.Game;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import Server.Server;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Jordi
 */
public class PaddleTest {
    private User player1, player2, player3;
    private Paddle paddle1, paddle2, paddle3;
    private TVector2 standardSize, position, velocity;
    private Server selectedServer;
    private Game game;
    public PaddleTest() {
    }
    
    @Before
    public void setUp() throws RemoteException {
        selectedServer = new Server();
        game  = new Game(1, 300,true, new ArrayList<User>());
        
        player1 = new User("PongLord666","123456", "Email@Pong.com");
        player2 = new User("TestAccount1", "Test01", "Test@Pong.com");
        player3 = new User("TestAccount2", "Test02", "Anothertest@Pong.com");
        
        standardSize = new TVector2(25,10);
        position = new TVector2(50,50);
        velocity = new TVector2(10,10);
        paddle1 = new Paddle(10, position, velocity, standardSize, player1, Paddle.WindowLocation.NORTH, null);
        game.addObject(paddle1);
        
        
        position = new TVector2(75,75);
        velocity = new TVector2(100,100);
        paddle2 = new Paddle(20, position, velocity, standardSize, player2, Paddle.WindowLocation.SOUTH, null);
        game.addObject(paddle2);
        
        position = new TVector2(100,100);
        velocity = new TVector2(50,50);
        paddle3 = new Paddle(30, position, velocity, standardSize, player3, Paddle.WindowLocation.EAST, null);
        game.addObject(paddle3);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testGetScore() 
    {
        assertEquals("Score of paddle1 is incorrect", 10, paddle1.getScore());
        assertEquals("Score of paddle2 is incorrect", 20, paddle2.getScore());
        assertEquals("Score of paddle3 is incorrect", 30, paddle3.getScore());
    }
    
    @Test
    public void testSetScore()
    {
        paddle1.addScore(50);
        paddle2.addScore(10000);
        paddle3.addScore(15000000);
        
        assertEquals("Score of paddle1 isn't set correctly", 60, paddle1.getScore());
        assertEquals("Score of paddle2 isn't set correctly", 10020, paddle2.getScore());
        assertEquals("Score of paddle3 isn't set correctly", 15000030, paddle3.getScore());
        
        paddle1.addScore(-1);
        paddle2.addScore(-1000);
        
        assertEquals("Score of paddle1 didn't go down", 59, paddle1.getScore());
        assertEquals("Score of paddle2 didn't go down", 9020, paddle2.getScore());
    }
    
    @Test
    public void testGetPosition()
    {
        TVector2 expected = new TVector2(50,50);
        try
        {
            assertEquals(expected.getX(),paddle1.getPosition().getX(),1);
            assertEquals(expected.getY(),paddle1.getPosition().getY(),1);
        }
        catch(IllegalArgumentException iaex)
        {
            fail("paddle1 X or Y position incorrect");
        }
        
        expected = new TVector2(75,75);
        try
        {
            assertEquals(expected.getX(), paddle2.getPosition().getX(),1);
            assertEquals(expected.getY(), paddle2.getPosition().getY(),1);
        }
        catch(IllegalArgumentException iaex)
        {
            fail("paddle2 X or Y incorrect");
        }

        expected = new TVector2(100,100);
        try
        {
            assertEquals(expected.getX(), paddle3.getPosition().getX(),1);
            assertEquals(expected.getY(), paddle3.getPosition().getY(),1);
        }
        catch(IllegalArgumentException iaex)
        {
            fail("paddle3 X or Y incorrect");
        }
    }
    
    @Test
    public void testMove()
    {
        TVector2 expected = new TVector2(49,50);
        paddle1.Move(Paddle.Direction.LEFT);
        try
        {
            assertEquals(expected.getX(),paddle1.getPosition().getX(),1);
            assertEquals(expected.getY(),paddle1.getPosition().getY(),1);
        }
        catch(IllegalArgumentException iaex)
        {
            fail("paddle1 X or Y position incorrect");
        }
        
        expected = new TVector2(50,50);
        paddle1.Move(Paddle.Direction.RIGHT);
        try
        {
            assertEquals(expected.getX(),paddle1.getPosition().getX(),1);
            assertEquals(expected.getY(),paddle1.getPosition().getY(),1);
        }
        catch(IllegalArgumentException iaex)
        {
            fail("paddle1 X or Y position incorrect");
        }
        
        expected = new TVector2(50,49);
        paddle1.Move(Paddle.Direction.DOWN);
        try
        {
            assertEquals(expected.getX(),paddle1.getPosition().getX(),1);
            assertEquals(expected.getY(),paddle1.getPosition().getY(),1);
        }
        catch(IllegalArgumentException iaex)
        {
            fail("paddle1 X or Y position incorrect");
        }
        
        expected = new TVector2(50,50);
        paddle1.Move(Paddle.Direction.UP);
        try
        {
            assertEquals(expected.getX(),paddle1.getPosition().getX(),1);
            assertEquals(expected.getY(),paddle1.getPosition().getY(),1);
        }
        catch(IllegalArgumentException iaex)
        {
            fail("paddle1 X or Y position incorrect");
        }
    }
    
    @Test
    public void testGetSize()
    {
        TVector2 expected = new TVector2(25,10);
        try
        {
            assertEquals(expected.getX(),paddle1.getSize().getX(),1);
            assertEquals(expected.getY(),paddle1.getSize().getY(),1);
        }
        catch(IllegalArgumentException iaex)
        {
            fail("paddle1 size incorrect");
        }
    }
    
    @Test
    public void testSetSize()
    {
        TVector2 expected = new TVector2(10,25);
        paddle1.setSize(expected);
        
        try
        {
            assertEquals(expected.getX(), paddle1.getSize().getX(),1);
            assertEquals(expected.getY(), paddle1.getSize().getY(),1);
        }
        catch(IllegalArgumentException iaex)
        {
            
        }
    }
    
    @Test
    public void testGetVelocity()
    {
        TVector2 expected = new TVector2(10,10);
        try
        {
            assertEquals(expected.getX(),paddle1.getVelocity().getX(),1);
            assertEquals(expected.getY(),paddle1.getVelocity().getY(),1);
        }
        catch(IllegalArgumentException iaex)
        {
            fail("paddle1 size incorrect");
        } 
    }
    
    @Test
    public void testSetVelocity()
    {
        TVector2 expected = new TVector2(10,25);
        paddle1.setVelocity(expected);
        
        try
        {
            assertEquals(expected.getX(), paddle1.getVelocity().getX(),1);
            assertEquals(expected.getY(), paddle1.getVelocity().getY(),1);
        }
        catch(IllegalArgumentException iaex)
        {
            
        }
    }
}
