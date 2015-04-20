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
public class TVector2
{

    private float x;
    private float y;

    /**
     * Getter of x.
     *
     * @return x as float
     */
    public float getX()
    {
        return x;
    }

    /**
     * Setter of x.
     *
     * @param x value of x as float
     */
    public void setX(float x)
    {
        this.x = x;
    }

    /**
     * Getter of y.
     *
     * @return y as float.
     */
    public float getY()
    {
        return y;
    }

    /**
     * Setter of y.
     *
     * @param y value of y as float.
     */
    public void setY(float y)
    {
        this.y = y;
    }

    /**
     * Constructor
     *
     * @param x x value of vector as float.
     * @param y y value of vector as float.
     */
    public TVector2(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * *
     * returns new TVector2(0.0f, 0.0f);
     */
    public static TVector2 zero = new TVector2(0.0f, 0.0f);

    /**
     * Returns ALWAYS POSITIVE (vector2.x + vector2.y)
     *
     * @param vector2
     * @return ALWAYS POSITIVE (vector2.x + vector2.y)
     */
    public static float total(TVector2 vector2)
    {
        TVector2 newVector2 = new TVector2(vector2.getX(), vector2.getY());
        if (newVector2.getX() < 0)
        {
            newVector2.setX(newVector2.getX() * (-1));
        }
        if (newVector2.getY() < 0)
        {
            newVector2.setY(newVector2.getY() * (-1));
        }
        return (newVector2.getX() + newVector2.getY());
    }

    /**
     * @return "x:" + this.x + " y:" + this.y;
     */
    @Override
    public String toString()
    {
        return "x:" + this.x + " y:" + this.y;
    }
}
