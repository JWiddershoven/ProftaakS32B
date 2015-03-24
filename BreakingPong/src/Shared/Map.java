/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

/**
 * @author Jelle
 */
public class Map {

    private String name;
    private int playerAmount;
    private int[][] layout;

    /**
     * Creates a new Map object with a name, playerAmount and layout.
     * @param name The name of the map. Name cannot be an empty string.
     * @param playerAmount The amount of players of the map. playerAmount has to
     * be 2 or 4.
     * @param layout The layout of the map. Must contain at least one item.
     */
    public Map(String name, int playerAmount, int[][] layout) {
        if (!name.equals("") && name != null && layout != null && layout.length > 1) {
            if (playerAmount == 2 || playerAmount == 4) {
                this.name = name;
                this.playerAmount = playerAmount;
                this.layout = layout;
            } else {
                throw new IllegalArgumentException();
            }

        }
        else {
            throw new IllegalArgumentException();
        }
            
    }

    /**
     * @return The name of the map.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return The amount of players of the map.
     */
    public int getPlayerAmount() {
        return this.playerAmount;
    }
    
    /**
     * @return The layout of the map.
     */
    public int[][] getLayout()
    {
        return this.layout;
    }

    /**
     * Delete the map object.
     */
    public void deleteMap() {
        
    }

    /**
     * Updates the map object.
     *
     * @param name The name of the map. Name cannot be an empty string.
     * @param playerAmount The amount of players of the map. playerAmount has to be 2 or 4.
     * @param layout The layout of the map. Must contain at least one item.
     */
    public void updateMap(String name, int playerAmount, int[][] layout) {
        if (name != null && !name.equals("") && layout != null && layout.length > 1) {
            if (playerAmount == 2 || playerAmount == 4) {
                this.name = name;
                this.playerAmount = playerAmount;
                this.layout = layout;
            }
            else {
                throw new IllegalArgumentException();
            }
        }
        else {
            throw new IllegalArgumentException();
        }
    }
}
