/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;


import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;

/**
 *
 * @author Mnesymne
 */
public class GameObject
{

    private TVector2 position;
    private TVector2 velocity;
    private TVector2 size;
    private final Image MyImage;
    private Color color;

    /**
     * JFX Color
     * @return 
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * return new TVector2(position.getX() + (size.getX() / 2), position.getY() + (size.getY() / 2));
     * @return  new TVector2(position.getX() + (size.getX() / 2), position.getY() + (size.getY() / 2));
     */
    public TVector2 getMiddlePosition()
    {
        return new TVector2(position.getX() + (size.getX() / 2), position.getY() + (size.getY() / 2));
    }
    
    /**
     * Getter of position.
     *
     * @return position as TVector2
     */
    public TVector2 getPosition()
    {
        return position;
    }

    /**
     * Setter of position
     *
     * @param position value of position as TVector2
     */
    public void setPosition(TVector2 position)
    {
        this.position = position;
    }

    /**
     * Getter of velocity.
     *
     * @return velocity as TVector2
     */
    public TVector2 getVelocity()
    {
        return velocity;
    }

    /**
     * Setter of velocity.
     *
     * @param velocity value of velocity as TVector2
     */
    public void setVelocity(TVector2 velocity)
    {
        this.velocity = velocity;
    }

    /**
     * returns this.MyImage
     * @return this.MyImage
     */
    public Image getImage()
    {
        return this.MyImage;
    }
    /**
     * Getter of size.
     *
     * @return size as TVector2
     */
    public TVector2 getSize()
    {
        return size;
    }

    /**
     * Setter of size
     *
     * @param size value of size as TVector2
     */
    public void setSize(TVector2 size)
    {
        this.size = size;
    }

    /**
     * Constructor
     *
     * @param position value of position as TVector2.
     * @param velocity value of velocity as TVector2.
     * @param size value of size as TVector2.
     * @param image
     */
    public GameObject(TVector2 position, TVector2 velocity, TVector2 size,Image image)
    {
        this.position = position;
        this.velocity = velocity;
        this.size = size;
        this.MyImage = image;
    }

    /**
     * 
     * @return Rectangle made of Pos.X, Pos.Y with size X and Y
     */
    public Rectangle getBounds()
    {
        return new Rectangle((int)position.getX(),(int) position.getY(),(int) size.getX(),(int) size.getY());
    }
    
    @Override
    public String toString()
    {
        return "Pos: " + this.position.toString() + " Vel: " + this.velocity.toString();
    }
}
