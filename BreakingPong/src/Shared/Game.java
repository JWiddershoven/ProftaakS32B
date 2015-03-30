/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Shared;
import java.util.ArrayList;
/**
 *
 * @author Mnesymne
 */
public class Game
{
    private int id;
    private int gameTime;
    private boolean powerUps;
    private ArrayList<Map> selectedMaps;
    private ArrayList<User> userList;
    private ArrayList<CPU> botList;
    private ArrayList<GameObject> objectList;
    private ArrayList<Ball> ballList;
    /**
     * Getter of id
     * @return id as int
     */
    public int getId() {
        return id;
    }
    /**
     * Setter of id
     * @param id value of id as int
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Getter of gameTime
     * @return gameTime as int
     */
    public int getGameTime() {
        return gameTime;
    }
    /**
     * Setter of gameTime
     * @param gameTime  value of gameTime as int
     */
    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }
    /**
     * Getter of powerUps
     * @return powerUps as boolean
     */
    public boolean getPowerUps() {
        return powerUps;
    }
    /**
     * Setter of powerUps
     * @param powerUps value of powerUps as boolean
     */
    public void setPowerUps(boolean powerUps) {
        this.powerUps = powerUps;
    }
    /**
     * Constructor of game
     * @param id value of id as int
     * @param gameTime value of gameTime as int
     * @param powerUps value of powerUps as int
     */
    public Game(int id, int gameTime, boolean powerUps) {
        this.id = id;
        this.gameTime = gameTime;
        this.powerUps = powerUps;
        
        this.botList = new ArrayList<>();
        this.userList = new ArrayList<>();
        this.objectList = new ArrayList<>();
        this.ballList = new ArrayList<>();
        this.selectedMaps = new ArrayList<>();
    }
    /**
     * Adds a CPU player to the game
     * @param botName value of botName as String
     * @param botDifficulty value of Difficulty as Byte
     */
    public void addBot(String botName, Byte botDifficulty)
    {
        CPU newBot = new CPU(botName,botDifficulty);
        botList.add(newBot);
    }
    /**
     * Removes a CPU player from the game
     * @param botName value of botName as String
     */
    public void removeBot(String botName)
    {
        for(CPU bot : botList)
        {
            if(bot.getName().equals(botName))
            {
                botList.remove(bot);
            }
        }
    }
    /**
     * Start a game if it isn't in progress.
     * A game shouldn't be able to start if the total players in game is below 2.
     * A game shouldn't be able to start if gameTime is below 120 seconds.
     */
    public void startGame()
    {
        
    }
    /**
     * Ends a game that currently is in progress.
     * A game can't be ended if it isn't in progress.
     */
    public void endGame()
    {
        
    }
}
