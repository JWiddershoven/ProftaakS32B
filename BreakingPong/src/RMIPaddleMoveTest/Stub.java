/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMIPaddleMoveTest;

import Interfaces.IServer;
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
public class Stub extends UnicastRemoteObject implements RemotePropertyListener
{
    private RemotePublisher services;
    private Registry reg;
    private PaddleMoveClient client;
    private long timeOut;
    private String newValues;
    
    public Stub(PaddleMoveClient client) throws RemoteException
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
               
                if(System.currentTimeMillis() - timeOut > 10*1000 || services == null)
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
            if(this.services != null)
            {
                this.services.removeListener(this, "getValues");
            }
            UnicastRemoteObject.unexportObject(this, true);
        }
        catch(RemoteException ex)
        {
            Logger.getLogger(Stub.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException
    {
        newValues = new String(evt.getNewValue().toString());
    }
    
    public void connect()
    {
      try
        {
            this.reg = LocateRegistry.getRegistry("localhost", 1100);
            this.services = (RemotePublisher) this.reg.lookup("gameServer");
            this.services.addListener(this, "getValues");
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
    
}
