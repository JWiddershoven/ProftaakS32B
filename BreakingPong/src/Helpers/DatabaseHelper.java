/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author sjorsvanmierlo
 */
public final class DatabaseHelper {

    private static Connection connection;
    private final static String username = "root";
    private final static String password = "";
    private final static String url = "jdbc:mysql://localhost:3306/BreakingBusiness";

    public static boolean registerUser(String username, String password, String email) throws SQLException {

        if (username == null || password == null || email == null || username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            throw new IllegalArgumentException("Parameters niet correct ingevuld!");
        }
        double defaultRating = 0.0;
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
            prepStatement.setString(2, password.trim());
            prepStatement.setString(3, email.trim());
            prepStatement.setDouble(4, defaultRating);

            prepStatement.execute();

            
            result = true;
        } catch (SQLException ex){            
            System.out.println(ex.getMessage());
            result = false;
            throw ex;
        } finally{
            closeConnection();
        }
        
        return result;
    }

    public static boolean loginUser(String username, String password) {

        boolean result = false;
        try {
            initConnection();

            closeConnection();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
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
