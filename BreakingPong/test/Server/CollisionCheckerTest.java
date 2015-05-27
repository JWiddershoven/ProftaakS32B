/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Shared.Ball;
import Shared.Block;
import Shared.CPU;
import Shared.GameObject;
import Shared.Paddle;
import Shared.TVector2;
import Shared.User;
import java.awt.Image;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Lorenzo
 */
public class CollisionCheckerTest
{

    private Paddle paddle1, paddle2;
    private Block block1, block2;
    private Ball ball;
    private ArrayList<GameObject> gameObjects;
    private Image image;

    public CollisionCheckerTest()
    {

    }

    @Before
    public void setUp()
    {
        gameObjects  = new ArrayList<>();
        CPU cpu = new CPU("test", Byte.MIN_VALUE);
        TVector2 paddleSize = new TVector2(25, 10);
        TVector2 blockSize = new TVector2(10, 10);
        TVector2 velocity = TVector2.zero;
        image = null;
        TVector2 position = new TVector2(50, 50);
        paddle1 = new Paddle(10, position, velocity, paddleSize, cpu, Paddle.WindowLocation.NORTH,image);
        position = new TVector2(75, 75);
        paddle2 = new Paddle(10, position, velocity, paddleSize, cpu, Paddle.WindowLocation.SOUTH, image);
        
        position = new TVector2(30, 55);
        block1 = new Block(10, false, null, position, velocity, blockSize, image);
        position = new TVector2(50, 45);
        block2 = new Block(10, false, null, position, velocity, blockSize, image);
        position = new TVector2(45,45);
        gameObjects.add(paddle1);
        gameObjects.add(paddle2);
        gameObjects.add(block1);
        gameObjects.add(block2);
        CollisionChecker.gameObjectsList.addAll(gameObjects);
    }

    @Test    
    public void testCheckCollide()
    {
        assertEquals(block2, CollisionChecker.collidesWithFirst(paddle1));    
        CollisionChecker.gameObjectsList.clear();  
    }
}
