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
public class TVector2 {
    
    private float x;
    private float y;
/**
 * Getter of x.
 * @return x as float
 */
    public float getX() {
        return x;
    }
/**
 * Setter of x.
 * @param x value of x as float
 */
    public void setX(float x) {
        this.x = x;
    }
/**
 * Getter of y.
 * @return y as float.
 */
    public float getY() {
        return y;
    }
/**
 * Setter of y.
 * @param y value of y as float.
 */
    public void setY(float y) {
        this.y = y;
    }
/**
 * Constructor
 * @param x x value of vector as float.
 * @param y y value of vector as float.
 */
    public TVector2(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
