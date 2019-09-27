package fr.delaporte.laboratoireGSB.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {

    private static byte[] SALT = ("laboGSB").getBytes();

    /**
     * Retourne une chaine de caractère haché sous le format SHA 512.
     *
     * @param passwordToHash
     * @return generatedPassword
     */
    public static String encryptSHA512(String passwordToHash){

        String generatedPassword = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(SALT);

            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();

            for(int i= 0; i< bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return generatedPassword;
    }
}
