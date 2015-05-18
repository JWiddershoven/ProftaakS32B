/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import static Client.ClientGUI.mainStage;
import Helpers.DatabaseHelper;
import Server.Administration;
import java.awt.EventQueue;
import java.awt.TrayIcon;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.SQLException;
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
public class LoginGUiFXController implements Initializable
{

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
    public void initialize(URL location, ResourceBundle resources)
    {
        try {
            administration = Administration.getInstance();
        } catch (RemoteException ex) {
            Logger.getLogger(LoginGUiFXController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="- - - - - - - - - - - Eventhandlers - - - - - - - - - - -">>
    /**
     *
     * @param evt
     */
    @FXML
    private void onLoginClick(ActionEvent evt)
    {
        String username = tfLoginUsername.getText();
        String password = tfLoginPassword.getText();
        login(username, password);

    }

    /**
     *
     * @param evt
     */
    @FXML
    private void onLoginClear(ActionEvent evt)
    {
        clearLogin();
    }

    /**
     * Clears the login fields
     */
    private void clearLogin()
    {
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
    private void login(String username, String password)
    {
        if (username.trim().isEmpty() || password.trim().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Please fill in both fields.",
                    "Fields cannot be empty", TrayIcon.MessageType.INFO.ordinal());

        } else
        {
            try
            {
                
                //LoggedinUser ingelogd = DatabaseHelper.loginUser(username, password);
                
                ClientGUI.loggedinUser = administration.login(username, password);

                System.out.println("succesfully logged in.");

                // succesvol ingelogd.
                // TODO: Open LobbySelect.fxml
                Parent root = FXMLLoader.load(getClass().getResource("LobbySelect.fxml"));
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
                clearLogin();

            } catch (IllegalArgumentException ex)
            {
                JOptionPane.showMessageDialog(null, ex.getMessage(),
                        "Fields cannot be empty", TrayIcon.MessageType.WARNING.ordinal());
            } catch (Server.Administration.IncorrectLoginDataException ex)
            {
                if (ex.getMessage().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Username and password combination is incorrect",
                            "Login failed", TrayIcon.MessageType.WARNING.ordinal());
                } else
                {
                    JOptionPane.showMessageDialog(null, ex.getMessage(),
                            "Login failed", TrayIcon.MessageType.WARNING.ordinal());
                }
            } catch (IOException ex)
            {
                JOptionPane.showMessageDialog(null, ex.getMessage(),
                        "Unexpected error in Login()", TrayIcon.MessageType.ERROR.ordinal());
            }
        }

    }

    /**
     *
     * @param evt
     */
    // WAAROM KOMT HIJ NIET IN DEZE METHODE
    @FXML
    private void onCreateUserCreate(ActionEvent evt)
    {
        String message = null;
        try {
            message = createUser(tfCreateUserUsername.getText(), tfCreateUserEmail.getText(), tfCreateUserPassword.getText(),
                    tfCreateUserReEnterPassword.getText());
        } catch (RemoteException ex) {
            Logger.getLogger(LoginGUiFXController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        final String messageForDialog = message;
        
        if (!messageForDialog.isEmpty())
        {
            EventQueue.invokeLater(() -> {               
                JOptionPane.showMessageDialog(null, messageForDialog);
            });
            
        } else
        {
            clearCreateUser();
            EventQueue.invokeLater(() -> {
                JOptionPane.showMessageDialog(null, "Gebruiker toegevoegd!");
                
            });
        }
    }

    /**
     * Validates input fields and creates the user account if all fields are valid.
     * @param username
     * @param email
     * @param password
     * @param repassword
     * @return Error message or empty string if account is created.
     */
    private String createUser(String username, String email, String password, String repassword) throws RemoteException
    {
        if (username == null || username.trim().isEmpty())
        {
            return "Username cannot be empty.";
        }
        if (password == null || password.isEmpty())
        {
            return "Password cannot be empty.";
        }
        if (email == null || email.trim().isEmpty())
        {
            return "Email address cannot be empty.";
        }
        if (!(email.contains("@") && email.contains(".")))
        {
            return "Email address is not of correct format.";
        }
        if (username.length() < 6)
        {
            return "Username must be at least 6 characters";
        }
        if (password.length() < 6)
        {
            return "Password must be at least 6 characters";
        }
        if(repassword == null || repassword.isEmpty()){
            return "Re-password cannot be empty";
        }
        if(!password.equals(repassword)){
            return "Passwords must match";
        }
        
        try
        {
            boolean dbActionWorked = DatabaseHelper.registerUser(username, password, email);
            System.out.println("DBworked = " + dbActionWorked);
            
            Shared.User newUser = new Shared.User(username, password, email, Administration.getInstance().getServer());
            Administration.getInstance().getServer().addUser(newUser);
        } catch (SQLException ex)
        {
            return "Username is already taken";
        }
        return "";
    }

    /**
     *
     * @param evt
     */
    @FXML
    private void onCreateUserClear(ActionEvent evt)
    {
        clearCreateUser();
    }
    
    private void clearCreateUser(){
        tfCreateUserEmail.clear();
        tfCreateUserPassword.clear();
        tfCreateUserReEnterPassword.clear();
        tfCreateUserUsername.clear();
    }

    // </editor-fold>
}
