/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Shared.User;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Mnesymne
 */
public class Administration {

    private static Administration instance = null;

    /**
     * The administration constructor. Here will the server, database and user
     * list be created.
     * @throws java.rmi.RemoteException
     */
    protected Administration() throws RemoteException {
        this.server = new Server();
        this.database = new Database();
        this.users = new ArrayList<>();
    }

    /**
     * The singleton for administration
     *
     * @return the administration
     * @throws java.rmi.RemoteException
     */
    public static Administration getInstance() throws RemoteException {
        if (instance == null) {
            instance = new Administration();
        }

        return instance;
    }

    /**
     *
     * @author Lorenzo
     */
    public class IncorrectLoginDataException extends Exception {

        //Constructor that accepts a message
        public IncorrectLoginDataException(String message) {
            super(message);
        }
    }

    private final Server server;

    public Server getServer() {
        return server;
    }

    private final Database database;


    public Database getDatabase() {
        return database;
    }

    private final ArrayList<User> users;

    /**
     * The username will be checked in the database When the username exists and
     * the password matches If there is a match the user can login on the server
     *
     * @param userName The username of the user
     * @param password The password of the user
     * @return User is succesfully logged in
     * @throws Server.Administration.IncorrectLoginDataException
     */
    public User login(String userName, String password) throws IncorrectLoginDataException {
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

            User user = new User(userName, password, "test@test.nl", getServer());
            users.add(user);
            server.addUser(user);
            return user;

        } else {
            throw new IncorrectLoginDataException("Username and password combination is incorrect.");
        }

        //Check in the database if the user exists
        //check the username with the password
        //Create a User
        //Add user to users
    }

}
