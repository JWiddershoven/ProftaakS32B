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
public class GameObject {
    
    private TVector2 position;
    private TVector2 velocity;
    private TVector2 size;
    /**
     * Getter of position.
     * @return position as TVector2
     */
    public TVector2 getPosition() {
        return position;
    }
    /**
     * Setter of position
     * @param position value of position as TVector2
     */
    public void setPosition(TVector2 position) {
        this.position = position;
    }
    /**
     * Getter of velocity.
     * @return velocity as TVector2
     */
    public TVector2 getVelocity() {
        return velocity;
    }
    /**
     * Setter of velocity.
     * @param velocity value of velocity as TVector2
     */
    public void setVelocity(TVector2 velocity) {
        this.velocity = velocity;
    }
    /**
     * Getter of size.
     * @return size as TVector2
     */
    public TVector2 getSize() {
        return size;
    }
    /**
     * Setter of size
     * @param size value of size as TVector2
     */
    public void setSize(TVector2 size) {
        this.size = size;
    }
    /**
     * Constructor
     * @param position value of position as TVector2.
     * @param velocity value of velocity as TVector2.
     * @param size value of size as TVector2.
     */
    public GameObject(TVector2 position, TVector2 velocity, TVector2 size) {
        this.position = position;
        this.velocity = velocity;
        this.size = size;
    }
}
