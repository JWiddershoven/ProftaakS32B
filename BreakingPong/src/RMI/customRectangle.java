/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import javafx.scene.shape.Rectangle;

/**
 *
 * @author Mnesymne
 */
public class customRectangle extends Rectangle{
    
    public String name;

    public customRectangle(String name, double x, double y, double width, double height) {
        super(x, y, width, height);
        this.name = name;
    }
    
    
    
}
