/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import Interfaces.IGame;
import RMIPaddleMoveTest.Stub;
import fontys.observer.RemotePropertyListener;
import fontys.observer.RemotePublisher;
import java.beans.PropertyChangeEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

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
                this.publisher.removeListener(this, "getValues");
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
            this.publisher.addListener(this, "getValues");
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
        System.out.println("YAY");
    }
    
}
