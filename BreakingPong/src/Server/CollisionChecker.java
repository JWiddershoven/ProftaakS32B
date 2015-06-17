/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Shared.Ball;
import Shared.GameObject;
import java.util.ArrayList;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 *
 * @author Lorenzo
 */
public class CollisionChecker {

    public static ArrayList<GameObject> gameObjectsList = new ArrayList<>();

    /**
     * Checks if the objectToCheck collides with other GameObjects
     *
     * @param objectToCheck
     * @return GameObject collided with
     */
    public static GameObject collidesWithFirst(GameObject objectToCheck) {
        if (gameObjectsList == null || gameObjectsList.isEmpty()) {
            throw new IllegalStateException("gameObjectsList is leeg in ColliderChecker.java");
        }
        for (GameObject gb : gameObjectsList) {
            if (!gb.equals(objectToCheck)) {
                if (CollisionChecker.checkCollidesWith(objectToCheck, gb)) {
                    return gb;
                }
            }
        }
        return null;
    }

    /**
     * Checks if the objectToCheck collides with other GameObjects
     *
     * @param objectToCheck
     * @param gameObjects List of all GameObjects
     * @return GameObject collided with
     */
    public static GameObject collidesWithFirst(GameObject objectToCheck, ArrayList<GameObject> gameObjects) {
        for (GameObject gb : gameObjects) {
            if (!gb.equals(objectToCheck)) {
                if (CollisionChecker.checkCollidesWith(objectToCheck, gb)) {
                    return gb;
                }
            }
        }
        return null;
    }

    public static ArrayList<GameObject> collidesWithMultiple(GameObject objectToCheck) {
        if (gameObjectsList == null || gameObjectsList.isEmpty()) {
            throw new IllegalStateException("gameObjectsList is empty ColliderChecker.java");
        }
        ArrayList<GameObject> collidedObjects = new ArrayList<>();
        for (int i = gameObjectsList.size() - 1; i >= 0; i--) {
            // Game is closed
            if (gameObjectsList == null) {
                return new ArrayList<GameObject>();
            }
            if (!gameObjectsList.get(i).equals(objectToCheck)) {
                if (CollisionChecker.checkCollidesWith(objectToCheck, gameObjectsList.get(i))) {
                    collidedObjects.add(gameObjectsList.get(i));
                }
            }
        }
        return collidedObjects;
    }

    public static boolean checkCollidesWith(GameObject go1, GameObject go2) {

//        Shape s1 = new javafx.scene.shape.Rectangle(go1.getPosition().getX(), go1.getPosition().getY(), go1.getSize().getX(), go1.getSize().getY());
//        Shape s2 = new javafx.scene.shape.Rectangle(go2.getPosition().getX(), go2.getPosition().getY(), go2.getSize().getX(), go2.getSize().getY());
//        Shape intersect = Shape.intersect(s1, s2);
//        if (intersect.getBoundsInLocal().getWidth() != -1)
//            return true;
//        return false;
        if (go1 instanceof Ball) {
            Ball b = (Ball) go1;
            if (go2 instanceof Ball) {
                Ball b2 = (Ball) go2;
                return b.getBounds().intersects(b2.getBounds().getBoundsInParent());
            }
            else {
                return b.getBounds().intersects(go2.getBounds().getBoundsInParent());
            }
        }
        else if (go2 instanceof Ball) {
            Ball b2 = (Ball) go2;
            if (go1 instanceof Ball) {
                Ball b = (Ball) go1;
                return b.getBounds().intersects(b2.getBounds().getBoundsInParent());
            }
            else {
                return go1.getBounds().intersects(b2.getBounds().getBoundsInParent());
            }
        }
        else {
            return go1.getBounds().intersects(go2.getBounds().getBoundsInParent());
        }
    }
}
