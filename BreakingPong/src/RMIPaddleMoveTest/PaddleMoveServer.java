/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMIPaddleMoveTest;

import RMI.RMIGame;
import RMI.RMIUser;
import Shared.Paddle;
import Shared.User;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Jordi
 */
public class PaddleMoveServer extends Application
{
    private int playerAmount;
    private ArrayList<RMIUser> playerList;
    RMIGame game;
    private IConnection connection;
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        System.setProperty("java.rmi.server.hostname", "127.0.0.1");        
        System.out.println("Starting Server");
        try
        {
            connection = new Skeleton();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("gameServer", connection);
        }
        catch(RemoteException ex)
        {
            Logger.getLogger(PaddleMoveServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        //RMIGame game = new RMIGame(1, 300, true,playerList);
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }
}
