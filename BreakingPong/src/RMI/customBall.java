/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import javafx.scene.shape.Circle;

/**
 *
 * @author Mnesymne
 */
public class customBall extends Circle{
    
    public String Name;

    public customBall(String Name, double centerX, double centerY, double radius) {
        super(centerX, centerY, radius);
        this.Name = Name;
    }
    
}
