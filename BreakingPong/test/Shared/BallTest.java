/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import Server.CollisionChecker;
import Server.Server;
import java.awt.Color;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jelle
 */
public class BallTest
{

    private Ball b;
    private Game testGame;
    private CPU cpu;
    private User user;
    private TVector2 position;
    private TVector2 velocity;
    private TVector2 size;
    private Paddle p;
    private Server server;

    @Before
    public void setUp()
    {
        testGame = new Game(1, 180, false);
        server = new Server();
        cpu = new CPU("Bot", (byte) 5, testGame);
        user = new User("Jelle123", "wachtwoord123", "jelle@gmail.com", server);
        position = new TVector2(50, 30);
        velocity = new TVector2(15, 40);
        size = new TVector2(5, 5);
        p = new Paddle(33, position, velocity, size, cpu, Paddle.windowLocation.NORTH, Color.BLACK);
    }

    @Test
    public void testBall()
    {
        /**
         * Creates a new ball object with a paddle, position, velocity and size.
         */
        try
        {
            b = new Ball(p, position, velocity, size, testGame, Color.RED);
            assertEquals(p, b.getLastPaddleTouched());
            assertEquals(position, b.getPosition());
            assertEquals(velocity, b.getVelocity());
            assertEquals(size, b.getSize());
        }
        catch (IllegalArgumentException exc)
        {
            fail("Constructor failure!");
        }

        // @param position The position of a GameObject.
        try
        {
            b = new Ball(p, null, velocity, size, testGame, Color.RED);
            fail("Position of a GameObject cannot be null.");
        }
        catch (IllegalArgumentException exc)
        {

        }

        // @param velocity The velocity of a GameObject.
        try
        {
            b = new Ball(p, position, null, size, testGame, Color.RED);
            fail("Velocity of a GameObject cannot be null.");
        }
        catch (IllegalArgumentException exc)
        {

        }

        // @param size The size of a GameObject.
        try
        {
            b = new Ball(p, position, velocity, null, testGame, Color.RED);
            fail("Size of a GameObject cannot be null.");
        }
        catch (IllegalArgumentException exc)
        {

        }
    }

    @Test
    public void testSetLastPaddleTouched()
    {
        b = new Ball(p, position, velocity, size, testGame, Color.RED);
        Paddle p2 = new Paddle(100, position, velocity, size, cpu, Paddle.windowLocation.SOUTH, Color.BLACK);
        try
        {
            b.setLastPaddleTouched(p2);
            assertEquals(p2, b.getLastPaddleTouched());
        }
        catch (IllegalArgumentException exc)
        {

        }

        try
        {
            b.setLastPaddleTouched(null);
            fail("lastPaddleTouched cannot be null.");
        }
        catch (IllegalArgumentException exc)
        {

        }
    }

    @Test
    public void testBallCollision()
    {
        velocity = new TVector2(1, 1);
        size = new TVector2(10, 10);
        position = new TVector2(10, 10);
        b = new Ball(p, position, velocity, size, testGame, Color.RED);
        Block block = new Block(0, false, null, new TVector2(21, 11), TVector2.zero, new TVector2(300, 300), Color.YELLOW);
        CollisionChecker.gameObjectsList.add(b);
        CollisionChecker.gameObjectsList.add(block);
        b.update();
        b.update();
        b.update();
        b.update();
        assertEquals("Bounce was incorrect", new TVector2(-1, 1), b.getVelocity());
        CollisionChecker.gameObjectsList.clear();
    }
}
