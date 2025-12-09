package beans;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    public static String encriptar(String pas){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(pas.getBytes());
            
            byte[] hash = md.digest();
            
            StringBuilder cad = new StringBuilder();
            
            for (byte b : hash) {
                    cad.append(String.format("%02x", b));
            }
            
            return cad.toString();
            
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }        
    }
}
