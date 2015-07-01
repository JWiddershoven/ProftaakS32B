/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import Server.CollisionChecker;
import Shared.Paddle.WindowLocation;
import java.awt.Image;
import java.util.ArrayList;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javax.swing.Timer;

/**
 * @author Jelle
 */
public class Ball extends GameObject {

    private Paddle lastPaddleTouched;
    public TVector2 spawnPos;
    Timer timer;
    public static final float maxSpeed = 3f;
    private long lastTimePaddleTouched;

    /**
     * Creates a new ball object with a TVector2 and lastPaddleTouched.
     *
     * @param lastPaddleTouched The last Paddle which touched the ball.
     * @param position The position of a GameObject.
     * @param velocity The velocity of a GameObject.
     * @param size The size of a GameObject.
     * @param image
     */
    public Ball(Paddle lastPaddleTouched, TVector2 position, TVector2 velocity, TVector2 size, Image image) {
        super(position, velocity, size, image);
        this.spawnPos = position;
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
    public ArrayList<GameObject> update() {
        ArrayList<GameObject> collidedWith = new ArrayList<>();
        ArrayList<GameObject> returnList = new ArrayList<>();
        collidedWith.add(CollisionChecker.collidesWithFirst(this));
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
                    // CHECK UNSTRCUTBALE
                    if (b.isDestructable()) {
//                        Media someSound = new Media(getClass().getResource("sound.mp3").toString());
//                        if(someSound != null)
//                        {
//                            MediaPlayer mp = new MediaPlayer(someSound);
//                            mp.play();
//                        }                        
                        returnList.add(b);
                    }
                    if (lastPaddleTouched != null) {
                        if (b.getPowerUp() != null) {
                            switch (b.getPowerUp().getType()) {
                                case IncreasePaddleSize: {
                                    if (lastPaddleTouched.getWindowLocation() == WindowLocation.NORTH || lastPaddleTouched.getWindowLocation() == WindowLocation.SOUTH) {
                                        TVector2 newSize = new TVector2(150f, 20f);
                                        lastPaddleTouched.setSize(newSize);
                                        break;
                                    } else {
                                        TVector2 newSize = new TVector2(20f, 150f);
                                        lastPaddleTouched.setSize(newSize);
                                        break;
                                    }
                                }
                                case DecreasePaddleSize: {
                                    if (lastPaddleTouched.getWindowLocation() == WindowLocation.NORTH || lastPaddleTouched.getWindowLocation() == WindowLocation.SOUTH) {
                                        TVector2 newSize = new TVector2(75f, 20f);
                                        lastPaddleTouched.setSize(newSize);
                                        break;
                                    } else {
                                        TVector2 newSize = new TVector2(20f, 75f);
                                        lastPaddleTouched.setSize(newSize);
                                        break;
                                    }
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
                }
            }
        }
        TVector2 newPos = new TVector2(this.getPosition().getX() + this.getVelocity().getX(),
                this.getPosition().getY() + this.getVelocity().getY());

        this.setPosition(newPos);

        return returnList;
    }

    /**
     * Let the ball bounce
     *
     * @param go Game object to bounce
     */
    public void bounce(GameObject go) {
        TVector2 position = this.getPosition();
        TVector2 goPos = go.getMiddlePosition();
        float f1, f2;
        TVector2 vel = new TVector2(this.getVelocity().getX(), this.getVelocity().getY());

        if (go instanceof Paddle) {
            Paddle p = (Paddle) go;
            if (p.isEnabled()) {
                this.lastPaddleTouched = p;
                if (p.getWindowLocation() == Paddle.WindowLocation.NORTH || p.getWindowLocation() == Paddle.WindowLocation.SOUTH) {
                    vel.setY(bounceFloat(vel.getY()));
                }
                if (p.getWindowLocation() == Paddle.WindowLocation.WEST || p.getWindowLocation() == Paddle.WindowLocation.EAST) {
                    vel.setX(bounceFloat(vel.getX()));
                }
                this.setVelocity(vel);
            
            return;
            }
        }

        //Hit was on right
        if (getMiddlePosition().getY() > goPos.getY()) {
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

    /**
     * return new Circle(getMiddlePosition().getX(),
     * getMiddlePosition().getY(),getSize().getX() /2);
     *
     * @return return new Circle(getMiddlePosition().getX(),
     * getMiddlePosition().getY(),getSize().getX() /2);
     */
    @Override
    public Shape getBounds() {
        return new Rectangle(getPosition().getX(), getPosition().getY(), getSize().getX(), getSize().getY());
        //return new Circle(getMiddlePosition().getX(), getMiddlePosition().getY(), getSize().getX() / 2);
    }

}
