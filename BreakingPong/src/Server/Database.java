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

    private final ObservableList<Timestamp> gameTijdsduren = FXCollections.observableArrayList();

    public ObservableList<Timestamp> getGameTijdsduren() {
        return gameTijdsduren;
    }

    /**
     * Database contrusctor
     */
    public Database() {
        fillGametijden();
    }

    /**
     * Moet verwijderd / aangepast worden als er een database connectie is.
     */
    private void fillGametijden() {
        // 5 minuten
        gameTijdsduren.add(new Timestamp(0, 0, 0, 0, 5, 0, 0));
        gameTijdsduren.add(new Timestamp(0, 0, 0, 0, 10, 0, 0));
        gameTijdsduren.add(new Timestamp(0, 0, 0, 0, 15, 0, 0));
    }

}
