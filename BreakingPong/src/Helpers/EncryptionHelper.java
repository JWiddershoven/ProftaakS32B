/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author sjorsvanmierlo
 */
public final class EncryptionHelper {
    
    //"MD5", "SHA-1", "SHA-256"
    
    public static String hashString(String message, EncryptionType type)
        throws Exception {
 
    String algorithm;
        
   switch(type){
       case MD5:
           algorithm = "MD5";
           break;
       case SHA1:
           algorithm = "SHA-1";
           break;
       case SHA256:
           algorithm = "SHA-256";
           break;
        default:
            algorithm = "MD5";
            break;
   }
        
    try {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        byte[] hashedBytes = digest.digest(message.getBytes("UTF-8"));
 
        return convertByteArrayToHexString(hashedBytes);
    } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
        throw new Exception(
                "Could not generate hash from String", ex);
    }
}

    private static String convertByteArrayToHexString(byte[] hashedBytes) {
    StringBuffer stringBuffer = new StringBuffer();
    for (int i = 0; i < hashedBytes.length; i++) {
        stringBuffer.append(Integer.toString((hashedBytes[i] & 0xff) + 0x100, 16)
                .substring(1));
    }
    return stringBuffer.toString();    
    }
    
}
