/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import Interfaces.IUser;
import RMIPaddleMoveTest.PaddleMoveServer;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Jordi
 */
public class Server extends Application {

    private ServerRMI rmiService;
    private ArrayList<IUser> playerList;
    private int playerAmount;

    @Override
    public void start(Stage primaryStage) throws Exception {

        System.out.println("Starting Server");
        playerList = new ArrayList<>();
        try {
            rmiService = new ServerRMI();
            Registry registry = LocateRegistry.createRegistry(1098);
            registry.rebind("gameServer", rmiService);
        }
        catch (RemoteException ex) {
            Logger.getLogger(PaddleMoveServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Server Started");
    }

    public static void main(String[] args) {
        launch(args);
    }

}
