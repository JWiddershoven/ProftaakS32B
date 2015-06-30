/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import Client.ClientGUI;
import Interfaces.IServer;
import Shared.Ball;
import Shared.Block;
import Shared.Paddle;
import fontys.observer.RemotePropertyListener;
import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Jordi
 */
public class Client extends Application implements RemotePropertyListener {
    private Stage stage;
    Group root;
    private Scene scene;
    public IServer connection;
    private ClientGUI gui;
    private ClientRMI clientRMI;
    public int gameTime;
    public ArrayList<Block> undestroyableblockList;
    public ArrayList<Ball> ballList;
    public ArrayList<Paddle> paddleList;
    public ArrayList<Paddle> paddlesIngame;
    public ArrayList<Block> destroyableList;
    private HBox hbox;
    public Text gameTimeLabel;
    public String Name;
    private int heightWindow = 800;
    private int widthWindow = 800;

    public Client(ClientGUI client, String playerName) {
        connection = client.connection;
        gui = client;
        this.Name = playerName;
    }
    
    

    
    public void start(Stage primaryStage, ClientRMI client) throws Exception {
        stage = primaryStage;
        root = new Group();
        scene = new Scene(root, widthWindow, heightWindow);

        gameTime = 0;
        // Input field for username
        //JOptionPane nameInput = new JOptionPane("Input username");
        //Name = JOptionPane.showInputDialog(nameInput, "Enter your username");
        //RMIUser user = new RMIUser("test", null, null, 0);
        // Connect to server
//        try {
//            //String ip = InetAddress.getLocalHost().getHostAddress();
//            connection = (IServer) Naming.lookup(StaticConstants.SERVER_RMI_STRING);
            if (connection != null) {
                clientRMI = client;
            }
            else {
                System.out.println("NOT CONNECTED TO THE SERVER.");
            }
//        }
//        catch (Exception ex) {
//            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
//        }
        hbox = new HBox();
        gameTimeLabel = new Text(10, 10, "GameTime");
        gameTimeLabel.setFill(Color.BLUE);
        hbox.getChildren().add(gameTimeLabel);
        root.getChildren().add(hbox);
        primaryStage.setScene(scene);
        primaryStage.show();
        keyPressed();
        // If client closes window disconnect from server
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                    clientRMI.stop();
                try {
                    System.out.println("Closed from Client.java.");
                    if (connection != null && gui.joinedLobby != null) {
                        gui.CurrentSession.getServer().leaveLobby(gui.joinedLobby.getLobbyID(), gui.CurrentSession.getUsername());
                    }
                    if (connection != null && gui.CurrentSession != null && !gui.CurrentSession.getUsername().isEmpty()) {
                        connection.logout(gui.CurrentSession.getUsername());
                    }
                }
                catch (RemoteException ex) {
                    Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                gui.CurrentSession = null;
                System.exit(0);
            }
        });
        new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 28_000_000) {
                    //   root.requestLayout();;
                    clientRMI.drawGame();
                    lastUpdate = now;
                }
            }}.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {

    }

    public void keyPressed() {
        // Client movement

        this.stage.getScene().setOnKeyPressed((KeyEvent k) ->
        {
            switch (k.getCode())
            {
                case A:
                case LEFT:
                case NUMPAD4:
                {
                    try
                    {
                        System.out.println("left");
                        connection.moveLeft(connection.getLobbyID(), this.Name);
                        
                    } catch (RemoteException ex)
                    {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }break;
                case D:
                case RIGHT:
                case NUMPAD6:
                {
                    try
                    {
                        System.out.println("right");
                        connection.moveRight(connection.getLobbyID(), this.Name);
                    }
                    catch (RemoteException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                }});
    }

    public void shutDown() {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                stage.close();
            }
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
