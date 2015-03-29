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
    /**
     * Enumerator Direction
     */
    public enum Direction 
    {
        LEFT, RIGHT, UP, DOWN
    };
    /**
     * Getter of score
     * @return score as int
     */
    public int getScore() {
        return score;
    }
    /**
     * Setter of score
     * @param score value of score as int 
     */
    public void setScore(int score) {
        if(score >= 0)
        {
            this.score = score;
        }
    }
    /**
     * Consturctor for CPU Paddle
     * @param score value of score as int
     * @param position value of position as TVector2
     * @param velocity value of velocity as TVector2
     * @param size value of size as TVector2
     * @param cpu value of cpu as CPU Object
     */
    public Paddle(int score, TVector2 position, TVector2 velocity, TVector2 size) 
    { 
        super(position,velocity,size);
        this.score = score;
    }
    /**
     * Constructor for Player Paddle
     * @param score value of score as int
     * @param position value of position as TVector2
     * @param velocity value of velocity as TVector2
     * @param size value of size as TVector2
     * @param user value of user as Player Object
     */
    public Paddle(int score, TVector2 position, TVector2 velocity, TVector2 size, User user) 
    { 
        super(position,velocity,size);
        this.score = score;
    }
    /**
     * Move methode for a paddle
     * Moves the paddle object location into the given direction
     * @param direction value of direction as enum
     */
    public void Move(Direction direction)
    {
         switch (direction) {
            case UP:
                TVector2 newPositionUp = new TVector2(this.getPosition().getX(), this.getPosition().getY()+1);
                this.setPosition(newPositionUp);
                     break;
            case DOWN:
                TVector2 newPositionDown = new TVector2(this.getPosition().getX(), this.getPosition().getY()-1);
                this.setPosition(newPositionDown);
                     break;
            case LEFT:
                
                TVector2 newPositionLeft = new TVector2(this.getPosition().getX()-1, this.getPosition().getY());
                this.setPosition(newPositionLeft);
                     break;
            case RIGHT:
                TVector2 newPosition = new TVector2(this.getPosition().getX()+1, this.getPosition().getY());
                this.setPosition(newPosition);
                     break;
         }
    }
}
