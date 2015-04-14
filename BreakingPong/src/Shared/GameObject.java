/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;


import java.awt.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Mnesymne
 */
public class GameObject
{

    private TVector2 position;
    private TVector2 velocity;
    private TVector2 size;
    
    private Color color;

    /**
     * JFX Color
     * @return 
     */
    public Color getColor()
    {
        return color;
    }

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
     * @param color as JAVA.AWT
     */
    public GameObject(TVector2 position, TVector2 velocity, TVector2 size, Color color)
    {
        this.position = position;
        this.velocity = velocity;
        this.size = size;
        this.color = color;
    }

    /**
     * 
     * @return Rectangle made of Pos.X, Pos.Y with size X and Y
     */
    public Rectangle getBounds()
    {
        return new Rectangle(position.getX(), position.getY(), size.getX(), size.getY());
    }
    
    @Override
    public String toString()
    {
        return "Object: " + this.position.toString();
    }
}
