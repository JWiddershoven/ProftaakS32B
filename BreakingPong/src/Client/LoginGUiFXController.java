/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import static Client.ClientGUI.mainStage;
import Interfaces.IClientSecurity;
import Server.Administration;
import Shared.SecurityRMI;
import java.awt.EventQueue;
import java.awt.TrayIcon;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

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

    Administration administration;

    // Login Tab
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            administration = Administration.getInstance();
        }
        catch (RemoteException ex) {
            Logger.getLogger(LoginGUiFXController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="- - - - - - - - - - - Eventhandlers - - - - - - - - - - -">>
    /**
     *
     * @param evt
     */
    @FXML
    private void onLoginClick(ActionEvent evt) {
        String username = tfLoginUsername.getText();
        String password = tfLoginPassword.getText();
        login(username, password);

    }

    /**
     *
     * @param evt
     */
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
     * Administration.java. When the login is successful will open
     * LobbySelect.fxml
     *
     * @param username The username of the user.
     * @param password The password of the user.
     */
    private void login(String username, String password) {
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in both fields.",
                    "Fields cannot be empty", TrayIcon.MessageType.INFO.ordinal());

        }
        else {
            try
            {
                IClientSecurity ics = new SecurityRMI();
                if(ClientGUI.connection.login(username, password))
                {
                    System.out.println("yay");
                }
                if (ClientGUI.CurrentSession != null)
                {

                    System.out.println("succesfully logged in.");

                    // succesvol ingelogd.
                    // TODO: Open LobbySelect.fxml
                    Parent root = FXMLLoader.load(getClass().getResource("LobbySelect.fxml"));
                    Scene scene = new Scene(root);
                    mainStage.setScene(scene);
                    mainStage.show();

                }
                else {
                    EventQueue.invokeLater(() -> {
                        JOptionPane.showMessageDialog(null, "Incorrect login info!",
                                "Incorrect login info!", TrayIcon.MessageType.WARNING.ordinal());
                    });

                }
                clearLogin();
            }
            catch (IllegalArgumentException ex) {
                EventQueue.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, ex.getMessage(),
                            "Fields cannot be empty", TrayIcon.MessageType.WARNING.ordinal());
                });

            }
            catch (IOException ex) {
                EventQueue.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, ex.getMessage(),
                            "Unexpected error in Login()", TrayIcon.MessageType.ERROR.ordinal());
                });
            }
        }

    }

    /**
     *
     * @param evt
     */
    // WAAROM KOMT HIJ NIET IN DEZE METHODE
    @FXML
    private void onCreateUserCreate(ActionEvent evt) {
        String message = createUser(tfCreateUserUsername.getText(), tfCreateUserEmail.getText(),
                tfCreateUserPassword.getText(), tfCreateUserReEnterPassword.getText());

        final String messageForDialog = message;

        if (messageForDialog != null && !messageForDialog.isEmpty()) {
            EventQueue.invokeLater(() -> {
                JOptionPane.showMessageDialog(null, messageForDialog);
            });

        }
        else {
            clearCreateUser();
            EventQueue.invokeLater(() -> {
                JOptionPane.showMessageDialog(null, "Gebruiker toegevoegd!");

            });
        }
    }

    /**
     * Validates input fields and creates the user account if all fields are
     * valid.
     *
     * @param username
     * @param email
     * @param password
     * @param repassword
     * @return Error message or empty string if account is created.
     */
    private String createUser(String username, String email, String password, String repassword) {
        if (username == null || username.trim().isEmpty()) {
            return "Username cannot be empty.";
        }
        if (password == null || password.isEmpty()) {
            return "Password cannot be empty.";
        }
        if (email == null || email.trim().isEmpty()) {
            return "Email address cannot be empty.";
        }
        if (!(email.contains("@") && email.contains("."))) {
            return "Email address is not of correct format.";
        }
        if (username.length() < 6) {
            return "Username must be at least 6 characters";
        }
        if (password.length() < 6) {
            return "Password must be at least 6 characters";
        }
        if (repassword == null || repassword.isEmpty()) {
            return "Re-password cannot be empty";
        }
        if (!password.equals(repassword)) {
            return "Passwords must match";
        }

        try {
            String returnValue = ClientGUI.connection.createUser(username, email, password);
            return returnValue;
        }
        catch (RemoteException ex) {
            Logger.getLogger(LoginGUiFXController.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
    }

    /**
     *
     * @param evt
     */
    @FXML
    private void onCreateUserClear(ActionEvent evt) {
        clearCreateUser();
    }

    private void clearCreateUser() {
        tfCreateUserEmail.clear();
        tfCreateUserPassword.clear();
        tfCreateUserReEnterPassword.clear();
        tfCreateUserUsername.clear();
    }

    // </editor-fold>
}
