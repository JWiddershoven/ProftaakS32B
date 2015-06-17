/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMIPaddleMoveTest;

import Helpers.StaticConstants;
import fontys.observer.RemotePropertyListener;
import java.beans.PropertyChangeEvent;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mnesymne
 */
public class TestController extends UnicastRemoteObject implements RemotePropertyListener {

    int yPos = 200;
    int xPos = 350;
    int level = 10;
    IConnection connection;
    Tests test;

    public TestController(Tests test) throws RemoteException {
       this.test = test;
    }

    public IConnection register() {

        try {
            connection = (IConnection) Naming.lookup(StaticConstants.SERVER_RMI_STRING);
            connection.addListener(this, "getValuesX");
            connection.addListener(this, "getValuesY");
        } catch (Exception ex) {
            Logger.getLogger(TestController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        System.out.println("ping");
      if(evt.getPropertyName().equals("getValuesX"))
      {
        test.xPos = (int)evt.getNewValue();
      }
      else
      {
        test.yPos = (int)evt.getNewValue();
      }

       
        
    }
}
