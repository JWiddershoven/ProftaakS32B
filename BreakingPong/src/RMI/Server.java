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
    private RMIGame game;
    
    /**
     * AMOUNT OF PLAYERS BEFORE THE GAME STARTS.
     */
    private final int playerAmount = 1;

    @Override
    public void start(Stage primaryStage) throws Exception {    

        System.out.println("Starting Server");
        playerList = new ArrayList<>();
        try {
            game = new RMIGame(1, 300, true);
            rmiService = new ServerRMI();
            Registry registry = LocateRegistry.createRegistry(1098);
            registry.rebind("gameServer", rmiService);
        }
        catch (RemoteException ex) {
            Logger.getLogger(PaddleMoveServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Server Started");

        System.out.println("Waiting for " + playerAmount + " players to join the game");

        while (game.getPlayersInformationInGame(1).size() < playerAmount) {
            Thread.sleep(2000);
            System.out.println("Waiting for " + Integer.toString(playerAmount - game.getPlayersInformationInGame(1).size()) + " players to join the game.");
        }
        System.out.println("Loading map...");

        game.loadMap("src/RMI/test4x4.txt");
        System.out.println("Starting game...");
        game.StartGame();
    }

    public void addPlayerToGame(String username) throws RemoteException {
        game.joinGame(game.getID(), username);

    }

    public static void main(String[] args) {
        launch(args);
    }

}
