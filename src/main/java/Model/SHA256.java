package Model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

public class SHA256 {
        public static String hashString(String text) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] encodedHash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
                StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
                for (byte b : encodedHash) {
                    String hex = String.format("%02x", b);
                    hexString.append(hex);
                }

                return hexString.toString();

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
    }
}
