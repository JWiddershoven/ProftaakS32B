/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Helpers.StaticConstants;
import Interfaces.ILobby;
import Interfaces.IServer;
import Shared.Session;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Lorenzo
 */
public class ClientGUI extends Application {

    public static Stage mainStage;
    public static ClientGUI instance;
   /// public static String loggedinUser;
    public static ILobby joinedLobby;
    public static RMIClientController controller;
    public static IServer connection;
    public static Session CurrentSession;
    
    
    @FXML     
       Label labelLevel;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginGUi.fxml"));
        Scene scene = new Scene(root);
        instance = this;
        try {
            ClientGUI.controller = new RMIClientController(this);
            ClientGUI.connection = (IServer) Naming.lookup(StaticConstants.SERVER_RMI_STRING);
        } catch (RemoteException ex) {
            Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        mainStage = primaryStage;
        mainStage.setTitle("Breaking Pong");
        mainStage.setScene(scene);
        mainStage.show();
        
        mainStage.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            @Override
            public void handle(WindowEvent event)
            {
                Platform.runLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            controller.stop();
                            System.out.println("Closed from ClientGUI.");
                            if (CurrentSession != null && ClientGUI.joinedLobby != null)
                                CurrentSession.getServer().leaveLobby(ClientGUI.joinedLobby.getLobbyID(), ClientGUI.CurrentSession.getUsername());
                            if (connection != null && CurrentSession != null && !CurrentSession.getUsername().isEmpty())
                                connection.logout(CurrentSession.getUsername());
                        } catch (RemoteException ex)
                        {
                            Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        CurrentSession = null;
                        System.exit(0);
                    }
                });
            }
        });
    }
    
    public void setLabel(String s)
    {
        labelLevel.setText(s);
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
