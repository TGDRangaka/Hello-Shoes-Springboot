package lk.ijse.helloshoesbackend.util;

import lombok.extern.slf4j.Slf4j;
import java.util.Base64;
import java.util.UUID;

@Slf4j
public class UtilMatter {
    public static String generateUUID() {
        String uuid = UUID.randomUUID().toString();
        log.info("Generated UUID: {}", uuid);
        return uuid;
    }

    public static String convertBase64(byte[] data) {
        log.info("Converting data to Base64");
        String encodedString = Base64.getEncoder().encodeToString(data).replaceAll("(\\r\\n|\\n|\\r)", "");
        log.info("Converted data to Base64");
        return encodedString;
    }

    public static String decodeCredentials(String encodedText, int depth) {
        log.info("Decoding credentials");
        String decodedText = encodedText;
        while (depth-- > 0) {
            decodedText = new String(Base64.getDecoder().decode(decodedText));
            log.debug("Decoded text");
        }
        return decodedText;
    }
}
