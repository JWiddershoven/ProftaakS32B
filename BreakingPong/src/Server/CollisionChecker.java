/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Shared.GameObject;
import java.util.ArrayList;

/**
 *
 * @author Lorenzo
 */
public class CollisionChecker
{

    public static ArrayList<GameObject> gameObjectsList = new ArrayList<>();

    /**
     * Checks if the objectToCheck collides with other GameObjects
     * @param objectToCheck
     * @return GameObject collided with
     */
    public static GameObject collidesWithFirst(GameObject objectToCheck)
    {
        if (gameObjectsList == null || gameObjectsList.isEmpty())
        {
            throw new IllegalStateException("gameObjectsList is leeg in ColliderChecker.java");
        }
        for (GameObject gb : gameObjectsList)
        {
            if (!gb.equals(objectToCheck))
            {
                if (CollisionChecker.checkCollidesWith(objectToCheck, gb))
                {
                    return gb;
                }
            }
        }
        return null;
    }
    
    /**
     * Checks if the objectToCheck collides with other GameObjects
     * @param objectToCheck
     * @param gameObjects List of all GameObjects
     * @return GameObject collided with
     */
    public static GameObject collidesWithFirst(GameObject objectToCheck, ArrayList<GameObject> gameObjects)
    {
        for (GameObject gb : gameObjects)
        {
            if (!gb.equals(objectToCheck))
            {
                if (CollisionChecker.checkCollidesWith(objectToCheck, gb))
                {
                    return gb;
                }
            }
        }
        return null;
    }

    public static ArrayList<GameObject> collidesWithMultiple(GameObject objectToCheck)
    {
        if (gameObjectsList == null || gameObjectsList.isEmpty())
        {
            throw new IllegalStateException("gameObjectsList is empty ColliderChecker.java");
        }
        ArrayList<GameObject> collidedObjects = new ArrayList<>();
        for (int i = gameObjectsList.size() - 1; i > 0; i--)
        {
            // Game is closed
            if (gameObjectsList == null)
                return new ArrayList<GameObject>();
            if (!gameObjectsList.get(i).equals(objectToCheck))
            {
                if (CollisionChecker.checkCollidesWith(objectToCheck, gameObjectsList.get(i)))
                {
                    collidedObjects.add(gameObjectsList.get(i));
                }
            }
        }
        return collidedObjects;
    }
    
    
    public static boolean checkCollidesWith(GameObject go1, GameObject go2)
    {
        return go1.getBounds().intersects(go2.getBounds());
    }

}
