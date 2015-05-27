/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMIPaddleMoveTest;

import fontys.observer.RemotePropertyListener;
import fontys.observer.RemotePublisher;
import java.beans.PropertyChangeEvent;
import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 *
 * @author Jordi
 */
public class Tests extends Application {

    public int yPos = 200;
    public int xPos = 350;
    public int level = 10;
    Group root;
    Scene scene;
    Stage stage;
    Circle ball;
    IConnection connection;
    RemotePublisher publisher;
    TestController testController;

    @Override
    @SuppressWarnings("empty-statement")
    public void start(Stage primaryStage) throws Exception {

        testController = new TestController(this);
        stage = primaryStage;
        root = new Group();
        scene = new Scene(root, 700, 400);
        ball = new Circle(xPos, yPos, 20, Color.RED);
        root.getChildren().add(ball);
        primaryStage.setScene(scene);
        primaryStage.show();
        connection = testController.register();
        keyPressed();

        for(int i = 0 ; i < 100;i++)
        {
            redraw();
            Thread.sleep(5);
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    public void keyPressed() {
        this.stage.getScene().setOnKeyPressed((KeyEvent k) -> {
            switch (k.getCode().toString()) {
                case "W": {
                    if (yPos > 0) {
                        yPos = yPos - (1 * level);
                        try {
                            connection.moveUp();
                            System.out.println("Up");
                        } catch (RemoteException ex) {
                            System.out.println(ex.getMessage());
                        }
                        root.getChildren().remove(ball);
                        ball = new Circle(xPos, yPos, 20, Color.RED);
                        root.getChildren().add(ball);
                        break;
                    }
                }
                case "S": {
                    if (yPos < scene.getHeight()) {
                        yPos = yPos + (1 * level);
                        try {
                           connection.moveDown();
                            System.out.println("Down");
                        } catch (RemoteException ex) {
                            System.out.println(ex.getMessage());
                        }
                        root.getChildren().remove(ball);
                        ball = new Circle(xPos, yPos, 20, Color.RED);
                        root.getChildren().add(ball);
                        break;
                    }
                }
                 case "A": {
                    if (xPos > 0) {
                        xPos = xPos - (1 * level);
                        try {
                            connection.moveUp();
                            System.out.println("lef   t");
                        } catch (RemoteException ex) {
                            System.out.println(ex.getMessage());
                        }
                        root.getChildren().remove(ball);
                        ball = new Circle(xPos, yPos, 20, Color.RED);
                        root.getChildren().add(ball);
                        break;
                    }
                }
                 
                case "D": {
                    if (xPos < scene.getWidth()) {
                        xPos = xPos + (1 * level);
                        try {
                           connection.moveDown();
                            System.out.println("right");
                        } catch (RemoteException ex) {
                            System.out.println(ex.getMessage());
                        }
                        root.getChildren().remove(ball);
                        ball = new Circle(xPos, yPos, 20, Color.RED);
                        root.getChildren().add(ball);
                        break;
                    }
                }
                
            }
        });
    }
    
    public void redraw()
    {
        root.getChildren().remove(ball);
        ball = new Circle(xPos, yPos, 20, Color.RED);
        root.getChildren().add(ball);
    }
}
