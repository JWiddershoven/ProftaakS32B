/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import Server.CollisionChecker;
import java.awt.Color;
import java.util.Date;
import java.util.ArrayList;
import javafx.application.Platform;
import javax.swing.Timer;

/**
 * @author Jelle
 */
public class Ball extends GameObject {

    private final Game game;
    private Paddle lastPaddleTouched;
    Timer timer;
    public static final float maxSpeed = 1.5f;
    private long lastTimePaddleTouched;

    /**
     * Creates a new ball object with a TVector2 and lastPaddleTouched.
     *
     * @param lastPaddleTouched The last Paddle which touched the ball.
     * @param position The position of a GameObject.
     * @param velocity The velocity of a GameObject.
     * @param size The size of a GameObject.
     * @param game The game of where this ball is in.
     * @param color JAVA AWT
     */
    public Ball(Paddle lastPaddleTouched, TVector2 position, TVector2 velocity, TVector2 size, Game game, Color color) {
        super(position, velocity, size, color);
        this.game = game;
        if (position != null && velocity != null && size != null) {
            this.lastPaddleTouched = lastPaddleTouched;
        } else {
            throw new IllegalArgumentException();
        }
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
     * updates the position of the ball
     */
    public void update() {
        ArrayList<GameObject> collidedWith = new ArrayList<>();
        collidedWith.addAll(CollisionChecker.collidesWithMultiple(this));
        boolean hasBounced = false;
        // If ball collides with something that is not a WhiteSpace.
        for (GameObject go : collidedWith) {
            if (go != null && !go.getClass().equals(WhiteSpace.class)) {
                //System.out.println("Collided" + go.toString());
                if (hasBounced == false) {
                    bounce(go);
                    hasBounced = true;
                }
                if (go.getClass().equals(Block.class)) {
                    Block b = (Block) go;
                    if (lastPaddleTouched != null) {
                        if (b.getPowerUp() != null) {
                            switch (b.getPowerUp().getType()) {
                                case IncreasePaddleSize: {
                                    TVector2 newSize = new TVector2(175f, 20f);
                                    lastPaddleTouched.setSize(newSize);
                                    break;
                                }
                                case DecreasePaddleSize: {
                                    TVector2 newSize = new TVector2(75f, 20f);
                                    lastPaddleTouched.setSize(newSize);
                                    break;
                                }
                                case IncreaseBallSize: {
                                    TVector2 newSize = new TVector2(30f, 30f);
                                    this.setSize(newSize);
                                    break;
                                }
                                case DecreaseBallSize: {
                                    TVector2 newSize = new TVector2(7.5f, 7.5f);
                                    this.setSize(newSize);
                                    break;
                                }
                                case IncreaseBallSpeed: {
                                    TVector2 newSpeed = new TVector2(1.5f, 1.5f);
                                    this.setVelocity(newSpeed);
                                    break;
                                }
                                case DecreaseBallSpeed: {
                                    TVector2 newSpeed = new TVector2(0.5f, 0.5f);
                                    this.setVelocity(newSpeed);
                                    break;
                                }
                            }
                        }
                        lastPaddleTouched.addScore(b.getPoints());
                    }
                    if (b.isDestructable()) {
                        game.objectList.remove(go);
                        CollisionChecker.gameObjectsList.remove(go);
                    }
                }
            }
        }
        TVector2 newPos = new TVector2(this.getPosition().getX() + this.getVelocity().getX(),
                this.getPosition().getY() + this.getVelocity().getY());
        // Increments x/y coordinates with (positive or negative) movement.
        // Do so in Platform.runLater in order to avoid changing a javaFX object while not on application thread

        Platform.runLater(() -> {
            this.setPosition(newPos);
        });

        //Check if the ball is out of the screen bounds
        if (this.getPosition().getX() < this.game.getWidth() || this.getPosition().getX() > this.game.getWidth() || this.getPosition().getY() < this.game.getHeight() || this.getPosition().getY() > this.game.getHeight()) {

        }
    }

    /**
     *
     * @param go
     */
    public void bounce(GameObject go) {
        TVector2 position = this.getPosition();
        TVector2 goPos = go.getMiddlePosition();
        float f1, f2;
        TVector2 vel = new TVector2(this.getVelocity().getX(), this.getVelocity().getY());

        if (go instanceof Paddle) {
            Paddle p = (Paddle) go;
            this.lastPaddleTouched = p;
            if (p.getWindowLocation() == Paddle.windowLocation.NORTH || p.getWindowLocation() == Paddle.windowLocation.SOUTH) {
                vel.setY(bounceFloat(vel.getY()));
            }
            if (p.getWindowLocation() == Paddle.windowLocation.WEST || p.getWindowLocation() == Paddle.windowLocation.EAST) {
                vel.setX(bounceFloat(vel.getX()));
            }
            this.setVelocity(vel);
            System.out.println("Hit");
            return;
        }

           
            if (getMiddlePosition().getY()  > goPos.getY()) {
                f1 = goPos.getY() - getMiddlePosition().getY();
            } else {
                f1 = getMiddlePosition().getY() - goPos.getY();
            }
            if (getMiddlePosition().getX() > goPos.getX()) {
                f2 = goPos.getX() - getMiddlePosition().getX();
            } else {
                f2 = getMiddlePosition().getX() - goPos.getX();
            }
        if (f1 < f2) {
            vel.setY(bounceFloat(this.getVelocity().getY()));
        } else {
            vel.setX(bounceFloat(this.getVelocity().getX()));
        }
    

        this.setVelocity(vel);

    }

    /**
     *
     * @param x
     * @return
     */
    public float bounceFloat(float x) {
        x *= -1;
        return x;
    }

}
