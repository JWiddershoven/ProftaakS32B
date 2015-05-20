/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import static Client.ClientGUI.mainStage;
import Server.Administration;
import Server.Lobby;
import Shared.User;
import java.awt.TrayIcon;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class LobbySelectFXController implements Initializable
{

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

    private static Administration administration;

    private final ObservableList<User> onlineUsersList
            = FXCollections.observableArrayList();
    private final ObservableList<Lobby> lobbiesList
            = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try {
            administration = Administration.getInstance();
        } catch (RemoteException ex) {
            Logger.getLogger(LobbySelectFXController.class.getName()).log(Level.SEVERE, null, ex);
        }

        fillListViews();
    }

    private void fillListViews()
    {
        try
        {
            User user1 = new User("testuser1", "123456", "test@em.nl", administration.getServer());
            User user2 = new User("testuser2", "123456", "test@em.nl", administration.getServer());

            onlineUsersList.clear();
            
            administration.getServer().getOnlineUsers().add(user1);
            administration.getServer().getOnlineUsers().add(user2);
            onlineUsersList.addAll(administration.getServer().getOnlineUsers());
            lvOnlineUsers.setItems(onlineUsersList);

            Lobby lobby1 = new Lobby(1, "Test Lobby1", "123", user1, (byte) 4, administration.getServer());
            Lobby lobby2 = new Lobby(2, "Test Lobby2", "", user2, (byte) 2, administration.getServer());
            lobby1.joinLobby(user1);
            lobby2.joinLobby(user2);
            administration.getServer().getLobbys().add(lobby1);
            administration.getServer().getLobbys().add(lobby2);

            lobbiesList.clear();
            lobbiesList.addAll(administration.getServer().getLobbys());
            lvLobbies.setItems(lobbiesList);
        }
        catch (Exception ex)
        {
            System.out.println("ERROR in fillListViews : " + ex.getMessage());
            throw ex;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="- - - - - - - - - - - Eventhandlers - - - - - - - - - - -">>
    @FXML
    private void onJoinLobbyClick()
    {
        System.out.println("on lobby join click");
        Lobby selectedLobby = (Lobby) lvLobbies.getSelectionModel().getSelectedItem();
        if (selectedLobby != null)
        {
            try
            {
                selectedLobby.joinLobby(ClientGUI.loggedinUser);
                // joinLobby throws exceptions, if no exception continue
                ClientGUI.joinedLobby = selectedLobby;
                Parent root = FXMLLoader.load(getClass().getResource("GameLobby.fxml"));
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(null, "Lobby join error:\n" + ex.getMessage(),
                        "Join error", TrayIcon.MessageType.INFO.ordinal());
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Please select a lobby first",
                    "Select a lobby", TrayIcon.MessageType.INFO.ordinal());
        }
    }

    @FXML
    private void onCreateLobbyClick()
    {
        System.out.println("on lobby create click");
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("CreateLobby.fxml"));

            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.show();
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Create lobby error:\n" + ex.getMessage(),
                    "Create error", TrayIcon.MessageType.INFO.ordinal());
        }
    }

    @FXML
    private void onSendChatClick()
    {
        System.out.println("sent chat!");
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
