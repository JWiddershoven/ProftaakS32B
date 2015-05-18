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
 *
 * @author sjorsvanmierlo
 */
public final class DatabaseHelper {

    private static Connection connection;
    private final static String username = "root";
    private final static String password = "";
    private final static String url = "jdbc:mysql://localhost:3306/BreakingBusiness";
    private final static EncryptionType encryptionType = EncryptionType.MD5;

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
            System.out.println(ex.getMessage());
            result = false;
            throw ex;
        } finally {
            closeConnection();
        }

        return result;
    }

    public static LoggedinUser loginUser(String username, String password) {

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Parameters niet correct ingevuld!");
        }
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
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            closeConnection();
        }

        if (result) {
            return new LoggedinUser(result, databaseUsername, databasePassword, databaseEmail, databaseRating);
        } else {
            return new LoggedinUser(result);
        }
    }

    public static double updateRating(String username) {

        double result = 0.0;
        try {
            initConnection();

            closeConnection();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    public static double getRating(String username) {

        double result = 0.0;
        try {
            initConnection();

            closeConnection();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    private static void initConnection() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void closeConnection() {
        try {
            connection.close();
            connection = null;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
