/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Shared;

/**
 * @author Jelle
 */
public class Ball extends GameObject {
    
    private Paddle lastPaddleTouched;
    private TVector2 tvector2;
    
    /**
     * Creates a new ball object with a TVector2 and lastPaddleTouched.
     * @param tvector2 The TVector2 of the ball.
     * @param lastPaddleTouched The last Paddle which touched the ball.
     * @param position The position of a GameObject.
     * @param velocity The velocity of a GameObject.
     * @param size The size of a GameObject.
     */
    public Ball(TVector2 tvector2, Paddle lastPaddleTouched, TVector2 position, TVector2 velocity, TVector2 size)
    {
        super(position, velocity, size);
        this.tvector2 = tvector2;
        this.lastPaddleTouched = lastPaddleTouched;
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
        this.lastPaddleTouched = lastPaddleTouched;
    }
    
    /**
     * Moves the ball in the specified direction.
     * @param tvector2 The TVector2 of the ball.
     */
    public void move(TVector2 tvector2)
    {
        
    }
    
    /**
     * Handles the collision between the ball and an object.
     * @param tvector2 The TVector2 of the ball.
     */
    public void collision(TVector2 tvector2)
    {
        
    }
}
