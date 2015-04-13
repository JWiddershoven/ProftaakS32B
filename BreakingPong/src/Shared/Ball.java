/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

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
            float velocityX = this.getVelocity().getX();
            float velocityY = this.getVelocity().getY();
            
            // reverse x bounce movement whenever the ball would cross the right border on its current course
            if (this.getVelocity().getX() > 0 && this.getVelocity().getX() + xMovement + (this.getSize().getX() / 2) > 800)
            {
                velocityX *= -1;
                this.setVelocity(new TVector2(velocityX, velocityY));
            }
            // reverses x bounce movement if ball would cross left border
            if (xMovement < 0 && this.getVelocity().getX() + xMovement - (this.getSize().getX() / 2) < 0)
            {
                velocityX *= -1;
                this.setVelocity(new TVector2(velocityX, velocityY));
            }
            // reverses y bounce movement if ball would cross bottom border
            if (yMovement > 0 && this.getVelocity().getY() + yMovement + (this.getSize().getX() / 2) > 800)
            {
                velocityY *= -1;
                this.setVelocity(new TVector2(velocityX, velocityY));
            }
            // reverses y bounce movement if ball would cross top border
            if (yMovement < 0 && this.getVelocity().getY() + yMovement - (this.getSize().getX() / 2) < 0)
            {
                velocityY *= -1;
                this.setVelocity(new TVector2(velocityX, velocityY));
            }
            TVector2 newPos = new TVector2(this.getPosition().getX() + velocityX, this.getPosition().getY() + velocityY);
            // Increments x/y coordinates with (positive or negative) movement.
            // Do so in Platform.runLater in order to avoid changing a javaFX object while not on application thread
            Platform.runLater(() ->
            {
                this.setPosition(newPos);
                this.getVelocity().setX(getVelocity().getX());
                this.getVelocity().setY(getVelocity().getY());
            });
        });
        timer.start();
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
