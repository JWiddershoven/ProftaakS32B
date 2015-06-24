/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The database helper
 *
 * @author sjorsvanmierlo
 */
public final class DatabaseHelper {

    private static Connection connection;

    //MAC local
    //private final static String username = "root";
    //private final static String password = "";
    //private final static String url = "jdbc:mysql://localhost:3306/BreakingBusiness";
    //Athena
    private final static String username = "dbi319081";
    private final static String password = "gb7tVqUdKI";
    private final static String url = "jdbc:mysql://athena01.fhict.local:3306/dbi319081";
    private final static EncryptionType encryptionType = EncryptionType.MD5;

    /**
     * Register a new user to the game.
     *
     * @param username The username of the new user. Has to be unique.
     * @param password The password of the new user. Has to be 6 characters
     * long.
     * @param email The email of the new user. Must contain @ and a . to be
     * valid.
     * @return Returns true if the user is registerd else false.
     * @throws SQLException If SQL fails , excetpion will be thrown to GUI to
     * handle.
     */
    public static boolean registerUser(String username, String password, String email) throws SQLException {

        if (username == null || password == null || email == null || username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            throw new IllegalArgumentException("Parameters niet correct ingevuld!");
        }
        double defaultRating = 0.0;
        String hashedPassword = null;
        try {
            hashedPassword = EncryptionHelper.hashString(password.trim(), encryptionType);
        } catch (Exception ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean result = false;
        try {
            initConnection();

            PreparedStatement prepStatement = connection.prepareStatement(
                    "INSERT INTO Administration("
                    + "Username,"
                    + "Password,"
                    + "Email,"
                    + "Rating)"
                    + " VALUES (?,?,?,?)");

            prepStatement.setString(1, username.trim());
            prepStatement.setString(2, hashedPassword);
            prepStatement.setString(3, email.trim());
            prepStatement.setDouble(4, defaultRating);

            prepStatement.execute();

            result = true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
            result = false;
            throw ex;
        } finally {
            closeConnection();
        }

        return result;
    }

    /**
     * Logs a user in.
     *
     * @param username The username cannot be empty or null.
     * @param password The password cannot be empty or null.
     * @return a object with user info if succeeded else object with false
     * boolean.
     */
    public static LoggedinUser loginUser(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Parameters niet correct ingevuld!");
        }

        // For testing without database
<<<<<<< HEAD
        if (username.trim().toLowerCase().startsWith("database")) {
            return new LoggedinUser(true, username, password, "database@gmail.com", 666);
        }

=======
        if (username.trim().toLowerCase().startsWith("database"))
            return new LoggedinUser(true,username,"password","database@gmail.com",666);
        
>>>>>>> origin/master
        String hashedPassword = "";
        String databasePassword = "";
        String databaseUsername = "";
        String databaseEmail = "";
        Double databaseRating = 0.0;

        try {
            hashedPassword = EncryptionHelper.hashString(password.trim(), encryptionType);
        } catch (Exception ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean result = false;
        try {
            initConnection();

            //database actie
            if (connection != null) {
                PreparedStatement p = connection.prepareStatement("Select * FROM Administration");
                ResultSet rs = p.executeQuery();

                while (rs.next()) {
                    databaseUsername = rs.getString("Username");
                    databasePassword = rs.getString("Password");
                    databaseEmail = rs.getString("Email");
                    databaseRating = rs.getDouble("Rating");

                    if (databaseUsername != null || databasePassword != null) {
                        result = username.trim().equals(databaseUsername) && databasePassword.equals(hashedPassword);
                        if (result == true) {
                            break;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        if (result) {
            return new LoggedinUser(result, databaseUsername, databasePassword, databaseEmail, databaseRating);
        } else {
            return new LoggedinUser(result);
        }
    }

    /**
     * Updates the rating of a user.
     *
     * @param username The username cannot be empty or null.
     * @param newRating The new rating cannot be NaN.
     * @return Returns a boolean is the update is successfull.
     */
    public static boolean updateRating(String username, Double newRating) {

        if (username == null || username.isEmpty() || newRating.isNaN()) {
            throw new IllegalArgumentException("Parameters not defined!");
        }

        int updateCount = 0;

        try {
            initConnection();

            PreparedStatement p = connection.prepareStatement("UPDATE Administration SET Rating=? WHERE username = ?");
            p.setDouble(1, newRating);
            p.setString(2, username.trim());

            p.execute();
            updateCount = p.getUpdateCount();

            closeConnection();
        } catch (Exception ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (updateCount > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the rating of a user.
     *
     * @param username The username cannot be empty or null.
     * @return Returns a double with the rating.
     */
    public static double getRating(String username) {

        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username not defined!");
        }
        int count = 0;
        double result = 0.0;
        try {
            initConnection();

            PreparedStatement p = connection.prepareStatement("Select rating FROM Administration WHERE username = ?");
            p.setString(1, username.trim());
            ResultSet rs = p.executeQuery();

            while (rs.next()) {
                count++;
                result = rs.getDouble("rating");
            }
            closeConnection();
        } catch (Exception ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (count == 0) {
            throw new IllegalStateException("No rating found!");
        }

        return result;
    }

    /**
     * Initialises the database connection.
     */
    private static void initConnection() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected");
        } catch (Exception ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Closes the database connnection.
     */
    private static void closeConnection() {
        try {
            //if (connection != null)
            connection.close();
            connection = null;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
