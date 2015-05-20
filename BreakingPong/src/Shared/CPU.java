/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import java.util.ArrayList;

/**
 *
 * @author Mnesymne
 */
public class CPU
{

    private final String name;
    private final Byte difficulty; // Currently not used
    private final Game currentGame;

    private Paddle myPaddle; // CPU PADDLE KOPPLING VIA PADDLE KLASSE

    private Ball closestBall;
    private ArrayList<Ball> currentPosBall;

    public CPU(String name, Byte difficulty, Game myGame)
    {
        this.name = name;
        this.difficulty = difficulty;
        this.currentGame = myGame;
    }

    public Paddle getMyPaddle()
    {
        return myPaddle;
    }
    public void setMyPaddle(Paddle myPaddle)
    {
        this.myPaddle = myPaddle;
    }

    public String getName()
    {
        return name;
    }

    public byte getDifficuly()
    {
        return this.difficulty;
    }

    /**
     * Called every update
     */
    public void update()
    {
        currentPosBall = currentGame.getBallList();
        setClosestBall();
        if (closestBall != null)
        {
            Move();
        }
    }

    private void setClosestBall()
    {
        closestBall = null;
        //System.out.println("Paddle:" + myPaddle.getPosition().toString());
        for (Ball b : currentPosBall)
        {
            //System.out.println("Ball pos: " + b.getPosition().toString());
            if (closestBall == null)
            {
                closestBall = b;
            }
            try
            {
                if (getDistance(b.getMiddlePosition()) < getDistance(closestBall.getMiddlePosition()))
                {
                    closestBall = b;
                }
            }
            catch(Exception ex)
            {
                System.out.println(ex.getMessage());
                 if (getDistance(b.getMiddlePosition()) < getDistance(closestBall.getMiddlePosition()))
                {
                    closestBall = b;
                }
            }
        }
        //System.out.println("Closest pos: " + closestBall.getPosition().toString());
    }

    private float getDistance(TVector2 targetPos)
    {
        TVector2 pos = myPaddle.getMiddlePosition();
        float distance = 0.0f;
        if (pos.getX() > targetPos.getX())
        {
            distance += pos.getX() - targetPos.getX();
        }
        else
        {
            distance += targetPos.getX() - pos.getX();
        }
        if (pos.getY() > targetPos.getY())
        {
            distance += pos.getY() - targetPos.getY();
        }
        else
        {
            distance += targetPos.getY() - pos.getY();
        }
        //System.out.println(distance);
        return distance;
    }

    /**
     * Methode for calling the move methode with difference for the position of
     * the CPU
     */
    public void Move()
    {
        try {
            if (this.myPaddle.getWindowLocation() == Paddle.windowLocation.SOUTH) {
                if (closestBall.getPosition().getX() > this.myPaddle.getPosition().getX() + (this.myPaddle.getSize().getX() / 2)) {
                    myPaddle.Move(Paddle.direction.RIGHT);
                }
                else {
                    myPaddle.Move(Paddle.direction.LEFT);
                }
            }
            else if (this.myPaddle.getWindowLocation() == Paddle.windowLocation.NORTH) {
                if (closestBall.getPosition().getX() > this.myPaddle.getPosition().getX() + (this.myPaddle.getSize().getX() / 2)) {
                    myPaddle.Move(Paddle.direction.RIGHT);
                }
                else {
                    myPaddle.Move(Paddle.direction.LEFT);
                }
            }
            else if (this.myPaddle.getWindowLocation() == Paddle.windowLocation.WEST) {
                if (closestBall.getPosition().getY() > this.myPaddle.getPosition().getY() + (this.myPaddle.getSize().getY() / 2)) {
                    myPaddle.Move(Paddle.direction.UP);
                }
                else {
                    myPaddle.Move(Paddle.direction.DOWN);
                }
            }
            else if (this.myPaddle.getWindowLocation() == Paddle.windowLocation.EAST) {
                if (closestBall.getPosition().getY() > this.myPaddle.getPosition().getY() + (this.myPaddle.getSize().getY() / 2)) {
                    myPaddle.Move(Paddle.direction.UP);
                }
                else {
                    myPaddle.Move(Paddle.direction.DOWN);
                }
            }
        }

        catch (Exception e) {
            System.out.println("CPU.Move() ERROR ! : " + e.getMessage());
            if (this.myPaddle.getWindowLocation() == Paddle.windowLocation.SOUTH) {
                if (closestBall.getPosition().getX() > this.myPaddle.getPosition().getX() + (this.myPaddle.getSize().getX() / 2)) {
                    myPaddle.Move(Paddle.direction.RIGHT);
                }
                else {
                    myPaddle.Move(Paddle.direction.LEFT);
                }
            }
            else if (this.myPaddle.getWindowLocation() == Paddle.windowLocation.NORTH) {
                if (closestBall.getPosition().getX() > this.myPaddle.getPosition().getX() + (this.myPaddle.getSize().getX() / 2)) {
                    myPaddle.Move(Paddle.direction.RIGHT);
                }
                else {
                    myPaddle.Move(Paddle.direction.LEFT);
                }
            }
            else if (this.myPaddle.getWindowLocation() == Paddle.windowLocation.WEST) {
                if (closestBall.getPosition().getY() > this.myPaddle.getPosition().getY() + (this.myPaddle.getSize().getY() / 2)) {
                    myPaddle.Move(Paddle.direction.UP);
                }
                else {
                    myPaddle.Move(Paddle.direction.DOWN);
                }
            }
            else if (this.myPaddle.getWindowLocation() == Paddle.windowLocation.EAST) {
                if (closestBall.getPosition().getY() > this.myPaddle.getPosition().getY() + (this.myPaddle.getSize().getY() / 2)) {
                    myPaddle.Move(Paddle.direction.UP);
                }
                else {
                    myPaddle.Move(Paddle.direction.DOWN);
                }
            }
        }
    }

}
