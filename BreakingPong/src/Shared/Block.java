/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Shared;
/**
 *
 * @author Mnesymne
 */
public class Block extends GameObject {


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


    /**
     * The Block constructor.
     * Calls the super constructor of GameObject.
     * @param points as integer, The amount of points this blocks gives when it is destroyed.
     * @param isDestructable as boolean. Whether this block can be destroyed by the ball or not.
     * @param powerUp as PowerUp. Can be empty.
     * @param position as TVector2. The position of the GameObject in the game.
     * @param velocity as TVector2. The velocity of the GameObject in the game.
     * @param size as TVector2. The size of the GameObject in the game.
     */
    public Block(int points, boolean isDestructable, PowerUp powerUp,TVector2 position, TVector2 velocity, TVector2 size)
    {
        super(position,velocity,size);
        this.points = points;
        this.isDestructable = isDestructable;
        this.powerUp = powerUp;
    }

    public boolean isDestructable() {
        return isDestructable;
    }
    public void setDestructable(boolean isDestructable) {
        this.isDestructable = isDestructable;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public PowerUp getPowerUp() {
        return powerUp;
    }
    public void setPowerUp(PowerUp powerUp) {
        this.powerUp = powerUp;
    }
}