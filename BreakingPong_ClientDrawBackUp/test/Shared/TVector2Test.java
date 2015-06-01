/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jordi
 */
public class TVector2Test {
    
    private TVector2 vector, expected;
    public TVector2Test() {
    }
    
    @Before
    public void setUp() 
    {
        vector = new TVector2(10,10);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testGetXY() 
    {
        expected = new TVector2(10,10);
        assertEquals("Vector X is incorrect",expected.getX(),vector.getX(),1);
        assertEquals("Vector Y is incorrect", expected.getY(), vector.getY(),1);
    }
    
    @Test
    public void testSetXY()
    {
       vector.setX(11);
       vector.setY(11);
       expected = new TVector2(11,11);
       
       assertEquals("Vector X set is incorrect", expected.getX(), vector.getX(),1);
       assertEquals("Vector Y set is incorrect", expected.getY(), vector.getY(),1);
    }
}
