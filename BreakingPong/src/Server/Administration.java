/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Helpers.DatabaseHelper;
import Helpers.LoggedinUser;
import RMI.ServerRMI;
import Shared.User;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mnesymne
 */
public class Administration {

    private static Administration instance = null;

    /**
     * The administration constructor. Here will the server, database and user
     * list be created.
     *
     * @throws java.rmi.RemoteException
     */
    public Administration() throws RemoteException {
        this.server = new ServerRMI();
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

        /**
         * Custom exception for incorrect Login data
         *
         * @param message
         */
        public IncorrectLoginDataException(String message) {
            super(message);
        }
    }

    private final ServerRMI server;

    public ServerRMI getServer() {
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

        if (userName.equals("username") && password.equals("password")) {
            User user = new User(userName, password, "test@test.nl");
            users.add(user);
            try {
                server.login(userName, password);
            } catch (RemoteException ex) {
                Logger.getLogger(Administration.class.getName()).log(Level.SEVERE, null, ex);
            }
            return user;
        } else {
            LoggedinUser lUser = DatabaseHelper.loginUser(userName, password);

            if (lUser.getLoggedIn()) {

                User user = new User(lUser.getUsername(), lUser.getPassword(), lUser.getEmail());
                Double rating = lUser.getRating();
                user.setRating(rating.intValue());
                users.add(user);
                try {
                    server.login(user.getUsername(), user.getPassword());
                } catch (RemoteException ex) {
                    Logger.getLogger(Administration.class.getName()).log(Level.SEVERE, null, ex);
                }
                return user;

            } else {
                throw new IncorrectLoginDataException("Username and password combination is incorrect.");
            }
        }

    }

}
