/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMIPaddleMoveTest;

import fontys.observer.RemotePublisher;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Jordi
 */
public interface IConnection extends Remote, RemotePublisher
{
    public int[] newValues(int newX, int newY) throws RemoteException;
}
