/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import Interfaces.IUser;
import Server.CollisionChecker;
import java.awt.Image;
import java.util.ArrayList;

/**
 *
 * @author Mnesymne
 */
public class Paddle extends GameObject {

    private int score;
    private CPU cpuPlayer;
    private IUser humanPlayer;
    private WindowLocation selectedPosition;
    private boolean enabled = true;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Enumerator Direction
     */
    public enum Direction {

        LEFT, RIGHT, UP, DOWN
    };

    public enum WindowLocation {

        NORTH, EAST, SOUTH, WEST
    }

    /**
     * Getter of score
     *
     * @return score as int
     */
    public int getScore() {
        return score;
    }

    /**
     * Setter of score
     *
     * @param score value of score as int
     */
    public void addScore(int score) {
        this.score = this.score + score;
    }

    public WindowLocation getWindowLocation() {
        return selectedPosition;
    }

    public IUser getPlayer() {
        return humanPlayer;
    }

    public CPU getCPU() {
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
     * @param selectedLocation value of selectedPosition as WindowLocation
     * @param image
     */
    public Paddle(int score, TVector2 position, TVector2 velocity, TVector2 size, CPU cpu, WindowLocation selectedLocation, Image image) {
        super(position, velocity, size, image);
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
     * @param selectedLocation value of selectedposition as WindowLocation
     * @param image
     */
    public Paddle(int score, TVector2 position, TVector2 velocity, TVector2 size, IUser user, WindowLocation selectedLocation, Image image) {
        super(position, velocity, size, image);
        this.score = score;
        this.selectedPosition = selectedLocation;
        this.humanPlayer = user;
    }

    /**
     * Move methode for a paddle Moves the paddle object location into the given
     * Direction
     *
     * @param direction value of Direction as enum
     */
    public void Move(Direction direction) {
        TVector2 oldPosition = getPosition();
        TVector2 newPosition = TVector2.zero;
        switch (direction) {
            case UP:
                newPosition = new TVector2(this.getPosition().getX(), this.getPosition().getY() + 2);
                break;
            case DOWN:
                newPosition = new TVector2(this.getPosition().getX(), this.getPosition().getY() - 2);
                break;
            case LEFT:
                newPosition = new TVector2(this.getPosition().getX() - 2, this.getPosition().getY());
                break;
            case RIGHT:
                newPosition = new TVector2(this.getPosition().getX() + 2, this.getPosition().getY());
                break;
        }
        this.setPosition(newPosition);
        ArrayList<GameObject> collidedWith = CollisionChecker.collidesWithMultiple(this);
        // if paddle collides with something that is not a ball or a whitespace
        for (GameObject g : collidedWith) {
            if (g != null && !g.getClass().equals(Ball.class) && !g.getClass().equals(WhiteSpace.class)) {
                this.setPosition(oldPosition);
            }
        }
    }

    /**
     * update moves the paddle
     *
     * @param direction - LEFT or RIGHT.
     */
    public void MoveDirection(Direction direction) {
        if (direction == Direction.LEFT) {
            if (selectedPosition == WindowLocation.NORTH || selectedPosition == WindowLocation.SOUTH) {
                this.Move(Direction.LEFT);
            }
            else if (selectedPosition == WindowLocation.WEST) {
                this.Move(Direction.UP);
            }
            else if (selectedPosition == WindowLocation.EAST) {
                this.Move(Direction.DOWN);
            }
        }
        if (direction == Direction.RIGHT) {
            if (selectedPosition == WindowLocation.NORTH || selectedPosition == WindowLocation.SOUTH) {
                this.Move(Direction.RIGHT);
            }
            else if (selectedPosition == WindowLocation.WEST) {
                this.Move(Direction.DOWN);
            }
            else if (selectedPosition == WindowLocation.EAST) {
                this.Move(Direction.UP);
            }
        }
    }
}
