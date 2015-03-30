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
    private CPU cpuPlayer;
    private User humanPlayer;
    private windowLocation selectedPosition;
    /**
     * Enumerator Direction
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
    
    public windowLocation getWindowLocation()
    {
        return selectedPosition;
    }
    /**
     * Consturctor for CPU Paddle
     * @param score value of score as int
     * @param position value of position as TVector2
     * @param velocity value of velocity as TVector2
     * @param size value of size as TVector2
     * @param cpu value of cpu as CPU Object
     * @param selectedLocation value of selectedPosition as windowLocation
     */
    public Paddle(int score, TVector2 position, TVector2 velocity, TVector2 size, CPU cpu, windowLocation selectedLocation) 
    { 
        super(position,velocity,size);
        this.score = score;
        this.selectedPosition = selectedLocation;
        this.cpuPlayer = cpu;
    }
    /**
     * Constructor for Player Paddle
     * @param score value of score as int
     * @param position value of position as TVector2
     * @param velocity value of velocity as TVector2
     * @param size value of size as TVector2
     * @param user value of user as Player Object
     * @param selectedLocation value of selectedposition as windowLocation
     */
    public Paddle(int score, TVector2 position, TVector2 velocity, TVector2 size, User user, windowLocation selectedLocation) 
    { 
        super(position,velocity,size);
        this.score = score;
        this.selectedPosition = selectedLocation;
        this.humanPlayer = user;
    }
    /**
     * Move methode for a paddle
     * Moves the paddle object location into the given direction
     * @param direction value of direction as enum
     */
    public void Move(direction direction)
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
