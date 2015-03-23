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
public class PowerUp {

    private PowerUpType type;
    private int value;

    /**
     *
     * @param value
     * @param type
     */
    public PowerUp(int value, PowerUpType type) {
        this.value = value;
        this.type = type;
    }
}
