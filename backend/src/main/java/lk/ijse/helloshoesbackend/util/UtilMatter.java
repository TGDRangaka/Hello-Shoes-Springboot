package lk.ijse.helloshoesbackend.util;

import java.util.Base64;
import java.util.UUID;

public class UtilMatter {
    public static String generateUUID(){
        return UUID.randomUUID().toString();
    }

    public static String convertBase64(byte[] data){
        return Base64.getEncoder().encodeToString(data);
    }
}
