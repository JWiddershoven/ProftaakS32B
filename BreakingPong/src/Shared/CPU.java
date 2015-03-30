/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Shared;

import Shared.Paddle.Direction;
import java.util.ArrayList;

/**
 *
 * @author Mnesymne
 */
public class CPU {
    private final String name;
    private final Byte difficulty;
    private Paddle myPaddle;
    private ArrayList<Ball> allBalls;
    private Game currentGame;
    private Ball closestBall;
    private double[] points;

    public CPU(String name, Byte difficulty, Paddle mypaddle, Game myGame) {
        this.name = name;
        this.difficulty = difficulty;
        this.myPaddle = mypaddle;
        this.currentGame = myGame;
    }

    public String getName() {
        return name;
    }
    
    /**
     * Makes the CPU start his AI
     */
    public void runCPU()
    {
        
        ArrayList<Ball> currentPosBall = currentGame.getBallList();
        TVector2 currentPosPaddle = myPaddle.getPosition();
        float[] ClosestTo  = new float[8];
        int i = 0;
        for(Ball b : currentPosBall)
        {
          ClosestTo[i] = (b.getTVector2().getX() - currentPosPaddle.getX());
          i++;
          ClosestTo[i] = b.getTVector2().getY() - currentPosPaddle.getY();
          i++;
        }
        points = new double[4];
        points[0] = Math.sqrt(((ClosestTo[1] * 2) - currentPosPaddle.getY() * 2) + (ClosestTo[0] * 2 - currentPosPaddle.getX() * 2));
        points[1] = Math.sqrt(((ClosestTo[3] * 2) - currentPosPaddle.getY() * 2) + (ClosestTo[2] * 2 - currentPosPaddle.getX() * 2));
        points[2] = Math.sqrt(((ClosestTo[5] * 2) - currentPosPaddle.getY() * 2) + (ClosestTo[4] * 2 - currentPosPaddle.getX() * 2));
        points[3] = Math.sqrt(((ClosestTo[7] * 2) - currentPosPaddle.getY() * 2) + (ClosestTo[6] * 2 - currentPosPaddle.getX() * 2));
        
       
        
        Move();
    }
    
    public void calcPosition()
    {
         if(points[0] > points[1])
        {
            if(points[0] > points[2])
            {
                if(points[0] > points[3])
                {
                   closestBall = currentPosBall.get(0);
                }
                else
                {
                    closestBall = currentPosBall.get(3);
                }
            }
            else if(points[2] > points[3])
            {
                closestBall = currentPosBall.get(2);
            }
            else
            {
                 closestBall = currentPosBall.get(3);
            }
        }
        else  if(points[1] > points[2])
            {
                if(points[1] > points[3])
                {
                   closestBall = currentPosBall.get(1);
                }
                else
                {
                    closestBall = currentPosBall.get(3);
                }
            }
            else if(points[2] > points[3])
            {
                closestBall = currentPosBall.get(2);
            }
            else
            {
                 closestBall = currentPosBall.get(3);
            }
    }
    
    public void Move()
    {
        if(this.myPaddle.getWindowLock() == South)
        {
            if(closestBall.getTVector2().getX() > this.myPaddle.getPosition().getX())
            {
                myPaddle.Move(Direction.RIGHT);
            }
            else
            {
                myPaddle.Move(Direction.LEFT);
            }
        }
        else if(this.myPaddle.getWindowLock() == North)
        {
            if(closestBall.getTVector2().getX() < this.myPaddle.getPosition().getX())
            {
                myPaddle.Move(Direction.RIGHT);
            }
            else
            {
                myPaddle.Move(Direction.LEFT);
            }
        }
        else if(this.myPaddle.getWindowLock() == West)
        {
            if(closestBall.getTVector2().getY() > this.myPaddle.getPosition().getY())
            {
                myPaddle.Move(Direction.UP);
            }
            else
            {
                myPaddle.Move(Direction.DOWN);
            }
        }
        else if(this.myPaddle.getWindowLock() == East)
        {
            if(closestBall.getTVector2().getY() < this.myPaddle.getPosition().getY())
            {
                myPaddle.Move(Direction.UP);
            }
            else
            {
                myPaddle.Move(Direction.DOWN);
            }
        }
    }
    
}
