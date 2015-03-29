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
public class CPU {
    private final String name;
    private final Byte difficulty;

    public CPU(String name, Byte difficulty) {
        this.name = name;
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }
    
    /**
     * Makes the CPU start his AI
     */
    public void startCPU()
    {
        
    }
    
    
}
