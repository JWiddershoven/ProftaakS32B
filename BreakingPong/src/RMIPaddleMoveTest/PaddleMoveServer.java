/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMIPaddleMoveTest;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Jordi
 */
public class PaddleMoveServer extends Application
{
    private IConnection connection;
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        System.setProperty("java.rmi.server.hostname", "192.168.1.1");        
        System.out.println("Starting Server");
        try
        {
            connection = new Skeleton();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("gameServer", connection);
        }
        catch(RemoteException ex)
        {
            System.out.println(ex.getMessage());
            Logger.getLogger(PaddleMoveServer.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
        System.out.println("Gameserver executed. Listening for commands");
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }
}
