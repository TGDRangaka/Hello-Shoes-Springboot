package lk.ijse.helloshoesbackend.util;

import java.util.Base64;
import java.util.UUID;

public class UtilMatter {
    public static String generateUUID(){
        return UUID.randomUUID().toString();
    }

    public static String convertBase64(byte[] data){
        return Base64.getEncoder().encodeToString(data).replaceAll("(\\r\\n|\\n|\\r)", "");
    }

    public static String decodeCredentials(String encodedText, int depth){
        while (depth-- > 0){
            encodedText = (new String(Base64.getDecoder().decode(encodedText)));
        }
        return encodedText;
    }
}
