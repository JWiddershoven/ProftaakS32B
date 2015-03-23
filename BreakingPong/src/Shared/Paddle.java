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
public class Paddle extends GameObject{
    private int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Paddle(int score, TVector2 position, TVector2 velocity, TVector2 size) { 
        super(position,velocity,size);
        this.score = score;
    }
    
    public void Move()
    {
        
    }
}
