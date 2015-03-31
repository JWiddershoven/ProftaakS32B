/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Server.Administration;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 *
 * @author Lorenzo
 */
public class CreateLobbyFXController implements Initializable {

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
    private void onCreateLobbyClick(){
        System.out.println("Lobby created");
    }
    
}
