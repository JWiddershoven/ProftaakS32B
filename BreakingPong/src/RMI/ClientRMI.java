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
public class ClientRMI extends UnicastRemoteObject implements RemotePropertyListener
{

    private IGame game;
    private RemotePublisher publisher;
    private Client client;
    private long timeOut;
    private Registry reg;
    private String newGameTime;
    private Text gameTimeLabel;
    private Text fpsLabel;

    long nextSecond = System.currentTimeMillis() + 1000;
    int frameInLastSecond = 0;
    int framesInCurrentSecond = 0;

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
                if (Platform.isImplicitExit())
                {
                    super.cancel();
                }

                if (System.currentTimeMillis() - timeOut > 10 * 1000 || publisher == null)
                {
                    System.out.println("Attempting to setup a connection");
                    try
                    {
                        connect();
                        System.out.println("Connected!");
                    } catch (Exception ex)
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
            if (this.publisher != null)
            {
                this.publisher.removeListener(this, "getBlocks");
                this.publisher.removeListener(this, "getTime");
                this.publisher.removeListener(this, "getBalls");
                this.publisher.removeListener(this, "getPaddles");
                this.publisher.removeListener(this, "getGameOver");
            }
            UnicastRemoteObject.unexportObject(this, true);
        } catch (RemoteException ex)
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
            this.publisher.addListener(this, "getTime");
            this.publisher.addListener(this, "getBalls");
            this.publisher.addListener(this, "getPaddles");
            this.publisher.addListener(this, "getGameOver");
            this.client.connection.joinGame(1, client.Name);
            System.out.println("Game joined");
        } catch (RemoteException | NotBoundException ex)
        {
            Logger.getLogger(Stub.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            this.timeOut = System.currentTimeMillis();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException
    {
        //Draw all blocks from server
        if (evt.getPropertyName().equals("getBlocks"))
        {
            client.blockList = new ArrayList<>();
            Block[] blocks = (Block[]) evt.getNewValue();
            for (int i = 0; i < blocks.length; i++)
            {
                client.blockList.add(blocks[i]);
            }
            //System.out.println(client.blockList.size() + new Date().toString());
        }
        //Draw all balls from server
        if (evt.getPropertyName().equals("getBalls"))
        {
            client.ballList = new ArrayList<>();
            client.ballList = (ArrayList<Ball>) evt.getNewValue();
        }
        //Draw all paddles from server
        if (evt.getPropertyName().equals("getPaddles"))
        {
            client.paddleList = new ArrayList<>();
            client.paddleList = (ArrayList<Paddle>) evt.getNewValue();
        }
        // Get the gametime from server
        if (evt.getPropertyName().equals("getTime"))
        {
            Platform.runLater(new Runnable()
            {
                @Override
                public void run()
                {
                    newGameTime = evt.getNewValue().toString();
                }
            });
        }

        if (evt.getPropertyName().equals("getGameOver"))
        {
            try
            {
                System.out.println("GameOver!");
                stop();
                client.shutDown();
                return;
            } catch (Exception ex)
            {
                Logger.getLogger(ClientRMI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            drawGame();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ClientRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void drawGame() throws FileNotFoundException {
        ImagePattern pattern;
        File file;
        FileInputStream fis;
        Image img;
        Platform.runLater(new Runnable() {
            public void run()
            {
                client.root.getChildren().clear();
            }
        });

        if (client.paddleList != null)
        {
            for (Paddle paddle : client.paddleList)
            {
                Rectangle r = new Rectangle(paddle.getPosition().getX(), paddle.getPosition().getY(), paddle.getSize().getX(), paddle.getSize().getY());
                r.setFill(Color.GREEN);
                r.setStroke(Color.BLACK);
                Platform.runLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        client.root.getChildren().add(r);
                    }
                });
            }
        }

        if (client.ballList != null)
        {
            for (Ball ball : client.ballList)
            {
                Circle c = new Circle(ball.getPosition().getX(), ball.getPosition().getY(), 5);
                c.setStroke(Color.BLACK);
                c.setFill(Color.LIGHTBLUE);
                Platform.runLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        client.root.getChildren().add(c);
                    }
                });
            }
        }

        if (client.blockList != null)
        {
            for (Block block : client.blockList)
            {
                Rectangle r = new Rectangle(block.getPosition().getX(), block.getPosition().getY(), block.getSize().getX(), block.getSize().getY());
                if (block.isDestructable() == false) {
                    file = new File("src/RMI/GreyBlock.png");
                    fis = new FileInputStream(file);
                    img = new Image(fis);
                    pattern = new ImagePattern(img);
                    r.setFill(pattern);
                }
                else {
                    if (block.getPowerUp() == null) {
                        file = new File("src/RMI/YellowBlock.png");
                        fis = new FileInputStream(file);
                        img = new Image(fis);
                        pattern = new ImagePattern(img);
                        r.setFill(pattern);
                    }
                    else {
                        file = new File("src/RMI/RedBlock.png");
                        fis = new FileInputStream(file);
                        img = new Image(fis);
                        pattern = new ImagePattern(img);
                        r.setFill(pattern);
                    }
                }
                Platform.runLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        client.root.getChildren().add(r);
                    }
                });            
            }
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime > nextSecond)
        {
            nextSecond += 1000;
            frameInLastSecond = framesInCurrentSecond;
            framesInCurrentSecond = 0;
        }
        framesInCurrentSecond++;
        Platform.runLater(new Runnable()
        {

            @Override
            public void run()
            {
                gameTimeLabel = new Text(25, 25, "Time Left: " + newGameTime);
                gameTimeLabel.setFont(Font.font("Verdana", 20));
                gameTimeLabel.setFill(Color.WHITE);
                client.root.getChildren().add(gameTimeLabel);
                fpsLabel = new Text(25, 50, "FPS " + frameInLastSecond);
                fpsLabel.setFont(Font.font("Verdana", 20));
                fpsLabel.setFill(Color.WHITE);
                client.root.getChildren().add(fpsLabel);
            }
        });
    }
}
