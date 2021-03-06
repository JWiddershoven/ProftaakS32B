/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import Server.Game;
import Server.CollisionChecker;
import Server.Server;
import java.rmi.RemoteException;
import java.util.ArrayList;
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
    public void setUp() throws RemoteException
    {
        testGame = new Game(1, 180, false, new ArrayList<User>());
        server = new Server();
        cpu = new CPU("Bot", (byte) 5);
        user = new User("Jelle123", "wachtwoord123", "jelle@gmail.com");
        position = new TVector2(50, 30);
        velocity = new TVector2(15, 40);
        size = new TVector2(5, 5);
        p = new Paddle(33, position, velocity, size, cpu, Paddle.WindowLocation.NORTH, null);
    }

    @Test
    public void testBall()
    {
        /**
         * Creates a new ball object with a paddle, position, velocity and size.
         */
        try
        {
            b = new Ball(p, position, velocity, size,null);
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
            b = new Ball(p, null, velocity, size, null);
            fail("Position of a GameObject cannot be null.");
        }
        catch (IllegalArgumentException exc)
        {

        }

        // @param velocity The velocity of a GameObject.
        try
        {
            b = new Ball(p, position, null, size, null);
            fail("Velocity of a GameObject cannot be null.");
        }
        catch (IllegalArgumentException exc)
        {

        }

        // @param size The size of a GameObject.
        try
        {
            b = new Ball(p, position, velocity, null, null);
            fail("Size of a GameObject cannot be null.");
        }
        catch (IllegalArgumentException exc)
        {

        }
    }

    @Test
    public void testSetLastPaddleTouched()
    {
        b = new Ball(p, position, velocity, size, null);
        Paddle p2 = new Paddle(100, position, velocity, size, cpu, Paddle.WindowLocation.SOUTH, null);
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
        b = new Ball(p, position, velocity, size, null);
        Block block = new Block(1,0, false, null, new TVector2(21, 11), TVector2.zero, new TVector2(300, 300), null);
        CollisionChecker.gameObjectsList.add(b);
        CollisionChecker.gameObjectsList.add(block);
        b.update();
        b.update();
        b.update();
        b.update();
        assertEquals("Bounce was incorrect", new TVector2(-1.0f, 1.0f).toString(), b.getVelocity().toString());
        CollisionChecker.gameObjectsList.clear();
    }
}
