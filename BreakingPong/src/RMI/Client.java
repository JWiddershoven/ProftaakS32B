/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import Interfaces.IGame;
import Interfaces.IServer;
import RMIPaddleMoveTest.PaddleMoveClient;
import Shared.Ball;
import Shared.Block;
import Shared.Paddle;
import fontys.observer.RemotePropertyListener;
import java.beans.PropertyChangeEvent;
import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;

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
    private String name;
    private ArrayList<Block> blockList;
    private ArrayList<Ball> ballList;
    private ArrayList<Paddle> paddleList;
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        root = new Group();
        scene = new Scene(root, 700, 400);
        JOptionPane nameInput = new JOptionPane("Input username");
        name = JOptionPane.showInputDialog(nameInput, "Enter your username?");
        RMIUser user = new RMIUser(name, null, null, 0);
        try
        {
            clientRMI = new ClientRMI(this);
            String ip = InetAddress.getLocalHost().getHostAddress();
            connection = (IServer)Naming.lookup("rmi://127.0.0.1:1098/gameServer");
            //System.out.println(connection.receiveChat()); Throws exception but server method calling works
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        primaryStage.setScene(scene);
        primaryStage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
          public void handle(WindowEvent we) {
              clientRMI.stop();
          }
      });  
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        //Draw all blocks from server
        blockList = new ArrayList<>();
        blockList = (ArrayList<Block>) evt.getSource();
        for(Block block : blockList)
        {
            Rectangle r = new Rectangle(block.getPosition().getX(), block.getPosition().getY(), block.getSize().getX(), block.getSize().getY());
            root.getChildren().add(r);
        }
        //Draw all balls from server
        ballList = new ArrayList<>();
        ballList = (ArrayList<Ball>)evt.getSource();
        for(Ball ball : ballList)
        {
            Circle c = new Circle(ball.getPosition().getX(), ball.getPosition().getY(), 25, Color.RED);
            root.getChildren().add(c);
        }
        //Draw all paddles from server
        paddleList = new ArrayList<>();
        paddleList = (ArrayList<Paddle>)evt.getSource();
        for(Paddle paddle : paddleList)
        {
            Rectangle r = new Rectangle(paddle.getPosition().getX(), paddle.getPosition().getY(), paddle.getSize().getX(), paddle.getSize().getY());
            root.getChildren().add(r);
        }
    }
    public void keyPressed()
    {
        this.stage.getScene().setOnKeyPressed((KeyEvent k) ->
        {
        switch(k.getCode().toString())
        {
            case "W":
            {
            try {
                connection.moveLeft(1, name);
            } catch (RemoteException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            case "S":
            {
            try {
                connection.moveRight(1, name);
            } catch (RemoteException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        }
        });
    }
}
