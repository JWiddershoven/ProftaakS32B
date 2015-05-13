/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Interfaces.IServer;
import fontys.observer.BasicPublisher;
import fontys.observer.RemotePropertyListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Jordi
 */
public class RMIServer extends UnicastRemoteObject implements IServer
{
    private final BasicPublisher publisher;
    
    public RMIServer() throws RemoteException
    {
        publisher = new BasicPublisher(new String[] {"getValues"});
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
        
            @Override
            public void run()
            {
                publisher.inform(this, "getValues", null, getValues());
            }
        },0, 1500);
    }
    
    @Override
    public String[] getValues()
    {
        String[] valueNames = new String[] {"BlockPOS, PaddlePOS, BallPOS"};
        
        return null;
    }
    
    @Override
    public void addListener(RemotePropertyListener listener, String property)
    {
        this.publisher.addListener(listener, property);
        System.out.println("Listener addded");
    }
    
    @Override
    public void removeListener(RemotePropertyListener listener, String property)
    {
        this.publisher.removeListener(listener, property);
        System.out.println("Listener removed");
    }
}
