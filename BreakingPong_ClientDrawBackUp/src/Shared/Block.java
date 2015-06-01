/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import Shared.Paddle.WindowLocation;
import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;

/**
 *
 * @author Lorenzo
 */
public class Block extends GameObject
{
    /**
     * TVector2(20f,20f)
     */
    public static TVector2 standardBlockSize = new TVector2(20f,20f);
    
    /**
     * The amount of points this block gives when it is destroyed.
     */
    private int points;
    /**
     * Is this block destructable;
     */
    private boolean isDestructable;
    /**
     * Powerup - See PowerUp class
     */
    private PowerUp powerUp;

    private ArrayList<WindowLocation> locations;

    /**
     * The Block constructor. Calls the super constructor of GameObject.
     *
     * @param points as integer, The amount of points this blocks gives when it
     * is destroyed.
     * @param isDestructable as boolean. Whether this block can be destroyed by
     * the ball or not.
     * @param powerUp as PowerUp. Can be empty.
     * @param position as TVector2. The position of the GameObject in the game.
     * @param velocity as TVector2. The velocity of the GameObject in the game.
     * @param size as TVector2. The size of the GameObject in the game.
     */
    public Block(int points, boolean isDestructable, PowerUp powerUp, TVector2 position, TVector2 velocity, TVector2 size, Image image)
    {
        super(position, velocity, size, image);
        this.points = points;
        this.isDestructable = isDestructable;
        this.powerUp = powerUp;
    }

    /**
     * Is this block destructable;
     *
     * @return
     */
    public boolean isDestructable()
    {
        return isDestructable;
    }

    /**
     * Is this block destructable;
     *
     * @param isDestructable
     */
    public void setDestructable(boolean isDestructable)
    {
        this.isDestructable = isDestructable;
    }

    /**
     * The amount of points this block gives when it is destroyed.
     *
     * @return The amount of points this block gives when it is destroyed.
     */
    public int getPoints()
    {
        return points;
    }

    /**
     * The amount of points this block gives when it is destroyed.
     *
     * @param points The amount of points this block gives when it is destroyed.
     */
    public void setPoints(int points)
    {
        this.points = points;
    }

    /**
     * The Powerup of this Block - See PowerUp class. Can be null.
     *
     * @return The Powerup of this Block - See PowerUp class. Can be null.
     */
    public PowerUp getPowerUp()
    {
        return powerUp;
    }

    /**
     * Powerup - See PowerUp class. Can be null.
     *
     * @param powerUp The Powerup of this Block - See PowerUp class. Can be
     * null.
     */
    public void setPowerUp(PowerUp powerUp)
    {
        this.powerUp = powerUp;
    }

    /**
     *  if (powerUp == null)
            return Color.YELLOW;
        else
            return Color.RED;
     * @return  if (powerUp == null)
            return Color.YELLOW;
        else
            return Color.RED;
     */
    @Override
    public Color getColor()
    {
        if (powerUp == null)
            return Color.YELLOW;
        else
            return Color.RED;
    }

    /**
     * Destroys this object.
     */
    public void destroyObject()
    {

    }
}
