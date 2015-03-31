/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import static Client.ClientGUI.MainStage;
import Server.Administration;
import Server.Lobby;
import java.awt.HeadlessException;
import java.awt.TrayIcon;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    Button btnJoinLobby;
    @FXML
    Button btnCreateLobby;
    @FXML
    Button btnSendChat;

    
    private Administration administration;
   
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        administration = new Administration();  
    }
    
    @FXML
    private void onStartGameClick(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("InGame.fxml"));
            Scene scene = new Scene(root);
            MainStage.setScene(scene);
            MainStage.show();
        } catch (IOException | HeadlessException ex) {

        }
    }
    
}
