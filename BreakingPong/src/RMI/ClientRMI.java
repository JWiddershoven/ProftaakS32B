/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import Interfaces.IGame;
import RMIPaddleMoveTest.Stub;
import Shared.Ball;
import Shared.Block;
import Shared.Paddle;
import fontys.observer.RemotePropertyListener;
import fontys.observer.RemotePublisher;
import java.beans.PropertyChangeEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Jordi
 */
public class ClientRMI extends UnicastRemoteObject implements RemotePropertyListener {

    private IGame game;
    private RemotePublisher publisher;
    private Client client;
    private long timeOut;
    private Registry reg;
    
    public ClientRMI(Client client) throws RemoteException
    {
        this.client = client;
        this.start();
    }
    
    public void start()
    {
        Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                if(Platform.isImplicitExit()) 
                {
                    super.cancel();
                }
               
                if(System.currentTimeMillis() - timeOut > 10*1000 || publisher == null)
                {
                    System.out.println("Attempting to setup a connection");
                    try
                    {
                        connect();
                    }
                    catch(Exception ex)
                    {
                        Logger.getLogger(Stub.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
        }, 0, 2500);
    }
    
    public void stop()
    {
        try
        {
            if(this.publisher != null)
            {
                this.publisher.removeListener(this, "getBlocks");
                this.publisher.removeListener(this, "getTime");
            }
            UnicastRemoteObject.unexportObject(this, true);
        }
        catch(RemoteException ex)
        {
            Logger.getLogger(Stub.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void connect()
    {
        try
        {
            this.reg = LocateRegistry.getRegistry("127.0.0.1", 1098);
            this.publisher = (RemotePublisher) this.reg.lookup("gameServer");
            this.publisher.addListener(this, "getBlocks");
            this.publisher.addListener(this,"getTime");
        }
        catch(RemoteException | NotBoundException ex )
        {
            Logger.getLogger(Stub.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            this.timeOut = System.currentTimeMillis();
        }
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        System.out.println("kut");
                //Draw all blocks from server
        if (evt.getPropertyName().equals("getBlocks")) {
            client.blockList = new ArrayList<>();
            client.blockList = (ArrayList<Block>) evt.getNewValue();
            System.out.println(client.blockList.size());
//            for (Block block : client.blockList) {
//                Rectangle r = new Rectangle(block.getPosition().getX(), block.getPosition().getY(), block.getSize().getX(), block.getSize().getY());
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run()
//                    {
//                        client.root.getChildren().clear();
//                        client.root.getChildren().add(r);
//                    }
//                });
//            }
        }
        //Draw all balls from server
        if(evt.getPropertyName().equals("getBalls"))
        {
            client.ballList = new ArrayList<>();
            client.ballList = (ArrayList<Ball>) evt.getSource();
//            for (Ball ball : client.ballList) {
//                Circle c = new Circle(ball.getPosition().getX(), ball.getPosition().getY(), 25, Color.RED);
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run()
//                    {
//                       
//                        client.root.getChildren().clear();
//                        client.root.getChildren().add(c);
//                    }
//                });
//            }            
        }
        //Draw all paddles from server
        if(evt.getPropertyName().equals("getPaddles"))
        {
            client.paddleList = new ArrayList<>();
            client.paddleList = (ArrayList<Paddle>) evt.getNewValue();
//            for (Paddle paddle : client.paddleList) {
//                Rectangle r = new Rectangle(paddle.getPosition().getX(), paddle.getPosition().getY(), paddle.getSize().getX(), paddle.getSize().getY());
//              Platform.runLater(new Runnable() {
//                    @Override
//                    public void run()
//                    {
//                        client.root.getChildren().clear();
//                        client.root.getChildren().add(r);
//                    }
//                });
//            }        
        }
        // Get the gametime from server
        if(evt.getPropertyName().equals("getTime"))
        {
            client.gameTime = (int) evt.getNewValue();
            System.out.println(client.gameTime);
        }
        
        drawGame();
    }
    
    public void drawGame()
    {
        if(client.paddleList != null)
        {
            for (Paddle paddle : client.paddleList) {
            Rectangle r = new Rectangle(paddle.getPosition().getX(), paddle.getPosition().getY(), paddle.getSize().getX(), paddle.getSize().getY());
            Platform.runLater(new Runnable() {
                    @Override
                    public void run()
                    {
                        client.root.getChildren().add(r);
                    }
                });
            }
        }
        
        if(client.ballList != null)
        {
            for (Ball ball : client.ballList) {
            Circle c = new Circle(ball.getPosition().getX(), ball.getPosition().getY(), 25, Color.RED);
            Platform.runLater(new Runnable() {
                @Override
                public void run()
                {
                    client.root.getChildren().add(c);
                }
            });
            }    
        }
        
        if(client.blockList != null)
        {
            for (Block block : client.blockList) {
            Rectangle r = new Rectangle(block.getPosition().getX(), block.getPosition().getY(), block.getSize().getX(), block.getSize().getY());
            r.setStroke(Color.BLACK);
            if(block.isDestructable() == true)
            {
                r.setFill(Color.DARKGRAY);
            }
            else
            {
                if(block.getPowerUp() == null)
                {
                    r.setFill(Color.YELLOW);
                }
                else
                {
                    r.setFill(Color.RED);
                }
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run()
                {
                    client.root.getChildren().add(r);
                }
            });
        }}
    }
}
