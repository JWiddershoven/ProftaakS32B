/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import static Client.ClientGUI.MainStage;
import Server.Administration;
import Server.Lobby;
import java.awt.TrayIcon;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 *
 * @author Lorenzo
 */
public class LobbySelectFXController implements Initializable {

    // Textfields
    @FXML
    TextField tfChatInput;

    // Buttons
    @FXML
    Button btnJoinLobby;
    @FXML
    Button btnCreateLobby;
    @FXML
    Button btnSendChat;

    // Listviews
    @FXML
    ListView lvChat;
    @FXML
    ListView lvLobbies;
    @FXML
    ListView lvOnlineUsers;

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

    private Administration administration;

    private final ObservableList onlineUsersList
            = FXCollections.observableArrayList();
    private final ObservableList lobbiesList
            = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        administration = new Administration();
        fillListViews();
    }

    
    private void fillListViews() {
        onlineUsersList.clear();
        onlineUsersList.addAll(administration.getServer().getOnlineUsers());
        lvLobbies.setItems(onlineUsersList);
        lobbiesList.clear();
        lobbiesList.addAll(administration.getServer().getLobbys());
        lvOnlineUsers.setItems(lobbiesList);
    }
    
    // <editor-fold defaultstate="collapsed" desc="Eventhandlers">>
    
    @FXML
    private void onJoinLobbyClick() {
        System.out.println("on lobby join click");
        try {
            Lobby selectedLobby = (Lobby) lvLobbies.getSelectionModel().getSelectedItem();
            if (selectedLobby != null) {
                Parent root = FXMLLoader.load(getClass().getResource("GameLobby.fxml"));
                Scene scene = new Scene(root);
                MainStage.setScene(scene);
                MainStage.show();
            } else {
                JOptionPane.showMessageDialog(null, "Please select a lobby first",
                        "Select a lobby", TrayIcon.MessageType.INFO.ordinal());
            }
        } catch (Exception ex) {

        }
    }

    @FXML
    private void onCreateLobbyClick() {
        System.out.println("on lobby create click");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("CreateLobby.fxml"));
            Scene scene = new Scene(root);
            MainStage.setScene(scene);
            MainStage.show();
        } catch (Exception ex) {

        }
    }

    @FXML
    private void onSendChatClick() {
        System.out.println("sent chat!");
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
