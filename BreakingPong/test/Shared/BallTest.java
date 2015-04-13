/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import Server.Server;
import java.awt.Color;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jelle TO-DO: move(), collision()
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
         * Creates a new ball object with a TVector2 and lastPaddleTouched.
         */
        try
        {
            b = new Ball(p, position, velocity, size);
        } catch (IllegalArgumentException exc)
        {

        }

        // @param lastPaddleTouched The last Paddle which touched the ball.
        try
        {
            b = new Ball(null, position, velocity, size);
            fail("lastPaddleTouched cannot be null.");
        } catch (IllegalArgumentException exc)
        {

        }

        try
        {
            b = new Ball(p, position, velocity, size);
            assertEquals(p, b.getLastPaddleTouched());
        } catch (IllegalArgumentException exc)
        {

        }

        // @param position The position of a GameObject.
        try
        {
            b = new Ball(p, null, velocity, size);
            fail("Position of a GameObject cannot be null.");
        } catch (IllegalArgumentException exc)
        {

        }

        try
        {
            b = new Ball(p, position, velocity, size);
            assertEquals(position, b.getPosition());
        } catch (IllegalArgumentException exc)
        {

        }

        // @param velocity The velocity of a GameObject.
        try
        {
            b = new Ball(p, position, null, size);
            fail("Velocity of a GameObject cannot be null.");
        } catch (IllegalArgumentException exc)
        {

        }

        try
        {
            b = new Ball(p, position, velocity, size);
            assertEquals(velocity, b.getVelocity());
        } catch (IllegalArgumentException exc)
        {

        }

        // @param size The size of a GameObject.
        try
        {
            b = new Ball(p, position, velocity, null);
            fail("Size of a GameObject cannot be null.");
        } catch (IllegalArgumentException exc)
        {

        }

        try
        {
            b = new Ball(p, position, velocity, size);
            assertEquals(size, b.getSize());
        } catch (IllegalArgumentException exc)
        {

        }
    }

    @Test
    public void testSetLastPaddleTouched()
    {

        try
        {
            Paddle p2 = new Paddle(100, position, velocity, size, cpu, Paddle.windowLocation.SOUTH, Color.BLACK);
            b = new Ball(p, position, velocity, size);
            b.setLastPaddleTouched(p2);
            assertEquals(p2, b.getLastPaddleTouched());
        } catch (IllegalArgumentException exc)
        {

        }

        try
        {
            Paddle p2 = new Paddle(100, position, velocity, size, user, Paddle.windowLocation.EAST, Color.BLACK);
            b = new Ball(p, position, velocity, size);
            b.setLastPaddleTouched(p2);
            assertEquals(p2, b.getLastPaddleTouched());
        } catch (IllegalArgumentException exc)
        {

        }

        try
        {
            b = new Ball(p, position, velocity, size);
            b.setLastPaddleTouched(null);
            fail("lastPaddleTouched cannot be null.");
        } catch (IllegalArgumentException exc)
        {

        }
    }

// Verplaatst naar startBall() timer.
//    @Test
//    public void testMoveBall() {
//
//        try {
//            TVector2 vector = new TVector2(100, 100);
//            b = new Ball(tvector2, p, position, velocity, size);
//            b.move(vector);
//            assertEquals(vector.getX(), b.getTVector2().getX(), 1);
//            assertEquals(vector.getY(), b.getTVector2().getY(), 1);
//        } catch (IllegalArgumentException exc) {
//
//        }
//
//        try {
//            TVector2 vector = new TVector2(-10, -10);
//            b = new Ball(tvector2, p, position, velocity, size);
//            b.move(vector);
//            assertEquals(vector.getX(), b.getTVector2().getX(), 1);
//            assertEquals(vector.getY(), b.getTVector2().getY(), 1);
//        } catch (IllegalArgumentException exc) {
//
//        }
//
//        try {
//            b = new Ball(tvector2, p, position, velocity, size);
//            b.move(null);
//            fail("Vector cannot be null.");
//        } catch (IllegalArgumentException exc) {
//
//        }
//    }
}
