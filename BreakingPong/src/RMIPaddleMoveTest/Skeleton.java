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
public class Skeleton extends UnicastRemoteObject implements IConnection {

    private final BasicPublisher publisher;
    private int posX = 350;
    private int posY = 200;
    private String oldValue = "350,200";
    private String newValue = "";

    public Skeleton() throws RemoteException {
        publisher = new BasicPublisher(new String[]{"getValuesX","getValuesY"});
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

            }
        }, 0, 1500);
    }

    @Override
    public void addListener(RemotePropertyListener listener, String property) throws RemoteException {
        this.publisher.addListener(listener, property);
        System.out.println("Listener addded");
    }

    @Override
    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException {
        this.publisher.removeListener(listener, property);
        System.out.println("Listener removed");
    }

    @Override
    public int[] newValues(int newX, int newY) throws RemoteException {
        int[] newInts = new int[2];
        publisher.inform(this, "test", "test", "test");
        return newInts;
    }

    @Override
    public void moveUp() throws RemoteException {
        this.posY += 1;
        this.publisher.inform(this, "getValuesX", this.posY - 1, this.posY);
        System.out.println(this.posX);
        System.out.println(this.posY);
    }

    @Override
    public void moveDown() throws RemoteException {
        this.posY -= 1;
        this.publisher.inform(this, "getValuesX", this.posY - 1, this.posY);
        System.out.println(this.posX);
        System.out.println(this.posY);
    }

    @Override
    public void moveLeft() throws RemoteException {
        this.posX +=1;
        this.publisher.inform(this,"getValuesY", this.posX - 1, this.posX);
    }

    @Override
    public void moveRight() throws RemoteException {
         this.posX -=1;
        this.publisher.inform(this,"getValuesY", this.posX - 1, this.posX);
    }

    @Override
    public int getPosX() throws RemoteException {
        return this.posX;
    }

    @Override
    public int getPosY() throws RemoteException {
        return this.posY;
    }

}
