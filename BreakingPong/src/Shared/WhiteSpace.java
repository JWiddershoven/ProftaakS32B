/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import java.awt.Color;

/**
 *
 * @author Jordi
 */
public class WhiteSpace extends GameObject
{
    private Color color;
    public WhiteSpace(TVector2 position, TVector2 velocity, TVector2 size, Color color)
    {
        super(position, velocity, size);
        this.color = color;
    }
    
    public Color getColor()
    {
       return this.color;
    }
}
