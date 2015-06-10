/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import static Client.ClientGUI.mainStage;
import Server.Administration;
import Server.Game;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
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
public class GameLobbyFXController implements Initializable {

    // Textfields
    @FXML
    TextField tfChatInput;

    // Buttons
    @FXML
    Button btnStartGame;
    @FXML
    Button btnLeaveGame;
    @FXML
    Button btnSendChat;
    @FXML
    Button btnKickPlayer;

    // ListViews
    @FXML
    ListView lvChat;
    @FXML
    ListView lvPlayersInGame;
    @FXML
    ListView lvPlayersInLobby;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            administration = Administration.getInstance();
        }
        catch (RemoteException ex) {
            Logger.getLogger(GameLobbyFXController.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadUserInterface();
    }

    private void loadUserInterface() {
        fillListViews();
    }

    private void fillListViews() {
//        try
//        {
//            lvPlayersInGame.setItems(FXCollections.observableArrayList(ClientGUI.CurrentSession.getServer().getPlayerInformationFromLobby(ClientGUI.joinedLobby.getLobbyID())));
//        } catch (RemoteException ex)
//        {
//            Logger.getLogger(GameLobbyFXController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    // <editor-fold defaultstate="collapsed" desc="- - - - - - - - - - - Eventhandler - - - - - - - - - - -">
    @FXML
    private void onStartGameClick() {
        try {
            if (!ClientGUI.CurrentSession.getUsername().equals(ClientGUI.joinedLobby.getOwner(ClientGUI.joinedLobby.getLobbyID()))) {
                JOptionPane.showConfirmDialog(null, "Error when starting game: Only the host can start the game.", "Error starting game",
                        JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
            }
            // GameClient game = new GameClient(1, 300, true, ClientGUI.joinedLobby.getPlayerInformationFromLobby(ClientGUI.joinedLobby.getLobbyID()));
//            Thread gameLoopThread = game.setupGame();
//            if (gameLoopThread != null)
//            {
//                gameLoopThread.start();
//            }
        }
        catch (RemoteException ex) {
            JOptionPane.showConfirmDialog(null, "Error when starting game:\n" + ex.getMessage(), "Error starting game",
                    JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception ex) {
            JOptionPane.showConfirmDialog(null, "Error when starting game:\n" + ex.getMessage(), "Error starting game",
                    JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
        }
        /*try {
                
         Parent root = FXMLLoader.load(getClass().getResource("InGame.fxml"));
         Scene scene = new Scene(root);
         mainStage.setScene(scene);
         mainStage.show();
            
         } catch (IOException | HeadlessException ex) {
         System.out.println("error");
         }*/
    }

    @FXML
    private void onLeaveGameClick() throws Exception {
        if (ClientGUI.joinedLobby == null) {
            throw new Exception("Wat heb ik gedaan? joinedLobby mag niet null zijn");
        }
        try {
            ClientGUI.joinedLobby.leaveLobby(ClientGUI.joinedLobby.getLobbyID(), ClientGUI.CurrentSession.getUsername());
        }
        catch (Exception ex) {
            JOptionPane.showConfirmDialog(null, ex.getMessage(), "Leaving game error",
                    JOptionPane.CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
        }
        Parent root = FXMLLoader.load(getClass().getResource("LobbySelect.fxml"));
        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
    }

    @FXML
    private void onSendChatClick() {
        try {
            if (!tfChatInput.getText().trim().isEmpty()) {
                System.out.println("Sent chat");
                ClientGUI.CurrentSession.getServer().sendChat(tfChatInput.getText());
                tfChatInput.setText("");
            }
        }
        catch (RemoteException ex) {
            Logger.getLogger(GameLobbyFXController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showConfirmDialog(null, ex.getMessage(), "Sending chat error",
                    JOptionPane.CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
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

    @FXML
    private void onKickPlayerClick() throws RemoteException, Exception {
        boolean result = false;

        if (lvPlayersInGame.getSelectionModel().getSelectedItem() == null) {
            JOptionPane.showConfirmDialog(null, "Error: Select a player.", "Error",
                    JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!ClientGUI.CurrentSession.getUsername().equals(ClientGUI.joinedLobby.getOwner(ClientGUI.joinedLobby.getLobbyID()))) {
            JOptionPane.showConfirmDialog(null, "Error: Only the host can kick a player.", "Error",
                    JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (ClientGUI.joinedLobby == null) {
            throw new Exception("Lobby kan niet null zijn!");
        }

        try {
            result = ClientGUI.CurrentSession.getServer().kickPlayer(lvPlayersInGame.getSelectionModel().getSelectedItem().toString(), ClientGUI.joinedLobby.getLobbyID());
        }
        catch (IllegalArgumentException exc) {

        }

        if (result) {
            JOptionPane.showConfirmDialog(null, "Succes: The user has been kicked from the lobby.", "Success",
                    JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
        }
        else {
            JOptionPane.showConfirmDialog(null, "Error: Something went wrong, unable to kick user.\nTry again.", "Error",
                    JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
        }
    }

// </editor-fold>
}
