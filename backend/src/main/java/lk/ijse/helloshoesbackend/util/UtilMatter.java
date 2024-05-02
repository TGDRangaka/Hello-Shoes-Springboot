package lk.ijse.helloshoesbackend.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

public class UtilMatter {
    public static String generateUUID(){
        return UUID.randomUUID().toString();
    }

    public static String convertBase64(byte[] data){
//        return new String(Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8)));
        return Base64.getEncoder().encodeToString(data);
    }
}
