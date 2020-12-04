package dhbw.scanner.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

public class AESUtils {

    public static String decrypt(String encryptedMessage, String key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, generate(key));
            return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedMessage)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public static String encrypt(String plainMessage, String key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, generate(key));
            return Base64.getEncoder().encodeToString(cipher.doFinal(plainMessage.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    private static SecretKeySpec generate(String inputKey) {
        MessageDigest sha;

        try {
            byte[] key = inputKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            return new SecretKeySpec(key, "AES");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
