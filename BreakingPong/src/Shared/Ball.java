/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import Server.CollisionChecker;
import java.awt.Color;
import java.util.ArrayList;
import javafx.application.Platform;
import javax.swing.Timer;

/**
 * @author Jelle
 */
public class Ball extends GameObject
{

    private final Game game;
    private Paddle lastPaddleTouched;
    Timer timer;

    /**
     * Creates a new ball object with a TVector2 and lastPaddleTouched.
     *
     * @param lastPaddleTouched The last Paddle which touched the ball.
     * @param position The position of a GameObject.
     * @param velocity The velocity of a GameObject.
     * @param size The size of a GameObject.
     * @param game The game of where this ball is in.
     */
    public Ball(Paddle lastPaddleTouched, TVector2 position, TVector2 velocity, TVector2 size, Game game, Color color)
    {
        super(position, velocity, size, color);
        this.game = game;
        if (position != null && velocity != null && size != null)
        {
            this.lastPaddleTouched = lastPaddleTouched;
        }
        else
        {
            throw new IllegalArgumentException();
        }
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
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    /**
     * updates the position of the ball
     */
    public void update()
    {
        ArrayList<GameObject> collidedWith = new ArrayList<>();
        collidedWith.addAll(CollisionChecker.collidesWithMultiple(this));
        boolean hasBounced = false;
        // If ball collides with something that is not a WhiteSpace.
        for (GameObject go : collidedWith)
        {
            if (go != null && !go.getClass().equals(WhiteSpace.class))
            {
                System.out.println("Collided" + go.toString());
                if (hasBounced == false)
                {
                    bounce(go);
                    hasBounced = true;
                }
                if (go.getClass().equals(Block.class))
                {
                    Block b = (Block) go;
                    if (lastPaddleTouched != null)
                    {
                        lastPaddleTouched.addScore(b.getPoints());
                    }
                    if (b.isDestructable())
                    {
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
        Platform.runLater(() ->
        {
            this.setPosition(newPos);
        });

    }

    public void bounce(GameObject go)
    {
        if (go instanceof Paddle)
        {
            Paddle p = (Paddle) go;
            if (p.getWindowLocation() == Paddle.windowLocation.NORTH || p.getWindowLocation() == Paddle.windowLocation.SOUTH)
            {
                this.setVelocity(new TVector2(this.getVelocity().getX(), this.getVelocity().getY() * -1));
                return;
            }
            else
            {
                this.setVelocity(new TVector2(this.getVelocity().getX() * -1, this.getVelocity().getY()));
                return;
            }
        }
        //System.out.println("enter bounce");
        TVector2 position = this.getPosition();
        TVector2 goPos = go.getPosition();
        float f1, f2;
        //float deltaX = go.getPosition().getX() - this.getPosition().getX();
        //float deltaY = go.getPosition().getY() - this.getPosition().getY();
        //double angleInDegrees = Math.atan2(deltaY, deltaX) * 180 / Math.PI;
        TVector2 vel = this.getVelocity();
        //System.out.println("Position:" + this.getPosition().toString());
        //System.out.println("Position Go:" + go.getPosition().toString());
        if (position.getY() > goPos.getY())
        {
            f1 = goPos.getY() - position.getY();
        }
        else
        {
            f1 = position.getY() - goPos.getY();
        }
        if (position.getX() > goPos.getX())
        {
            f2 = goPos.getX() - position.getX();
        }
        else
        {
            f2 = position.getX() - goPos.getX();
        }
        //System.out.println(this.getVelocity().toString());
        //System.out.println("diff Y:" + f1 + " X:" + f2);
        if (f1 < f2)
        {
            //  System.out.println("bounceY");
            vel.setY(bounceFloat(this.getVelocity().getY()));
        }
        else
        {
            //System.out.println("bounceX");
            vel.setX(bounceFloat(this.getVelocity().getX()));
        }

        this.setVelocity(vel);
        //System.out.println(this.getVelocity().toString());
    }

    public float bounceFloat(float x)
    {
        x *= -1;
        return x;
    }
}
