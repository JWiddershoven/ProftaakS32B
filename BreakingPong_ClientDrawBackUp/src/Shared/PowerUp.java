/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Shared;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Mnesymne
 */
public class PowerUp implements Serializable {

    /**
     * PowerUpType - see PowerUpType enumeration
     */
    private PowerUpType type;
    /**
     * The value of the PowerUp. Can be negative.
     */
    private int value;


    /**
     * Constructor for PowerUp
     * @param value as int. Value can be negative.
     * @param type as PowerUpType.
     */
    public PowerUp(int value, PowerUpType type) {
        this.value = value;
        this.type = type;
    }
    
    /**
     * The PowerUpType of this PowerUp - see PowerUpType enumeration
     * @return The PowerUpType of this PowerUp - see PowerUpType enumeration
     */
    public PowerUpType getType() {
        return type;
    }
    /**
     * The PowerUpType of this PowerUp - see PowerUpType enumeration
     * @param type The PowerUpType of this PowerUp - see PowerUpType enumeration
     */
    public void setType(PowerUpType type) {
        this.type = type;
    }

    /**
     * The value of the PowerUp. Can be negative.
     * @return The value of the PowerUp. Can be negative.
     */
    public int getValue() {
        return value;
    }
    /**
     * The value of the PowerUp. Can be negative.
     * @param value The value of the PowerUp. Can be negative.
     */
    public void setValue(int value) {
        this.value = value;
    }
    
    public void getRandomPowerUpType()
    {
        List<PowerUpType> powerUps = Collections.unmodifiableList(Arrays.asList(PowerUpType.values()));
        int size = powerUps.size();
        Random random = new Random();
        
        this.type = powerUps.get(random.nextInt(size));
    }
}
