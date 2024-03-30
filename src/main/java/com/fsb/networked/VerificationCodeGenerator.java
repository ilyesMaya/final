package com.fsb.networked;

import java.util.Random;

public class VerificationCodeGenerator {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LENGTH = 6;
    public static String verificationCode; // Public variable to store the verification code

    public static String generateRandomString() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < LENGTH; i++) {
            int index = random.nextInt(ALPHABET.length());
            sb.append(ALPHABET.charAt(index));
        }

        verificationCode = sb.toString(); // Store the generated verification code
        return verificationCode;
    }
}
