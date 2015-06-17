/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Helpers.StaticConstants;
import Interfaces.IServer;
import fontys.observer.BasicPublisher;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Lorenzo
 */
public class ServerGUI extends Application
{
    private IServer services;
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("ServerMain.fxml"));
        Scene scene = new Scene(root);
        try
        {
            Registry registry = LocateRegistry.createRegistry(StaticConstants.PORT);
            if(registry == null)
            {
                registry = LocateRegistry.getRegistry(StaticConstants.PORT);
            }
            services = new RMIServer();
            registry.rebind("gameServer", services);
        }
        catch (RemoteException ex)
        {
            Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Gameserver executed. Listening for commands");
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
    
}
