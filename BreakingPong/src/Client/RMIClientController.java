/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Shared.Game;
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
public class RMIClientController extends UnicastRemoteObject implements RemotePropertyListener
{
    private RemotePublisher services;
    private Registry reg;
    private ClientGUI client;
    private long timeOut;
    
    public RMIClientController(ClientGUI client) throws RemoteException
    {
        this.client = client;
        this.start();
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void start()
    {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
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
                    connect();
                }
            }
        }, 0 , 2500);
    }
    
    private void stop()
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
            Logger.getLogger(RMIClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void connect()
    {
        try
        {
            this.reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            this.services = (RemotePublisher) this.reg.lookup("GameServer");
            this.services.addListener(this, "getValues");
        }
        catch(RemoteException | NotBoundException ex )
        {
            System.out.println(ex.getMessage());
        }
        finally
        {
            this.timeOut = System.currentTimeMillis();
        }
    }
}
