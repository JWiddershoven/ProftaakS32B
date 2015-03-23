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

    private PowerType type;
    private Int value;

    /**
     *
     * @param value
     * @param type
     */
    public PowerUp(Int value, PowerType type) {
        this.value = value;
        this.type = type;
    }
}
