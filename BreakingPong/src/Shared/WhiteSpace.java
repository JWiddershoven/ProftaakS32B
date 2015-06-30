/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import java.awt.Image;



/**
 *
 * @author Jordi
 */
public class WhiteSpace extends GameObject
{
    /**
     * The whitespace constructor
     * @param position  The TVector2 position
     * @param velocity The TVector2 velocity
     * @param size Th TVector2 Size
     * @param image The image
     */
    public WhiteSpace(TVector2 position, TVector2 velocity, TVector2 size, Image image)
    {
        super(position, velocity, size,image);
    }
}
