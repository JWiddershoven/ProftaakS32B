/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import Server.CollisionChecker;
import java.awt.event.ActionEvent;
import javafx.application.Platform;
import javax.swing.Timer;

/**
 * @author Jelle
 */
public class Ball extends GameObject
{

    private Paddle lastPaddleTouched;
    private float xMovement, yMovement;
    Timer timer;

    /**
     * Creates a new ball object with a TVector2 and lastPaddleTouched.
     *
     * @param lastPaddleTouched The last Paddle which touched the ball.
     * @param position The position of a GameObject.
     * @param velocity The velocity of a GameObject.
     * @param size The size of a GameObject.
     */
    public Ball(Paddle lastPaddleTouched, TVector2 position, TVector2 velocity, TVector2 size)
    {
        super(position, velocity, size);
        if (position != null && velocity != null && size != null)
        {
            this.lastPaddleTouched = lastPaddleTouched;
        } else
        {
            throw new IllegalArgumentException();
        }

        this.xMovement = 10;
        this.yMovement = 10;
    }

    /**
     * @return The last paddle which touched the ball.
     */
    public Paddle getLastPaddleTouched()
    {
        return this.lastPaddleTouched;
    }

    /**
     * @param lastPaddleTouched The last Paddle which touched the ball.
     */
    public void setLastPaddleTouched(Paddle lastPaddleTouched)
    {
        if (lastPaddleTouched != null)
        {
            this.lastPaddleTouched = lastPaddleTouched;
        } else
        {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Timer which handles the collision between a ball and the borders.
     */
    public void startBall()
    {
        timer = new Timer(10, (ActionEvent e) ->
        {

            GameObject collidedWith = CollisionChecker.collidesWith(this);
            // If ball collides with something that is not a WhiteSpace.
            if (collidedWith != null && !collidedWith.getClass().equals(WhiteSpace.class))
            {
                bounce(collidedWith);
            }

            TVector2 newPos = new TVector2(this.getPosition().getX() + this.getVelocity().getX(),
                    this.getPosition().getY() + this.getVelocity().getY());
            // Increments x/y coordinates with (positive or negative) movement.
            // Do so in Platform.runLater in order to avoid changing a javaFX object while not on application thread
            Platform.runLater(() ->
            {
                this.setPosition(newPos);
            });
        });
        timer.start();
    }

    public void bounce(GameObject go)
    {
        TVector2 goPos = go.getPosition();
        
        float deltaX = go.getPosition().getX() - this.getPosition().getX();
        float deltaY = go.getPosition().getY() - this.getPosition().getY();
        double angleInDegrees = Math.atan2(deltaY, deltaX) * 180 / Math.PI;
        TVector2 vel = this.getVelocity();
        TVector2 newVelocity = new TVector2((vel.getX() - vel.getX() - vel.getX()), (vel.getY() - vel.getY() - vel.getY()));
        this.setVelocity(newVelocity);
    }

    /**
     * Handles the collision between the ball and an object.
     *
     * @param tvector2 The TVector2 of the ball.
     */
    public void collision(TVector2 tvector2)
    {

    }
}
