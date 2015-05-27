/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMIPaddleMoveTest;

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
public class Skeleton extends UnicastRemoteObject implements IConnection
{
    private final BasicPublisher publisher;
    private String oldValue = "350,200";
    private String newValue = "";
    public Skeleton() throws RemoteException
    {
        publisher = new BasicPublisher(new String[] {"getValues"});
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run()
            {
                
            }
        },0,1500);
    }

    @Override
    public void addListener(RemotePropertyListener listener, String property) throws RemoteException
    {
        this.publisher.addListener(listener, property);
        System.out.println("Listener addded");
    }

    @Override
    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException
    {
        this.publisher.removeListener(listener, property);
        System.out.println("Listener removed");
    }

    @Override
    public int[] newValues(int newX, int newY) throws RemoteException
    {
        int[] newInts = new int[2];
        publisher.inform(newInts, oldValue, oldValue, newValue);
        return newInts;
    }
    
}
