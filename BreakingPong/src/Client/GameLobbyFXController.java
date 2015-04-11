/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Server.Administration;
import Shared.Game;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
        administration = Administration.getInstance();
    }

    // <editor-fold defaultstate="collapsed" desc="- - - - - - - - - - - Eventhandler - - - - - - - - - - -">
    @FXML
    private void onStartGameClick() {
        
        Game game = new Game(1, 300, true);
        game.setupGame();
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
    private void onLeaveGameClick() {

    }

    @FXML
    private void onSendChatClick() {

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
