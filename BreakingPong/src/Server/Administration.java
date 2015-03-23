/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Shared.User;
import java.util.ArrayList;

/**
 *
 * @author Mnesymne
 */
public class Administration {

    private Server server;

    private ArrayList<User> Users;

    /**
     * The administration constructor. Here will the server and user list be
     * created.
     */
    public Administration() {
        this.server = new Server();
        this.Users = new ArrayList<>();
    }

    /**
     * The username will be checked in the database When the username exists and
     * the password matches If there is a match the user can login on the server
     *
     * @param userName The username of the user
     * @param password The password of the user
     */
    public void login(String userName, String password) {

        if (userName == null || userName.isEmpty() || userName.equals("")) {
            throw new IllegalArgumentException("Username is not correct!");
        }

        if (password == null || password.isEmpty() || userName.equals("")) {
            throw new IllegalArgumentException("Password is not correct!");
        }

        //Check in the database if the user exists
        //check the username with the password
        //Create a User
        //Add user to users
    }
}
