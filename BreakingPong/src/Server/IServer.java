/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import fontys.observer.RemotePublisher;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Lorenzo
 */
public interface IServer extends Remote, RemotePublisher
{
    public String[] getValues() throws RemoteException;
}
