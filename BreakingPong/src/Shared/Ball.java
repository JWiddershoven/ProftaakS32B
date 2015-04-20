/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import Server.CollisionChecker;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
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
    public static final float maxSpeed = 1.5f;
    private Date lastTimePaddleTouched;

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
                //System.out.println("Collided" + go.toString());
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

    /**
     *
     * @param go
     */
    public void bounce(GameObject go)
    {
        if (go instanceof Paddle)
        {
            Paddle p = (Paddle) go;
            lastPaddleTouched = p;
            boolean difference = true;
            Date now = new Date();
            if (lastTimePaddleTouched != null)
            {
                if (now.getSeconds() == lastTimePaddleTouched.getSeconds())
                {
                    System.out.println("Too many collisions with paddle");
                    difference = false;
                }
            }
            if (difference == true)
            {
                lastTimePaddleTouched = new Date();
                if (bouncePaddle(p) == true)
                {
                    return;
                }
            }

        }
        TVector2 position = this.getPosition();
        TVector2 goPos = go.getPosition();
        float f1, f2;
        TVector2 vel = this.getVelocity();
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
        if (f1 < f2)
        {
            vel.setY(bounceFloat(this.getVelocity().getY()));
        }
        else
        {
            vel.setX(bounceFloat(this.getVelocity().getX()));
        }

        this.setVelocity(vel);

    }

    /**
     *
     * @param p
     * @return FALSE if nothing changed.
     */
    private boolean bouncePaddle(Paddle p)
    {
        if (p.getWindowLocation() == Paddle.windowLocation.NORTH || p.getWindowLocation() == Paddle.windowLocation.SOUTH)
        {
            float x, y;
            float distance = 0.0f;
            boolean left = false;
            boolean right = false;
            if (this.getMiddlePosition().getX() > p.getMiddlePosition().getX())
            {
                distance = this.getMiddlePosition().getX() - p.getMiddlePosition().getX();
                right = true;
            }
            else if (this.getMiddlePosition().getX() < p.getMiddlePosition().getX())
            {
                distance = p.getMiddlePosition().getX() - this.getMiddlePosition().getX();
                left = true;
            }
            if (right == true)
            {
                x = this.getVelocity().getX() + (distance / (maxSpeed * 25));
            }
            else if (left == true)
            {
                x = this.getVelocity().getX() - (distance / (maxSpeed * 25));
            }
            else
            {
                return false;
            }
            if (x > (maxSpeed - 0.2f))
            {
                x = maxSpeed - 0.2f;
            }
            if (x < (-maxSpeed + 0.2f))
            {
                x = -maxSpeed + 0.2f;
            }
            if (x < 0)
            {
                y = maxSpeed - (x * -1);
            }
            else
            {
                y = maxSpeed - x;
            }

            if (this.getVelocity().getY() > 0)
            {
                y = -y;
            }
            else if (y < 0)
            {
                y *= -1;
            }

            System.out.println("old vel: " + this.getVelocity().toString());
            this.setVelocity(new TVector2(x, y));
            System.out.println("new vel: " + this.getVelocity().toString());
        }
        else
        {
            // TODO:

            return false;
        }
        return true;
    }

    /**
     *
     * @param x
     * @return
     */
    public float bounceFloat(float x)
    {
        x *= -1;
        return x;
    }

}
