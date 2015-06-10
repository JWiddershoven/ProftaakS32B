/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import Interfaces.IUser;
import Shared.Paddle;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 *
 * @author Mnesymne
 */
public class RMIUser implements IUser, Serializable {

    //--------------------------------------------//

    private String username, password, email;
    private Paddle paddle;
    //private Server selectedServer;
    private int rating;

    //-----------------------------------
    public RMIUser(String username, String password, String email, int rating) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.rating = rating;
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }

    @Override
    public String getPlayerInformation(String username) throws RemoteException {
        return username + " - " + rating;
    }

    @Override
    public String getUsername(IUser user) {
        return username;
    }

}
