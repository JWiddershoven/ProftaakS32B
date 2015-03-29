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
 * @author Jelle TO-DO: move(), collision()
 */
public class BallTest {

    @Test
    public void testBall() {
        /**
         * Creates a new ball object with a TVector2 and lastPaddleTouched.
         */
        Ball b;
        TVector2 tvector2 = new TVector2(10, 10);
        TVector2 position = new TVector2(50, 30);
        TVector2 velocity = new TVector2(15, 40);
        TVector2 size = new TVector2(5, 5);
        Paddle p = new Paddle(33, position, velocity, size);

        try {
            b = new Ball(tvector2, p, position, velocity, size);
        } catch (IllegalArgumentException exc) {

        }

        // @param tvector2 The TVector2 of the ball.
        try {
            b = new Ball(null, p, position, velocity, size);
            fail();
        } catch (IllegalArgumentException exc) {

        }

        try {
            b = new Ball(tvector2, p, position, velocity, size);
            assertEquals(tvector2, b.getTVector2());
        } catch (IllegalArgumentException exc) {

        }

        // @param lastPaddleTouched The last Paddle which touched the ball.
        try {
            b = new Ball(tvector2, null, position, velocity, size);
            fail();
        } catch (IllegalArgumentException exc) {

        }

        try {
            b = new Ball(tvector2, p, position, velocity, size);
            assertEquals(p, b.getLastPaddleTouched());
        } catch (IllegalArgumentException exc) {

        }

        // @param position The position of a GameObject.
        try {
            b = new Ball(tvector2, p, null, velocity, size);
            fail();
        } catch (IllegalArgumentException exc) {

        }

        try {
            b = new Ball(tvector2, p, position, velocity, size);
            assertEquals(position, b.getPosition());
        } catch (IllegalArgumentException exc) {

        }

        // @param velocity The velocity of a GameObject.
        try {
            b = new Ball(tvector2, p, position, null, size);
            fail();
        } catch (IllegalArgumentException exc) {

        }

        try {
            b = new Ball(tvector2, p, position, velocity, size);
            assertEquals(velocity, b.getVelocity());
        } catch (IllegalArgumentException exc) {

        }

        // @param size The size of a GameObject.
        try {
            b = new Ball(tvector2, p, position, velocity, null);
            fail();
        } catch (IllegalArgumentException exc) {

        }

        try {
            b = new Ball(tvector2, p, position, velocity, size);
            assertEquals(size, b.getSize());
        } catch (IllegalArgumentException exc) {

        }
    }

    @Test
    public void testSetLastPaddleTouched() {

        Ball b;
        TVector2 tvector2 = new TVector2(10, 10);
        TVector2 position = new TVector2(50, 30);
        TVector2 velocity = new TVector2(15, 40);
        TVector2 size = new TVector2(5, 5);
        Paddle p = new Paddle(33, position, velocity, size);

        try {
            Paddle p2 = new Paddle(100, position, velocity, size);
            b = new Ball(tvector2, p, position, velocity, size);
            b.setLastPaddleTouched(p2);
            assertEquals(p2, b.getLastPaddleTouched());
        } catch (IllegalArgumentException exc) {

        }

        try {
            b = new Ball(tvector2, p, position, velocity, size);
            b.setLastPaddleTouched(null);
            fail();
        } catch (IllegalArgumentException exc) {

        }
    }

    @Test
    public void testMoveBall() {

        Ball b;
        TVector2 tvector2 = new TVector2(10, 10);
        TVector2 position = new TVector2(50, 30);
        TVector2 velocity = new TVector2(15, 40);
        TVector2 size = new TVector2(5, 5);
        Paddle p = new Paddle(33, position, velocity, size);

        try {
            TVector2 vector = new TVector2(100, 100);
            b = new Ball(tvector2, p, position, velocity, size);
            b.move(vector);
            assertEquals(vector.getX(), b.getTVector2().getX(), 1);
            assertEquals(vector.getY(), b.getTVector2().getY(), 1);
        } catch (IllegalArgumentException exc) {

        }

        try {
            TVector2 vector = new TVector2(-10, -10);
            b = new Ball(tvector2, p, position, velocity, size);
            b.move(vector);
            assertEquals(vector.getX(), b.getTVector2().getX(), 1);
            assertEquals(vector.getY(), b.getTVector2().getY(), 1);
        } catch (IllegalArgumentException exc) {

        }
        
        try {
            b = new Ball(tvector2, p, position, velocity, size);
            b.move(null);
            fail("Vector cannot be null.");
        } catch (IllegalArgumentException exc) {

        }
    }

}
