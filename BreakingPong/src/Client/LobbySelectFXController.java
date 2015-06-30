/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import static Client.ClientGUI.mainStage;
import Interfaces.ILobby;
import Interfaces.IUser;
import RMI.RMILobby;
import RMI.RMIUser;
import fontys.observer.RemotePropertyListener;
import java.awt.EventQueue;
import java.awt.TrayIcon;
import java.beans.PropertyChangeEvent;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TextArea;
import javax.swing.JOptionPane;

/**
 *
 * @author Lorenzo
 */
public class LobbySelectFXController extends UnicastRemoteObject implements Initializable, RemotePropertyListener {

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
    ListView lvLobbies;
    @FXML
    ListView lvOnlineUsers;

    // TextAreas
    @FXML
    TextArea taChat;
    @FXML
    TextArea taChatInput;

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
    private final ObservableList<IUser> onlineUsersList
            = FXCollections.observableArrayList();
    private final ObservableList<ILobby> lobbiesList
            = FXCollections.observableArrayList();

    /**
     *
     * @throws java.rmi.RemoteException
     */
    public LobbySelectFXController() throws RemoteException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            RMIClientController.services.addListener(this, "lobbyselectChat");
            // Autoscroll to bottom
            taChat.textProperty().addListener(new ChangeListener<Object>() {
                @Override
                public void changed(ObservableValue<?> observable, Object oldValue,
                        Object newValue) {
                    taChat.setScrollTop(Double.MAX_VALUE); //this will scroll to the bottom
                    //use Double.MIN_VALUE to scroll to the top
                }
            });
            fillListViews();
            ClientGUI.controller.setLobbyController(this);
        }
        catch (Exception ex) {
            Logger.getLogger(LobbySelectFXController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fillListViews() {

        Platform.runLater(() -> {
            try {
                if (ClientGUI.CurrentSession != null) {
                    lvOnlineUsers.setItems(FXCollections.observableArrayList(ClientGUI.CurrentSession.getServer().getOnlineUsers()));
                    lvLobbies.setItems(FXCollections.observableArrayList(ClientGUI.CurrentSession.getServer().getAllLobbies()));
                }
            }
            catch (Exception ex) {
                System.out.println("ERROR in fillListViews : " + ex.getMessage());
                Logger.getLogger(LobbySelectFXController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    // <editor-fold defaultstate="collapsed" desc="- - - - - - - - - - - Eventhandlers - - - - - - - - - - -">>
    @FXML
    private void onJoinLobbyClick() {
        System.out.println("on lobby join click");
        RMILobby selectedLobby = (RMILobby) lvLobbies.getSelectionModel().getSelectedItem();
        if (selectedLobby != null) {
            try {
                if(selectedLobby.getPlayerInformationFromLobby(selectedLobby.getId()).size() < selectedLobby.getMaxPlayers())
                {
                    ClientGUI.CurrentSession.getServer().joinLobby(selectedLobby.getId(), ClientGUI.CurrentSession.getUsername());
                    ClientGUI.joinedLobby = selectedLobby;
                    Parent root = FXMLLoader.load(getClass().getResource("GameLobby.fxml"));
                    Scene scene = new Scene(root);
                    mainStage.setScene(scene);
                    mainStage.show(); 
                }
                else
                {
                   EventQueue.invokeLater(() -> { JOptionPane.showMessageDialog(null, "Selected lobby is full");});
                }
            }
            catch (Exception ex) {
                Logger.getLogger(LobbySelectFXController.class.getName()).log(Level.SEVERE, null, ex);
              EventQueue.invokeLater(() -> {  JOptionPane.showMessageDialog(null, "Lobby join error:\n" + ex.getMessage(),
                        "Join error", TrayIcon.MessageType.INFO.ordinal());});
            }
        }
        else {
            EventQueue.invokeLater(() -> {JOptionPane.showMessageDialog(null, "Please select a lobby first",
                    "Select a lobby", TrayIcon.MessageType.INFO.ordinal());});
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
        }
        catch (Exception ex) {
            Logger.getLogger(LobbySelectFXController.class.getName()).log(Level.SEVERE, null, ex);
          EventQueue.invokeLater(() -> {  JOptionPane.showMessageDialog(null, "Create lobby error:\n" + ex.getMessage(),
                    "Create error", TrayIcon.MessageType.INFO.ordinal());});
        }
    }

    @FXML
    private void onRefreshClick() {
        fillListViews();
    }

    @FXML
    private void onSendChatClick() {
        try {
            String text = taChatInput.getText().trim();
            if (text.endsWith("\n"))
                text = text.substring(0, text.length() -2);
            if (!text.isEmpty()) {
                ClientGUI.CurrentSession.getServer().sendLobbySelectChat(ClientGUI.CurrentSession.getUsername() + ": " + text + "\n");
                taChatInput.setText("");
                System.out.println("Sent chat");
            }
        }
        catch (RemoteException ex) {
            Logger.getLogger(LobbySelectFXController.class.getName()).log(Level.SEVERE, null, ex);
          EventQueue.invokeLater(() -> {  JOptionPane.showConfirmDialog(null, ex.getMessage(), "Sending chat error",
                    JOptionPane.CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);});
        }
    }

    @FXML
    private void onHelpAboutClick() {
        EventQueue.invokeLater(() -> {JOptionPane.showConfirmDialog(null, "Breaking Pong\nBy Breaking Business", "About",
                JOptionPane.CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);});
    }

    @FXML
    private void onFileExitClick() throws RemoteException {
        EventQueue.invokeLater(() -> {
            int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit?",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (dialogResult == JOptionPane.YES_OPTION) {
                try {
                    ClientGUI.CurrentSession.getServer().logout(ClientGUI.CurrentSession.getUsername());
                }
                catch (RemoteException ex) {
                    Logger.getLogger(LobbySelectFXController.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.exit(0);
            }
        });
    }

    @FXML
    private void onEditDeleteClick() {
        System.out.println("deleted");
    }

    public void addUserToList(ObservableList<RMILobby> lobbys) {
        lvOnlineUsers.setItems(lobbys);
    }

    public void addLobbyToList(ObservableList<RMIUser> users) {
        lvOnlineUsers.setItems(users);
    }
    // </editor-fold>

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        if (evt.getPropertyName().equals("lobbyselectChat")) {
            Platform.runLater(() -> {
                taChat.appendText(evt.getNewValue().toString());
            });
        }
    }
}
