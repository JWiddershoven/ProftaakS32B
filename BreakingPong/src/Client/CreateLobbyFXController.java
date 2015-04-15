/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import static Client.ClientGUI.mainStage;
import Server.Administration;
import Shared.Map;
import java.awt.HeadlessException;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javax.swing.JOptionPane;

/**
 *
 * @author Lorenzo
 */
public class CreateLobbyFXController implements Initializable
{

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

    // ToggleGroups
    ToggleGroup rgroupPlayers;
    ToggleGroup rgroupPowerups;

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
        administration = Administration.getInstance();

        fillComboboxes();
    }

    /**
     * Fills comboboxes with data.
     */
    private void fillComboboxes()
    {
//        ObservableList<String> timeStamps = FXCollections.observableArrayList();
//        for (Timestamp ts : administration.getDatabase().getGameTimeDurations())
//        {
//            timeStamps.add(Integer.toString(ts.getHours()) + ":" + Integer.toString(ts.getMinutes()) + ":" + Integer.toString(ts.getSeconds()));
//        }
//       
                
        cbGametimes.setItems(administration.getDatabase().getTimesstampString());
        cbGametimes.getSelectionModel().select(0);
        cbMappen.setItems(administration.getServer().getMappenObservableList());
        cbMappen.getSelectionModel().select(0);

    }

// <editor-fold defaultstate="collapsed" desc="- - - - - - - - - - - Eventhandlers - - - - - - - - - - -">
    @FXML
    private void onCreateLobbyClick()
    {
        try
        {
            String errorMessage = createLobby();
            if (errorMessage.isEmpty())
            {

                Parent root = FXMLLoader.load(getClass().getResource("GameLobby.fxml"));
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
            } else
            {
                JOptionPane.showConfirmDialog(null, errorMessage, "Cannot create lobby",
                        JOptionPane.CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException | HeadlessException ex)
        {

        }
    }

    /**
     * *
     * First validates input fields then creates the lobby if fields are valid
     *
     * @return Error message or empty is fields are valid.
     */
    private String createLobby()
    {
        String lobbyname = tfLobbyName.getText().trim();
        if (lobbyname.trim().isEmpty())
        {
            return "Lobby name cannot be empty";
        }
        Map map = (Map) cbMappen.getSelectionModel().getSelectedItem();
        if (map == null)
        {
            return "Please select a Map";
        }
        Timestamp gameDuration = (Timestamp) cbGametimes.getSelectionModel().getSelectedItem();
        if (gameDuration == null)
        {
            return "Please select a Game Time";
        }
        byte maxPlayers = 2;
        if (rb2Players.isSelected())
        {
            maxPlayers = 2;
        }
        
        if(rb4Players.isSelected()){
            maxPlayers = 4;
        }
        
        try
        {
            if (ClientGUI.loggedinUser == null)
            {
                throw new Exception("Dit zou niet mogen gebeuren!");
            }
            administration.getServer().CreateLobby(lobbyname, tfPassword.getText(), ClientGUI.loggedinUser, maxPlayers, administration.getServer());
            return "";
        } catch (Exception ex)
        {
            return ex.getMessage();
        }
    }

    @FXML
    private void onCancelClick()
    {
        System.out.println("Cancel click");
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("LobbySelect.fxml"));
            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.show();
        } catch (Exception ex)
        {

        }
    }

    @FXML
    private void onHelpAboutClick()
    {
        JOptionPane.showConfirmDialog(null, "Breaking Pong\nBy Breaking Business", "About",
                JOptionPane.CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
    }

    @FXML
    private void onFileExitClick()
    {
        int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit?",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (dialogResult == JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }

    @FXML
    private void onEditDeleteClick()
    {
        System.out.println("deleted");
    }

// </editor-fold>
}
