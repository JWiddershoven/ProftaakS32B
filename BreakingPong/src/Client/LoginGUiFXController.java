/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.TrayIcon;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import javafx.event.ActionEvent;

/**
 *
 * @author Lorenzo
 */
public class LoginGUiFXController implements Initializable {
    
    // Textfields
    @FXML TextField tfLoginUsername;
    @FXML TextField tfLoginPassword;
    @FXML TextField tfCreateUserUsername;
    @FXML TextField tfCreateUserEmail;
    @FXML TextField tfCreateUserPassword;
    @FXML TextField tfCreateUserReEnterPassword;    
    
    // Buttons
    @FXML Button btnLogin;
    @FXML Button btnLoginClear;
    @FXML Button btnCreateUserCreate;
    @FXML Button btnCreateUserClear;
    
    
    @FXML
    private void onLoginClick(ActionEvent evt){
        System.out.println("button clicked");
        JOptionPane.showMessageDialog(null, "Login clicked!", "Login clicked",
                TrayIcon.MessageType.INFO.ordinal(), null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String t = tfLoginUsername.getPromptText();
        System.out.println("ini");
        
    }

    
    
    
    
}
