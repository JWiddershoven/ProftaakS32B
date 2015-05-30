/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import Interfaces.IGame;
import Interfaces.IServer;
import fontys.observer.RemotePropertyListener;
import java.beans.PropertyChangeEvent;
import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Jordi
 */
public class Client extends Application implements RemotePropertyListener {

    private Stage stage;
    private Group root;
    private Scene scene;
    private IServer connection;
    private ClientRMI clientRMI;
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        root = new Group();
        scene = new Scene(root, 700, 400);
        
        try
        {
            clientRMI = new ClientRMI(this);
            String ip = InetAddress.getLocalHost().getHostAddress();
            connection = (IServer)Naming.lookup("rmi://127.0.0.1:1098/gameServer");
            System.out.println(connection.receiveChat()); // Throws exception but works
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
