/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Helpers.StaticConstants;
import Interfaces.IServer;
import RMI.ServerRMI;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author Lorenzo
 */
public class ServerGUI extends Application {

    private IServer rmiService;
    Registry registry;

    @FXML
    private CheckBox chbxServerOn;
    @FXML
    private Label lblStatus;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ServerMain.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Server Application");
        primaryStage.show();
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

    @FXML
    private void onCheckboxChanged(ActionEvent e) {

        try {
            if (chbxServerOn.isSelected()) {
                turnServerOn();
            }
            else {
                turnServerOff();
            }
        }
        catch (RemoteException ex) {
            Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void turnServerOn() {
        try {
            registry = LocateRegistry.createRegistry(StaticConstants.SERVER_PORT);
            if (registry == null) {
                registry = LocateRegistry.getRegistry(StaticConstants.SERVER_PORT);
            }
            rmiService = new ServerRMI();
            registry.rebind(StaticConstants.SERVER_BIND_NAME, rmiService);
            Platform.runLater(() -> lblStatus.setText("Online at " + StaticConstants.SERVER_IP_ADDRESS + ":"+ StaticConstants.SERVER_PORT));
        }
        catch (RemoteException ex) {
            Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Gameserver executed. Listening for commands");
    }

    private void turnServerOff() throws RemoteException {
        try {
            registry.unbind(StaticConstants.SERVER_BIND_NAME);
            Platform.runLater(() -> lblStatus.setText("Offline"));
        }
        catch (NotBoundException | AccessException ex) {
            Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
