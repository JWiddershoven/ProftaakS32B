/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.sql.Timestamp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Lorenzo
 */
public class Database {

    private final ObservableList<Timestamp> gameDurations = FXCollections.observableArrayList();

    public ObservableList<Timestamp> getGameTimeDurations() {
        return gameDurations;
    }

    /**
     * Database contrusctor
     */
    public Database() {
        fillGameDurations();
    }

    /**
     * Moet verwijderd / aangepast worden als er een database connectie is.
     */
    private void fillGameDurations() {
        // 5 minuten
        gameDurations.add(new Timestamp(0, 0, 0, 0, 5, 0, 0));
        gameDurations.add(new Timestamp(0, 0, 0, 0, 10, 0, 0));
        gameDurations.add(new Timestamp(0, 0, 0, 0, 15, 0, 0));
    }

}
