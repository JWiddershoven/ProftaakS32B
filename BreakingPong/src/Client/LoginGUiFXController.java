/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

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
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

/**
 *
 * @author Lorenzo
 */
public class LoginGUiFXController implements Initializable {

    // Textfields
    @FXML
    TextField tfLoginUsername;
    @FXML
    PasswordField tfLoginPassword;
    @FXML
    TextField tfCreateUserUsername;
    @FXML
    TextField tfCreateUserEmail;
    @FXML
    PasswordField tfCreateUserPassword;
    @FXML
    PasswordField tfCreateUserReEnterPassword;

    // Buttons
    @FXML
    Button btnLogin;
    @FXML
    Button btnLoginClear;
    @FXML
    Button btnCreateUserCreate;
    @FXML
    Button btnCreateUserClear;

    private Administration administration;

    // Login Tab
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        administration = new Administration();

    }

    @FXML
    private void onLoginClick(ActionEvent evt) {
        String username = tfLoginUsername.getText();
        String password = tfLoginPassword.getText();
        login(username, password);
    }

    @FXML
    private void onLoginClear(ActionEvent evt) {
        clearLogin();
    }

    /**
     * Clears the login fields
     */
    private void clearLogin() {
        tfLoginPassword.clear();
        tfLoginUsername.clear();
    }

    /**
     * Checks if parameters are not empty and if so calls the login function on
     * Administration.java. When the login is succesful will open
     * LobbySelect.fxml
     *
     * @param username The username of the user.
     * @param password The password of the user.
     */
    private void login(String username, String password) {
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in both fields.",
                    "Fields cannot be empty", TrayIcon.MessageType.INFO.ordinal());

        } else {
            try {
                administration.login(username, password);
                System.out.println("succesfully logged in.");
                // succesvol ingelogd.
                // TODO: Open LobbySelect.fxml
                clearLogin();
                Parent root = FXMLLoader.load(getClass().getResource("LobbySelect.fxml"));

            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(),
                        "Fields cannot be empty", TrayIcon.MessageType.INFO.ordinal());
            } catch (Server.Administration.IncorrectLoginDataException ex) {
                if (ex.getMessage().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Username and password combination is incorrect",
                            "Login failed", TrayIcon.MessageType.INFO.ordinal());
                } else {
                    JOptionPane.showMessageDialog(null, ex.getMessage(),
                            "Login failed", TrayIcon.MessageType.INFO.ordinal());
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(),
                        "Unexpected error", TrayIcon.MessageType.INFO.ordinal());
            }
        }

    }

    // Create User Tab
    @FXML
    private void onCreateUserCreate(ActionEvent evt) {

    }

    @FXML
    private void onCreateUserClear(ActionEvent evt) {
        tfCreateUserEmail.clear();
        tfCreateUserPassword.clear();
        tfCreateUserReEnterPassword.clear();
        tfCreateUserUsername.clear();
    }

}
