/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import static Client.ClientGUI.mainStage;
import Server.Administration;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 *
 * @author Lorenzo
 */
public class CreateLobbyFXController implements Initializable {

    // Textfields
    @FXML
    TextField tfLobbyName;
    @FXML
    PasswordField tfPassword;

    // Radiobuttons
    @FXML
    RadioButton rb2Players;
    @FXML
    RadioButton rb4Players;
    @FXML
    RadioButton rbPowerupsYes;
    @FXML
    RadioButton rbPowerupsNo;

    // Comboboxes
    @FXML
    ComboBox cbMappen;
    @FXML
    ComboBox cbGametimes;

    // Menuitems
    @FXML
    MenuItem miFile;
    @FXML
    MenuItem miFileClose;
    @FXML
    MenuItem miEdit;
    @FXML
    MenuItem miEditDelete;
    @FXML
    MenuItem miHelp;
    @FXML
    MenuItem miHelpAbout;

    // Buttons
    @FXML
    Button btnCreateLobby;
    @FXML
    Button btnCancel;

    private Administration administration;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        administration = new Administration();
        fillComboboxes();
    }

    /**
     * Fills comboboxes with data.
     */
    private void fillComboboxes() {
        ObservableList<String> timeStamps = FXCollections.observableArrayList();
        for (Timestamp ts : administration.getDatabase().getGameTimeDurations()) {
            timeStamps.add(Integer.toString(ts.getHours()) + ":" + Integer.toString(ts.getMinutes()) + ":" + Integer.toString(ts.getSeconds()));
        }
        cbGametimes.setItems(timeStamps);
        cbGametimes.getSelectionModel().select(0);
        cbMappen.setItems(administration.getServer().getMappenObservableList());
        cbMappen.getSelectionModel().select(0);

    }

// <editor-fold defaultstate="collapsed" desc="Eventhandlers">
    @FXML
    private void onCreateLobbyClick() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("GameLobby.fxml"));
            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.show();
        } catch (Exception ex) {

        }
    }

    @FXML
    private void onCancelClick() {
        System.out.println("Cancel click");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("LobbySelect.fxml"));
            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.show();
        } catch (Exception ex) {

        }
    }

    @FXML
    private void onHelpAboutClick() {
        JOptionPane.showConfirmDialog(null, "Breaking Pong\nBy Breaking Business", "About",
                JOptionPane.CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
    }

    @FXML
    private void onFileExitClick() {
        int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit?",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (dialogResult == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    @FXML
    private void onEditDeleteClick() {
        System.out.println("deleted");
    }

// </editor-fold>
}
