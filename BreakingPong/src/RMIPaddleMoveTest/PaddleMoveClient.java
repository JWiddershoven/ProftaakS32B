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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
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
public class PaddleMoveClient extends Application implements RemotePropertyListener
{
    int yPos = 200;
    int xPos = 350;
    int level = 10;
    Group root;
    Scene scene;
    Stage stage;
    Circle ball;
    IConnection connection;
    RemotePublisher publisher;
    private Stub stub;
    @Override
    public void start(Stage primaryStage) throws Exception
    {

        stage = primaryStage;
        root = new Group();
        scene = new Scene(root, 700, 400);
        ball = new Circle(xPos, yPos, 20, Color.RED);
        root.getChildren().add(ball);
        primaryStage.setScene(scene);
        primaryStage.show();
        try
        {
            this.stub = new Stub(this);
        }
        catch(RemoteException ex)
        {
            Logger.getLogger(PaddleMoveClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        try
        {
            String ip = InetAddress.getLocalHost().getHostAddress();
            connection = (IConnection)Naming.lookup("rmi://127.0.0.1:1099/gameServer");
        }
        catch(Exception ex)
        {
            Logger.getLogger(PaddleMoveClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        keyPressed();
    }

    public void keyPressed()
    {
        this.stage.getScene().setOnKeyPressed((KeyEvent k) ->
        {
        switch(k.getCode().toString())
        {
            case "W":
            {
                if(yPos > 0)
                {
                    yPos = yPos - (1 * level);
                    try
                    {
                        connection.newValues(xPos, yPos);  
                    }
                    catch(RemoteException ex)
                    {
                        Logger.getLogger(PaddleMoveClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    root.getChildren().remove(ball);
                    ball = new Circle(xPos, yPos, 20, Color.RED);
                    root.getChildren().add(ball);
                    break;
                }
            }
            case "S":
            {
                if(yPos < scene.getHeight())
                {
                    yPos = yPos + (1 * level);
                    try
                    {
                        connection.newValues(xPos, yPos);  
                    }
                    catch(RemoteException ex)
                    {
                        Logger.getLogger(PaddleMoveClient.class.getName()).log(Level.SEVERE, null, ex);
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
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException
    {
        System.out.println("ping");
        int[] newPos = (int[])evt.getSource();
        xPos = newPos[0];
        yPos = newPos[1];
        root.getChildren().remove(ball);
        ball = new Circle(xPos, yPos, 20, Color.RED);
        root.getChildren().add(ball);
    }
}
