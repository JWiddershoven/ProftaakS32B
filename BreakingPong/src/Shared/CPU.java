/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mnesymne
 */
public class CPU implements Serializable {

    private final String name;
    private final Byte difficulty;

    private Paddle myPaddle;

    private transient Ball closestBall;
    private ArrayList<Ball> currentPosBall;

    /**
     * The constructor
     *
     * @param name Name of the CPU
     * @param difficulty The difficulty level
     */
    public CPU(String name, Byte difficulty) {
        this.name = name;
        this.difficulty = difficulty;
    }

    /**
     * Get this paddle
     *
     * @return THe paddle
     */
    public Paddle getMyPaddle() {
        return myPaddle;
    }

    /**
     * Set the paddle
     *
     * @param myPaddle The new paddle
     */
    public void setMyPaddle(Paddle myPaddle) {
        this.myPaddle = myPaddle;
    }

    /**
     * Get name of the CPU
     *
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the difficlty
     *
     * @return The difficulty
     */
    public byte getDifficuly() {
        return this.difficulty;
    }

    /**
     * Called every update
     *
     * @param balls
     */
    public void update(ArrayList<Ball> balls, ArrayList<GameObject> gameObjects) {
        currentPosBall = balls;
        setClosestBall();
        if (closestBall != null) {
            try {
                Move(gameObjects);
            } catch (Exception ex) {
                Logger.getLogger(CPU.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Set the closest ball
     */
    private void setClosestBall() {
        closestBall = null;
        //System.out.println("Paddle:" + myPaddle.getPosition().toString());
        for (Ball b : currentPosBall) {
            //System.out.println("Ball pos: " + b.getPosition().toString());
            if (closestBall == null) {
                closestBall = b;
            }
            try {
                if (b != null && closestBall != null) {
                    if (getDistance(b.getMiddlePosition()) < getDistance(closestBall.getMiddlePosition())) {
                        closestBall = b;
                    }
                }
            } catch (Exception ex) {
                System.out.println("ERROR in setClosest ball " + ex.getMessage());
                Logger.getLogger(CPU.class.getName()).log(Level.SEVERE, null, ex);
                if (b != null && closestBall != null) {
                    if (getDistance(b.getMiddlePosition()) < getDistance(closestBall.getMiddlePosition())) {
                        closestBall = b;
                    }
                }
            }
        }
        //System.out.println("Closest pos: " + closestBall.getPosition().toString());
    }

    /**
     * Get the distance
     *
     * @param targetPos The target TVector2
     * @return The distance
     */
    private float getDistance(TVector2 targetPos) {
        TVector2 pos = myPaddle.getMiddlePosition();
        float distance = 0.0f;
        if (pos.getX() > targetPos.getX()) {
            distance += pos.getX() - targetPos.getX();
        } else {
            distance += targetPos.getX() - pos.getX();
        }
        if (pos.getY() > targetPos.getY()) {
            distance += pos.getY() - targetPos.getY();
        } else {
            distance += targetPos.getY() - pos.getY();
        }
        //System.out.println(distance);
        return distance;
    }

    /**
     * Methode for calling the move methode with difference for the position of
     * the CPU
     *
     * @throws java.lang.Exception
     */
    public void Move(ArrayList<GameObject> gameObjects) throws Exception {
        if (this.closestBall == null) {
            throw new Exception("closestBall is null in Move()");
        }
        if (this.myPaddle == null) {
            throw new Exception("myPaddle is null in Move()");
        }

        try {
            if (this.myPaddle.getWindowLocation() == Paddle.WindowLocation.SOUTH) {
                if (closestBall.getPosition().getX() > this.myPaddle.getPosition().getX() + (this.myPaddle.getSize().getX() / 2)) {
                    myPaddle.Move(Paddle.Direction.RIGHT, gameObjects);
                } else {
                    myPaddle.Move(Paddle.Direction.LEFT, gameObjects);
                }
            } else if (this.myPaddle.getWindowLocation() == Paddle.WindowLocation.NORTH) {
                if (closestBall.getPosition().getX() > this.myPaddle.getPosition().getX() + (this.myPaddle.getSize().getX() / 2)) {
                    myPaddle.Move(Paddle.Direction.RIGHT, gameObjects);
                } else {
                    myPaddle.Move(Paddle.Direction.LEFT, gameObjects);
                }
            } else if (this.myPaddle.getWindowLocation() == Paddle.WindowLocation.WEST) {
                if (closestBall.getPosition().getY() > this.myPaddle.getPosition().getY() + (this.myPaddle.getSize().getY() / 2)) {
                    myPaddle.Move(Paddle.Direction.UP, gameObjects);
                } else {
                    myPaddle.Move(Paddle.Direction.DOWN, gameObjects);
                }
            } else if (this.myPaddle.getWindowLocation() == Paddle.WindowLocation.EAST) {
                if (closestBall.getPosition().getY() > this.myPaddle.getPosition().getY() + (this.myPaddle.getSize().getY() / 2)) {
                    myPaddle.Move(Paddle.Direction.UP, gameObjects);
                } else {
                    myPaddle.Move(Paddle.Direction.DOWN, gameObjects);
                }
            }
        } catch (Exception e) {
            System.out.println("CPU.Move() ERROR ! : " + e.getMessage());
            if (this.myPaddle.getWindowLocation() == Paddle.WindowLocation.SOUTH) {
                if (closestBall.getPosition().getX() > this.myPaddle.getPosition().getX() + (this.myPaddle.getSize().getX() / 2)) {
                    myPaddle.Move(Paddle.Direction.RIGHT, gameObjects);
                } else {
                    myPaddle.Move(Paddle.Direction.LEFT, gameObjects);
                }
            } else if (this.myPaddle.getWindowLocation() == Paddle.WindowLocation.NORTH) {
                if (closestBall.getPosition().getX() > this.myPaddle.getPosition().getX() + (this.myPaddle.getSize().getX() / 2)) {
                    myPaddle.Move(Paddle.Direction.RIGHT, gameObjects);
                } else {
                    myPaddle.Move(Paddle.Direction.LEFT, gameObjects);
                }
            } else if (this.myPaddle.getWindowLocation() == Paddle.WindowLocation.WEST) {
                if (closestBall.getPosition().getY() > this.myPaddle.getPosition().getY() + (this.myPaddle.getSize().getY() / 2)) {
                    myPaddle.Move(Paddle.Direction.UP, gameObjects);
                } else {
                    myPaddle.Move(Paddle.Direction.DOWN, gameObjects);
                }
            } else if (this.myPaddle.getWindowLocation() == Paddle.WindowLocation.EAST) {
                if (closestBall.getPosition().getY() > this.myPaddle.getPosition().getY() + (this.myPaddle.getSize().getY() / 2)) {
                    myPaddle.Move(Paddle.Direction.UP, gameObjects);
                } else {
                    myPaddle.Move(Paddle.Direction.DOWN, gameObjects);
                }
            }
        }
    }

}
