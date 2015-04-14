/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.Timer;

/**
 *
 * @author Mnesymne
 */
public class CPU {

    private final String name;
    private final Byte difficulty; // Currently not used
    private Paddle myPaddle; // CPU PADDLE KOPPLING VIA PADDLE KLASSE

    private ArrayList<Ball> allBalls;
    private Game currentGame;
    private Ball closestBall;
    private double[] points;
    private ArrayList<Ball> currentPosBall;

    public CPU(String name, Byte difficulty, Game myGame) {
        this.name = name;
        this.difficulty = difficulty;
        this.currentGame = myGame;
    }

    public void setMyPaddle(Paddle myPaddle) {
        this.myPaddle = myPaddle;
    }

    public String getName() {
        return name;
    }

    public byte getDifficuly() {
        return this.difficulty;
    }

    public void startBot() {
        Timer timer = new Timer(10, (ActionEvent e) -> {
            runCPU();
        });
        timer.start();
    }

    /**
     * Makes the CPU start his AI
     */
    private void runCPU() {
        currentPosBall = currentGame.getBallList();
        setClosestBall();
        /*TVector2 currentPosPaddle = myPaddle.getPosition();
         float[] ClosestTo = new float[8];
         int i = 0;
         for (Ball b : currentPosBall) {
         ClosestTo[i] = (b.getPosition().getX() - currentPosPaddle.getX());
         i++;
         ClosestTo[i] = b.getPosition().getY() - currentPosPaddle.getY();
         i++;
         }
         int numberOfPlayers = 4;
         if (numberOfPlayers == 4) {
         points = new double[4];
         double out = (((ClosestTo[1] * 2) - currentPosPaddle.getY() * 2) + (ClosestTo[0] * 2 - currentPosPaddle.getX() * 2));
         double out2 = (((ClosestTo[3] * 2) - currentPosPaddle.getY() * 2) + (ClosestTo[2] * 2 - currentPosPaddle.getX() * 2));
         double out3 = (((ClosestTo[5] * 2) - currentPosPaddle.getY() * 2) + (ClosestTo[4] * 2 - currentPosPaddle.getX() * 2));
         double out4 = (((ClosestTo[7] * 2) - currentPosPaddle.getY() * 2) + (ClosestTo[6] * 2 - currentPosPaddle.getX() * 2));
         if (out < 0) {
         out = out * -1;
         }
         if (out2 < 0) {
         out2 = out2 * -1;
         }
         if (out3 < 0) {
         out3 = out3 * -1;
         }
         if (out4 < 0) {
         out4 = out4 * -1;
         }
         points[0] = Math.sqrt(out);
         points[1] = Math.sqrt(out2);
         points[2] = Math.sqrt(out3);
         points[3] = Math.sqrt(out4);
         calcPositionForFour();
         } else if (numberOfPlayers == 2) {
         points = new double[2];
         points[0] = Math.sqrt(((ClosestTo[1] * 2) - currentPosPaddle.getY() * 2) + (ClosestTo[0] * 2 - currentPosPaddle.getX() * 2));
         points[1] = Math.sqrt(((ClosestTo[3] * 2) - currentPosPaddle.getY() * 2) + (ClosestTo[2] * 2 - currentPosPaddle.getX() * 2));
         calcPositionForTwo();
         }
         //this.closestBall = currentPosBall.get(0);*/
        Move();
    }

    private void setClosestBall() {
        closestBall = null;
        //System.out.println("Paddle:" + myPaddle.getPosition().toString());
        for (Ball b : currentPosBall) {
            //System.out.println("Ball pos: " + b.getPosition().toString());
            if (closestBall == null) {
                closestBall = b;
            }
            if (getDistance(b.getMiddlePosition()) < getDistance(closestBall.getMiddlePosition())) {
                closestBall = b;
            }
        }
        //System.out.println("Closest pos: " + closestBall.getPosition().toString());
    }

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
        System.out.println(distance);
        return distance;
    }

    /**
     * Methode for calculating closestBall for four players
     */
    public void calcPositionForFour() {
        int closedTo = -1;
        for (int i = 0; i < points.length; i++) {
            if (closedTo == -1) {
                closedTo = 0;
            }
            if (points[closedTo] > 0) {
                if (points[closedTo] < points[i]) {
                    closedTo = i;

                }
            }
        }
        closestBall = currentPosBall.get(closedTo);
        System.out.println(closestBall.getPosition().toString());
        /*
         if (points[0] > points[1])
         {
         if (points[0] > points[2])
         {
         if (points[0] > points[3])
         {
         closestBall = currentPosBall.get(0);
         } else
         {
         closestBall = currentPosBall.get(3);
         }
         } else if (points[2] > points[3])
         {
         closestBall = currentPosBall.get(2);
         } else
         {
         closestBall = currentPosBall.get(3);
         }
         } else if (points[1] > points[2])
         {
         if (points[1] > points[3])
         {
         closestBall = currentPosBall.get(1);
         } else
         {
         closestBall = currentPosBall.get(3);
         }
         } else if (points[2] > points[3])
         {
         closestBall = currentPosBall.get(2);
         } else
         {
         closestBall = currentPosBall.get(3);
         }*/
    }

    /**
     * Methode for calculating closestBall for two players
     */
    public void calcPositionForTwo() {
        if (points[0] > points[1]) {
            closestBall = currentPosBall.get(0);
        } else {
            closestBall = currentPosBall.get(1);
        }
    }

    /**
     * Methode for calling the move methode with difference for the position of
     * the CPU
     */
    public void Move() {
        if (this.myPaddle.getWindowLocation() == Paddle.windowLocation.SOUTH) {
            if (closestBall.getPosition().getX() > this.myPaddle.getPosition().getX() + (this.myPaddle.getSize().getX() / 2)) {
                myPaddle.Move(Paddle.direction.RIGHT);
            } else {
                myPaddle.Move(Paddle.direction.LEFT);
            }
        } else if (this.myPaddle.getWindowLocation() == Paddle.windowLocation.NORTH) {
            if (closestBall.getPosition().getX() > this.myPaddle.getPosition().getX() + (this.myPaddle.getSize().getX() / 2)) {
                myPaddle.Move(Paddle.direction.RIGHT);
            } else {
                myPaddle.Move(Paddle.direction.LEFT);
            }
        } else if (this.myPaddle.getWindowLocation() == Paddle.windowLocation.WEST) {
            if (closestBall.getPosition().getY() > this.myPaddle.getPosition().getY() + (this.myPaddle.getSize().getY() / 2)) {
                myPaddle.Move(Paddle.direction.UP);
            } else {
                myPaddle.Move(Paddle.direction.DOWN);
            }
        } else if (this.myPaddle.getWindowLocation() == Paddle.windowLocation.EAST) {
            if (closestBall.getPosition().getY() > this.myPaddle.getPosition().getY() + (this.myPaddle.getSize().getY() / 2)) {
                myPaddle.Move(Paddle.direction.UP);
            } else {
                myPaddle.Move(Paddle.direction.DOWN);
            }
        }
    }

}
