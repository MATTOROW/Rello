package ru.itis.orisproject.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordCoder {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private PasswordCoder() {
    }

    public static String encode(String password) {
        return encoder.encode(password);
    }

    public static boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
