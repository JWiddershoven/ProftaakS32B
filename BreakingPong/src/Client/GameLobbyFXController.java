/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import static Client.ClientGUI.mainStage;
import Helpers.StaticConstants;
import RMIPaddleMoveTest.Stub;
import fontys.observer.RemotePropertyListener;
import fontys.observer.RemotePublisher;
import java.awt.HeadlessException;
import java.beans.PropertyChangeEvent;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 *
 * @author Lorenzo
 */
public class GameLobbyFXController extends UnicastRemoteObject implements Initializable, RemotePropertyListener {

    // Textfields
    @FXML
    transient TextField tfChatInput;

    // Buttons
    @FXML
    transient Button btnStartGame;
    @FXML
    transient Button btnLeaveGame;
    @FXML
    transient Button btnSendChat;
    @FXML
    transient Button btnKickPlayer;

    // ListViews
    @FXML
    transient ListView lvPlayersInGame;
    @FXML
    transient ListView lvPlayersInLobby;

    // TextAreas
    @FXML
    transient TextArea taChat;

    // Menuitems
    @FXML
    transient MenuItem miFile;
    @FXML
    transient MenuItem miFileClose;
    @FXML
    transient MenuItem miEdit;
    @FXML
    transient MenuItem miEditDelete;
    @FXML
    transient MenuItem miHelp;
    @FXML
    transient MenuItem miHelpAbout;

    private RemotePublisher publisher;
    private Registry reg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connect();
        loadUserInterface();
    }
    
    public GameLobbyFXController() throws RemoteException
    {
        
    }

    public void connect() {
        try {
            this.reg = LocateRegistry.getRegistry(StaticConstants.SERVER_IP_ADDRESS, StaticConstants.SERVER_PORT);
            this.publisher = (RemotePublisher) this.reg.lookup("gameServer");
            this.publisher.addListener(this, "getChat");
            System.out.println("PropertyListener active for chat.");
        }
        catch (RemoteException | NotBoundException ex) {
            System.out.println("Failed to connect to server to listen to chat.");
            Logger.getLogger(Stub.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    private void loadUserInterface() {
        fillListViews();

        // Autoscroll to bottom
        taChat.textProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue,
                    Object newValue) {
                taChat.setScrollTop(Double.MAX_VALUE); //this will scroll to the bottom
                //use Double.MIN_VALUE to scroll to the top
            }
        });
    }

    private void fillListViews() {
        try {
            lvPlayersInGame.setItems(FXCollections.observableArrayList(ClientGUI.CurrentSession.getServer().getPlayerInformationFromLobby(ClientGUI.joinedLobby.getLobbyID())));
        }
        catch (RemoteException ex) {
            Logger.getLogger(GameLobbyFXController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            Logger.getLogger(GameLobbyFXController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showConfirmDialog(null, "Error when starting game:\n" + ex.getMessage(), "Error starting game",
                    JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
        }
        catch (HeadlessException ex) {
            Logger.getLogger(GameLobbyFXController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(GameLobbyFXController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showConfirmDialog(null, ex.getMessage(), "Leaving game error",
                    JOptionPane.CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        }
        if (this.publisher != null) {
            this.publisher.removeListener(this, "getChat");
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
                ClientGUI.CurrentSession.getServer().sendChat(tfChatInput.getText());
                tfChatInput.setText("");
                System.out.println("Sent chat");
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
            if (this.publisher != null) {
                try {
                    this.publisher.removeListener(this, "getChat");
                }
                catch (RemoteException ex) {
                    Logger.getLogger(GameLobbyFXController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
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

        if (ClientGUI.joinedLobby == null) {
            throw new Exception("Lobby kan niet null zijn!");
        }
        if (lvPlayersInGame.getSelectionModel().getSelectedItem().toString() == null) {
            JOptionPane.showConfirmDialog(null, "Error: Select a player.", "Error",
                    JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
        }

        if (!ClientGUI.CurrentSession.getUsername().equals(ClientGUI.joinedLobby.getOwner(ClientGUI.joinedLobby.getLobbyID()))) {
            JOptionPane.showConfirmDialog(null, "Error: Only the host can kick a player.", "Error",
                    JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
        }

        try {
            result = ClientGUI.CurrentSession.getServer().kickPlayer(lvPlayersInGame.getSelectionModel().getSelectedItem().toString(), ClientGUI.joinedLobby.getLobbyID());
        }
        catch (IllegalArgumentException ex) {
            Logger.getLogger(GameLobbyFXController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (result) {
            JOptionPane.showConfirmDialog(null, "Succes: The user has been kicked from the lobby.", "Success",
                    JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
        }
        else {
            JOptionPane.showConfirmDialog(null, "Error: Something went wrong, unable to kick user.\nTry again.", "Error",
                    JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        taChat.appendText(evt.getNewValue().toString());
    }

// </editor-fold>
}
