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

    private ArrayList<User> users;

    /**
     * The administration constructor. Here will the server and user list be
     * created.
     */
    public Administration() {
        this.server = new Server();
        this.users = new ArrayList<>();
    }

    /**
     * The username will be checked in the database When the username exists and
     * the password matches If there is a match the user can login on the server
     *
     * @param userName The username of the user
     * @param password The password of the user
     */
    public void login(String userName, String password) {
        if (userName == null || userName.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty!");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty!");
        }

        // login gegevens voor eerste iteratie
        String checkUsername = "username";
        String checkPassword = "password";

        if (userName.contentEquals(checkUsername) && password.contentEquals(checkPassword)) {
            //logged in
        }

        //Check in the database if the user exists
        //check the username with the password
        //Create a User
        //Add user to users
    }
}
