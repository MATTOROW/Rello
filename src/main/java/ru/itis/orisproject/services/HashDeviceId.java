package ru.itis.orisproject.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashDeviceId {
    public static String hashString(String string) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] data = messageDigest.digest(string.getBytes());
            final StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < data.length; i++) {
                final String hex = Integer.toHexString(0xff & data[i]);
                if(hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
