/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import javafx.application.Platform;

/**
 * @author Jelle
 */
public class Ball extends GameObject {

    private Paddle lastPaddleTouched;
    private TVector2 tvector2;
    private float xMovement, yMovement;

    /**
     * Creates a new ball object with a TVector2 and lastPaddleTouched.
     *
     * @param tvector2 The TVector2 of the ball.
     * @param lastPaddleTouched The last Paddle which touched the ball.
     * @param position The position of a GameObject.
     * @param velocity The velocity of a GameObject.
     * @param size The size of a GameObject.
     */
    public Ball(TVector2 tvector2, Paddle lastPaddleTouched, TVector2 position, TVector2 velocity, TVector2 size) {
        super(position, velocity, size);
        if (tvector2 != null && lastPaddleTouched != null && position != null && velocity != null && size != null) {
            this.tvector2 = tvector2;
            this.lastPaddleTouched = lastPaddleTouched;
        } else {
            throw new IllegalArgumentException();
        }

        this.xMovement = 10;
        this.yMovement = 10;
    }

    /**
     * @return The last paddle which touched the ball.
     */
    public Paddle getLastPaddleTouched() {
        return this.lastPaddleTouched;
    }

    /**
     * @param lastPaddleTouched The last Paddle which touched the ball.
     */
    public void setLastPaddleTouched(Paddle lastPaddleTouched) {
        if (lastPaddleTouched != null) {
            this.lastPaddleTouched = lastPaddleTouched;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @return The TVector2 of the ball.
     */
    public TVector2 getTVector2() {
        return this.tvector2;
    }

    /**
     * Moves the ball in the specified direction.
     *
     * @param tvector2 The TVector2 of the ball.
     */
    public void move(TVector2 tvector2) {
        if (tvector2 != null) {
            this.tvector2 = tvector2;
        } else {
            throw new IllegalArgumentException();
        }

        /* Radius & sceneHeight + sceneWidth aanpassen.
        // reverse x bounce movement whenever the ball would cross the right border on its current course
        if (xMovement > 0 && this.tvector2.getX() + xMovement + radius.get() > sceneWidth.get()) {
            xMovement *= -1;
        }
        // reverses x bounce movement if ball would cross left border
        if (xMovement < 0 && this.tvector2.getX() + xMovement - radius.get() < 0) {
            xMovement *= -1;
        }
        // reverses y bounce movement if ball would cross bottom border
        if (yMovement > 0 && this.tvector2.getY() + yMovement + radius.get() > sceneHeight.get()) {
            yMovement *= -1;
        }
        // reverses y bounce movement if ball would cross top border
        if (yMovement < 0 && this.tvector2.getY() + yMovement - radius.get() < 0) {
            yMovement *= -1;
        }
        */

        // Increments x/y coordinates with (positive or negative) movement.
        // Do so in Platform.runLater in order to avoid changing a javaFX object while not on application thread
        Platform.runLater(() -> {
            this.tvector2.setX(tvector2.getX() + xMovement);
            this.tvector2.setY(tvector2.getY() + yMovement);
        });
    }

    /**
     * Handles the collision between the ball and an object.
     *
     * @param tvector2 The TVector2 of the ball.
     */
    public void collision(TVector2 tvector2) {

    }
}
