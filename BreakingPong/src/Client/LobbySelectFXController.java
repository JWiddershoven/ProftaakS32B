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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;

/**
 *
 * @author Lorenzo
 */
public class LobbySelectFXController implements Initializable {

    // Textfields
    @FXML
    PasswordField tfChatInput;

    // Buttons
    @FXML
    Button btnJoinLobby;
    @FXML
    Button btnCreateLobby;
    @FXML
    Button btnSendChat;

    // Listviews
    @FXML ListView lvChat;
    @FXML ListView lvLobbies;
    @FXML ListView lvOnlineUsers;
    
    
    // Menuitems
    @FXML MenuItem miFile;
    @FXML MenuItem miFileClose;
    @FXML MenuItem miEdit;
    @FXML MenuItem miEditDelete;
    @FXML MenuItem miHelp;
    @FXML MenuItem miHelpAbout;
    
    private Administration administration;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        administration = new Administration();

    }




    /

}
