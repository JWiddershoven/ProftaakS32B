/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lorenzo
 */
public class StaticConstants {
    
    public static final String SERVER_BIND_NAME = "gameServer";
    public static String SERVER_IP_ADDRESS = "127.0.0.1";
    public static final int SERVER_PORT = 1098;
    
    /**
     * SERVER_IP_ADDRESS + ":"+ SERVER_PORT;
     */
    public static String SERVER_IP_PORT = SERVER_IP_ADDRESS + ":"+ SERVER_PORT;
    /**
     * rmi://" + SERVER_IP_ADDRESS + ":" + SERVER_PORT + "/" + SERVER_BIND_NAME;
     */
    public static String SERVER_RMI_STRING = "rmi://" + SERVER_IP_ADDRESS + ":" + SERVER_PORT + "/" + SERVER_BIND_NAME;
    
    public static String getLocalIp()
    {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException ex) {
            Logger.getLogger(StaticConstants.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
}
