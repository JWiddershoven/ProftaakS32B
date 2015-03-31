/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import static Client.ClientGUI.MainStage;
import Server.Administration;
import java.awt.TrayIcon;
import java.net.URL;
import java.util.ResourceBundle;
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
    ComboBox cbMap;
    @FXML
    ComboBox cbGametime;

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
    }

// <editor-fold defaultstate="collapsed" desc="Eventhandlers">
    
    
    @FXML
    private void onCreateLobbyClick() {
        System.out.println("Lobby created");
    }

    @FXML
    private void onCancelClick() {
        System.out.println("Cancel click");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("LobbySelect.fxml"));
            Scene scene = new Scene(root);
            MainStage.setScene(scene);
            MainStage.show();
        } catch (Exception ex) {

        }
    }

    @FXML
    private void onHelpAboutClick() {
        JOptionPane.showMessageDialog(null, "Breaking Pong\nBy Breaking Business",
                "About", TrayIcon.MessageType.INFO.ordinal());
    }

    @FXML
    private void onFileExitClick() {
        int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION);
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
