/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

/**
 *
 * @author Lorenzo
 */
public class ConverterHelper {

    public static String getUsernameFromUserToString(String userToString) {
        String username = userToString;
        String[] split = username.split("-");
        username = "";
        for (int i = 0; i < split.length - 1; i++) {
            username += split[i];
        }
        return username.trim();
    }

}
