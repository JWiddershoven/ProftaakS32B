/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import static Client.ClientGUI.mainStage;
import RMI.RMILobby;
import Server.Lobby;
import Shared.User;
import java.awt.TrayIcon;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    @FXML
    Button btnRefresh;

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

    //private static Administration administration;
    private final ObservableList<User> onlineUsersList
            = FXCollections.observableArrayList();
    private final ObservableList<Lobby> lobbiesList
            = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        try
//        {
//            //administration = Administration.getInstance();
//        } catch (RemoteException ex)
//        {
//            Logger.getLogger(LobbySelectFXController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        fillListViews();

    }

    public void fillListViews() {

        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                try {
                    lvOnlineUsers.setItems(FXCollections.observableArrayList(ClientGUI.CurrentSession.getServer().getOnlineUsers()));
                    lvLobbies.setItems(FXCollections.observableArrayList(ClientGUI.CurrentSession.getServer().getAllLobbies()));
                } catch (Exception ex) {
                    System.out.println("ERROR in fillListViews : " + ex.getMessage());
                    Logger.getLogger(LobbySelectFXController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });

    }

    // <editor-fold defaultstate="collapsed" desc="- - - - - - - - - - - Eventhandlers - - - - - - - - - - -">>
    @FXML
    private void onJoinLobbyClick() {
        System.out.println("on lobby join click");
        RMILobby selectedLobby = (RMILobby) lvLobbies.getSelectionModel().getSelectedItem();
        if (selectedLobby != null)
        {
            try
            {
                //selectedLobby.joinLobby(ClientGUI.loggedinUser);
                selectedLobby.addUserToLobby(ClientGUI.CurrentSession.getUsername(), selectedLobby.getId());
                // joinLobby throws exceptions, if no exception continue
                ClientGUI.joinedLobby = selectedLobby;
                Parent root = FXMLLoader.load(getClass().getResource("GameLobby.fxml"));
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
            } catch (Exception ex)
            {
                Logger.getLogger(LobbySelectFXController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Lobby join error:\n" + ex.getMessage(),
                        "Join error", TrayIcon.MessageType.INFO.ordinal());
            }
        } else
        {
            JOptionPane.showMessageDialog(null, "Please select a lobby first",
                    "Select a lobby", TrayIcon.MessageType.INFO.ordinal());
        }
    }

    @FXML
    private void onCreateLobbyClick() {
        System.out.println("on lobby create click");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("CreateLobby.fxml"));

            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.show();
        } catch (Exception ex) {
            Logger.getLogger(LobbySelectFXController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Create lobby error:\n" + ex.getMessage(),
                    "Create error", TrayIcon.MessageType.INFO.ordinal());
        }
    }
    
    @FXML
    private void onRefreshClick()
    {
        fillListViews();
    }

    @FXML
    private void onSendChatClick() {
        System.out.println("sent chat!");
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
