/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import Server.CollisionChecker;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author Mnesymne
 */
public class Paddle extends GameObject
{
    
    private int score;
    private CPU cpuPlayer;
    private User humanPlayer;
    private windowLocation selectedPosition;
    private boolean left = false, right = false;

    /**
     * Enumerator direction
     */
    public enum direction
    {
        
        LEFT, RIGHT, UP, DOWN
    };
    
    public enum windowLocation
    {
        
        NORTH, EAST, SOUTH, WEST
    }

    /**
     * Getter of score
     *
     * @return score as int
     */
    public int getScore()
    {
        return score;
    }

    /**
     * Setter of score
     *
     * @param score value of score as int
     */
    public void addScore(int score)
    {
        this.score = this.score + score;
    }
    
    public windowLocation getWindowLocation()
    {
        return selectedPosition;
    }
    
    public User getPlayer()
    {
        return humanPlayer;
    }
    
    public CPU getCPU()
    {
        return cpuPlayer;
    }

    /**
     * Consturctor for CPU Paddle
     *
     * @param score value of score as int
     * @param position value of position as TVector2
     * @param velocity value of velocity as TVector2
     * @param size value of size as TVector2
     * @param cpu value of cpu as CPU Object
     * @param selectedLocation value of selectedPosition as windowLocation
     * @param image
     */
    public Paddle(int score, TVector2 position, TVector2 velocity, TVector2 size, CPU cpu, windowLocation selectedLocation,Image image)
    {
        super(position, velocity, size,image);
        this.score = score;
        this.selectedPosition = selectedLocation;
        this.cpuPlayer = cpu;
    }

    /**
     * Constructor for Player Paddle
     *
     * @param score value of score as int
     * @param position value of position as TVector2
     * @param velocity value of velocity as TVector2
     * @param size value of size as TVector2
     * @param user value of user as Player Object
     * @param selectedLocation value of selectedposition as windowLocation
     * @param image
     */
    public Paddle(int score, TVector2 position, TVector2 velocity, TVector2 size, User user, windowLocation selectedLocation,Image image)
    {
        super(position, velocity, size,image);
        this.score = score;
        this.selectedPosition = selectedLocation;
        this.humanPlayer = user;
    }

    /**
     * Move methode for a paddle Moves the paddle object location into the given
     * direction
     *
     * @param direction value of direction as enum
     */
    public void Move(direction direction)
    {
        TVector2 oldPosition = getPosition();
        TVector2 newPosition = TVector2.zero;
        switch (direction)
        {
            case UP:
                newPosition = new TVector2(this.getPosition().getX(), this.getPosition().getY() + 1);
                break;
            case DOWN:
                newPosition = new TVector2(this.getPosition().getX(), this.getPosition().getY() - 1);
                break;
            case LEFT:
                newPosition = new TVector2(this.getPosition().getX() - 1, this.getPosition().getY());
                break;
            case RIGHT:
                newPosition = new TVector2(this.getPosition().getX() + 1, this.getPosition().getY());
                break;
        }
        this.setPosition(newPosition);
        ArrayList<GameObject> collidedWith = CollisionChecker.collidesWithMultiple(this);
        // if paddle collides with something that is not a ball or a whitespace
        for (GameObject g : collidedWith)
        {
            if (g != null && !g.getClass().equals(Ball.class) && !g.getClass().equals(WhiteSpace.class))
            {
                this.setPosition(oldPosition);
            }
        }
    }
    
    /**
     * update moves the paddle
     */
    public void update()
    {
        if (left)
        {
            if (selectedPosition == windowLocation.NORTH || selectedPosition == windowLocation.SOUTH)
            {
                this.Move(direction.LEFT);
            }
            else if (selectedPosition == windowLocation.WEST)
            {
                this.Move(direction.UP);
            }
            else if (selectedPosition == windowLocation.EAST)
            {
                this.Move(direction.DOWN);
            }
        }
        if (right)
        {
            if (selectedPosition == windowLocation.NORTH || selectedPosition == windowLocation.SOUTH)
            {
                this.Move(direction.RIGHT);
            }
            else if (selectedPosition == windowLocation.WEST)
            {
                this.Move(direction.DOWN);
            }
            else if (selectedPosition == windowLocation.EAST)
            {
                this.Move(direction.UP);
            }
        }
    }
    
    public void keyPressed(int k)
    {
        if (this.getPlayer() != null)
        {
            if (k == KeyEvent.VK_LEFT)
            {
                left = true;
            }
            if (k == KeyEvent.VK_RIGHT)
            {
                right = true;
            }
        }
    }
    
    public void keyReleased(int k)
    {
        if (this.getPlayer() != null)
        {
            if (k == KeyEvent.VK_LEFT)
            {
                left = false;
            }
            if (k == KeyEvent.VK_RIGHT)
            {
                right = false;
            }
        }
    }
    
}
