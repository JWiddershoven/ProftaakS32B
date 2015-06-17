/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import Helpers.StaticConstants;
import Interfaces.IGame;
import RMIPaddleMoveTest.Stub;
import Shared.Ball;
import Shared.Block;
import Shared.Paddle;
import fontys.observer.RemotePropertyListener;
import fontys.observer.RemotePublisher;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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
    private String newGameTime;
    private Text gameTimeLabel;
    private Text fpsLabel;
    private boolean finished = true;
    private Text scoreLabel1;
    private Text scoreLabel2;
    private Text scoreLabel3;
    private Text scoreLabel4;
    long nextSecond = System.currentTimeMillis() + 1000;
    int frameInLastSecond = 0;
    int framesInCurrentSecond = 0;
    Image imageBall;
    Image imagePaddle;
    Image imageDestructable;
    Image imageUndestructable;
    Image ImagePowerup;

    public ClientRMI(Client client) throws RemoteException {
        this.client = client;
        this.start();
    }

    public void start() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (Platform.isImplicitExit()) {
                    super.cancel();
                }

                if (System.currentTimeMillis() - timeOut > 10 * 1000 || publisher == null) {
                    System.out.println("Attempting to setup a connection");
                    try {
                        connect();
                        System.out.println("Connected!");
                    }
                    catch (Exception ex) {
                        Logger.getLogger(Stub.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }, 0, 500);
    }

    public void stop() {
        try {
            if (this.publisher != null) {
                this.publisher.removeListener(this, "getBlocks");
                this.publisher.removeListener(this, "getTime");
                this.publisher.removeListener(this, "getBalls");
                this.publisher.removeListener(this, "getPaddles");
                this.publisher.removeListener(this, "getGameOver");
                this.publisher.removeListener(this, "getDestroys");
                this.publisher.removeListener(this, "getChanged");
            }
            UnicastRemoteObject.unexportObject(this, true);
        }
        catch (RemoteException ex) {
            Logger.getLogger(Stub.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void connect() {
        try {
            this.reg = LocateRegistry.getRegistry(StaticConstants.SERVER_IP_ADDRESS, StaticConstants.SERVER_PORT);
            this.publisher = (RemotePublisher) this.reg.lookup("gameServer");
            this.publisher.addListener(this, "getBlocks");
            this.publisher.addListener(this, "getTime");
            this.publisher.addListener(this, "getBalls");
            this.publisher.addListener(this, "getPaddles");
            this.publisher.addListener(this, "getGameOver");
            this.publisher.addListener(this, "getDestroys");
            this.publisher.addListener(this, "getChanged");
            this.client.connection.joinGame(1, client.Name);
            System.out.println("Game joined");
        }
        catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(Stub.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            this.timeOut = System.currentTimeMillis();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        //Draw all blocks from server

        if (evt.getPropertyName().equals("getBlocks")) {

            client.undestroyableblockList = new ArrayList<>();
            Block[] blocks = (Block[]) evt.getNewValue();
            for (int i = 0; i < blocks.length; i++) {
                client.undestroyableblockList.add(blocks[i]);
            }
            //System.out.println(client.blockList.size() + new Date().toString());

        }
        else if (evt.getPropertyName().equals("getDestroys")) {
            if (client.destroyableList == null || client.destroyableList.isEmpty()) {
                client.destroyableList = new ArrayList<>();
                Block[] destroyBlocks = (Block[]) evt.getNewValue();
                for (int i = 0; i < destroyBlocks.length; i++) {
                    client.destroyableList.add(destroyBlocks[i]);
                }
            }
        } //Draw all balls from server
        else if (evt.getPropertyName().equals("getBalls")) {
            client.ballList = new ArrayList<>();
            client.ballList = (ArrayList<Ball>) evt.getNewValue();
        } //Draw all paddles from server
        else if (evt.getPropertyName().equals("getPaddles")) {
            client.paddleList = new ArrayList<>();
            client.paddleList = (ArrayList<Paddle>) evt.getNewValue();
        } // Get the gametime from server
        else if (evt.getPropertyName().equals("getTime")) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    newGameTime = evt.getNewValue().toString();
                }
            });
        }
        else if (evt.getPropertyName().equals("getGameOver")) {
            try {
                System.out.println("GameOver!");
                stop();
                client.shutDown();
                return;
            }
            catch (Exception ex) {
                Logger.getLogger(ClientRMI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (evt.getPropertyName().equals("getChanged")) {
            int id = (int) evt.getNewValue();
            Block objectToRemove = null;
            try {
//                System.out.println("Changed");
                if (client.destroyableList != null) {
                    for (int i = client.destroyableList.size() - 1; i > 0; i--) {
                        if (client.destroyableList.get(i).getID() == id) {
                            objectToRemove = client.destroyableList.get(i);
                        }
                    }
                    if (objectToRemove != null) {
                        client.destroyableList.remove(objectToRemove);
                    }
                }
            }
            catch (Exception ex) {
                Logger.getLogger(ClientRMI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (evt.getPropertyName().equals("getChanged")) {
            int id = (int) evt.getNewValue();
            Block objectToRemove = null;
            try {
//                System.out.println("Changed");
                if (client.destroyableList != null) {
                    for (int i = client.destroyableList.size() - 1; i > 0; i--) {
                        if (client.destroyableList.get(i).getID() == id) {
                            objectToRemove = client.destroyableList.get(i);
                        }
                    }
                    if (objectToRemove != null) {
                        client.destroyableList.remove(objectToRemove);
                    }
                }
            }
            catch (Exception ex) {
                Logger.getLogger(ClientRMI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            drawGame();
        }
        catch (Exception ex) {
            Logger.getLogger(ClientRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  public void drawGame() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                client.root.getChildren().clear();
            }
        });

        if (client.paddleList != null) {

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    for (Paddle paddle : client.paddleList) {
                        Rectangle r = new Rectangle(paddle.getPosition().getX(), paddle.getPosition().getY(), paddle.getSize().getX(), paddle.getSize().getY());
                        r.setFill(Color.GREEN);
                        r.setStroke(Color.BLACK);
                        ImagePattern pattern = new ImagePattern(imagePaddle);
                        r.setFill(pattern);
                        client.root.getChildren().add(r);
                    }
                }
            });
        }

        if (client.ballList != null) {

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    for (Ball ball : client.ballList) {
                        Circle c = new Circle(ball.getPosition().getX(), ball.getPosition().getY(), 5);
                        c.setStroke(Color.BLACK);
                        c.setFill(Color.LIGHTBLUE);
                        ImagePattern pattern = new ImagePattern(imageBall);
                        c.setFill(pattern);
                        client.root.getChildren().add(c);
                    }
                }
            });
        }

        if (client.undestroyableblockList != null) {

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ArrayList<Block> blocks = new ArrayList(client.undestroyableblockList);
                    for (int i = blocks.size(); i > 0; i--) {
                        Block block = blocks.get(i - 1);
                        Rectangle r = new Rectangle(block.getPosition().getX(), block.getPosition().getY(), block.getSize().getX(), block.getSize().getY());
                        r.setStroke(Color.BLACK);
                        ImagePattern pattern = new ImagePattern(imageUndestructable);
                        r.setFill(pattern);
                        client.root.getChildren().add(r);
                    }
                }
            }
            );
        }

        if (client.destroyableList != null) {

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ArrayList<Block> changedBlockList = new ArrayList(client.destroyableList);
                    for (Block block : changedBlockList) {
                        Rectangle r = new Rectangle(block.getPosition().getX(), block.getPosition().getY(), block.getSize().getX(), block.getSize().getY());
                        r.setStroke(Color.BLACK);
                        if (block.isDestructable() == false) {
                            r.setFill(Color.DARKGRAY);
                        } else {
                            if (block.getPowerUp() == null) {
                                r.setFill(Color.YELLOW);
                                ImagePattern pattern = new ImagePattern(imageDestructable);
                                r.setFill(pattern);
                            } else {
                                r.setFill(Color.RED);
                                ImagePattern pattern = new ImagePattern(ImagePowerup);
                                r.setFill(pattern);
                            }
                        }
                        client.root.getChildren().add(r);
                    }
                }
            });

        }

        long currentTime = System.currentTimeMillis();
        if (currentTime > nextSecond) {
            nextSecond += 1000;
            frameInLastSecond = framesInCurrentSecond;
            framesInCurrentSecond = 0;
        }
        framesInCurrentSecond++;

        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                try {
                    gameTimeLabel = new Text(25, 25, "Time Left: " + newGameTime);
                    gameTimeLabel.setFont(Font.font("Verdana", 20));
                    gameTimeLabel.setFill(Color.WHITE);
                    client.root.getChildren().add(gameTimeLabel);

                    fpsLabel = new Text(25, 140, "FPS " + frameInLastSecond);
                    fpsLabel.setFont(Font.font("Verdana", 20));
                    fpsLabel.setFill(Color.WHITE);
                    client.root.getChildren().add(fpsLabel);

                    if (client != null && client.paddleList != null) {
                        for (int i = 0; i < client.paddleList.size(); i++) {
                            Text scoreText;
                            if (client.paddleList.get(i).getPlayer() != null) {
                                scoreText = new Text(25, 50 + (i * 20), client.paddleList.get(i).getPlayer().getUsername(null) + " : " + client.paddleList.get(i).getScore());
                            } else if (client.paddleList.get(i).getCPU() != null) {
                                scoreText = new Text(25, 50 + (i * 20), client.paddleList.get(i).getCPU().getName() + " : " + client.paddleList.get(i).getScore());
                            } else {
                                break;
                            }
                            scoreText.setFont(Font.font("Verdana", 20));
                            scoreText.setFill(Color.WHITE);
                            client.root.getChildren().add(scoreText);
                        }
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(ClientRMI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
