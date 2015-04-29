/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lorenzo
 */
public class PowerUpTypeTest {

    public PowerUpTypeTest() {
    }

    /**
     * Test of values method, of class PowerUpType.
     */
    @Test
    public void testValues() {
        System.out.println("PowerUpTypeTest - testValues");
        ArrayList<PowerUpType> types = new ArrayList<PowerUpType>();
        types.add(PowerUpType.IncreasePaddleSize);
        types.add(PowerUpType.IncreaseBallSize);
        types.add(PowerUpType.DecreasePaddleSize);
        types.add(PowerUpType.DecreaseBallSize);

        PowerUpType[] expResult = new PowerUpType[types.size()];
        expResult = types.toArray(expResult);

        PowerUpType[] result = PowerUpType.values();

        assertArrayEquals(expResult, result);
    }

    /**
     * Test of valueOf method, of class PowerUpType.
     */
    @Test
    public void testValueOf() {
        System.out.println("PowerUpTypeTest - testValueOf");
        String name = "DecreaseBallSpeed";
        PowerUpType expResult = PowerUpType.DecreaseBallSpeed;
        PowerUpType result = PowerUpType.valueOf(name);
        assertEquals(expResult, result);
    }

}
