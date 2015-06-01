/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import java.awt.Color;
import org.junit.Test;

/**
 *
 * @author Lorenzo
 */
public class BlockTest {

    public BlockTest() {

    }

    @Test
    public void testBlock() {
        Block b = new Block(25, false, null, new TVector2(25.0f, 25.0f), new TVector2(0.0f, 0.0f), new TVector2(10.0f, 10.0f),null);
        
   }
}
