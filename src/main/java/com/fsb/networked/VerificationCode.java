package com.fsb.networked;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class VerificationCode {

    @FXML
    private TextField validationCodeField;

    @FXML
    private Label warningLabel;

    private String generatedCode;

    // Method to generate a random validation code
    private String generateRandomCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        // Generate a 6-character alphanumeric code
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(36);
            char c = (index < 26) ? (char) ('A' + index) : (char) ('0' + index - 26);
            sb.append(c);
        }

        return sb.toString();
    }

    // Method to handle re-sending of validation code
    @FXML
    void resendCode(ActionEvent event)throws IOException {
        App.setRoot("ForgotPassword"); // Assuming "verificationCode.fxml" is the FXML file for the verification code scene
    }

    // Method to handle code verification
    @FXML
    void verifyCode(ActionEvent event) {
        String enteredCode = validationCodeField.getText().toUpperCase(); // Convert to uppercase for case-insensitivity

        // Get the generated verification code from the Utils class
        String generatedCode = VerificationCodeGenerator.verificationCode;

        if (enteredCode.equals(generatedCode)) {
            System.out.println("Validation successful!");
            // Proceed to the next step
        } else {
            System.out.println("Incorrect validation code!");
            warningLabel.setVisible(true); // Show warning label for incorrect code
        }
    }


    // Method to handle cancellation
    @FXML
    void cancel(ActionEvent event) throws IOException {

App.setRoot("LoginPage");

    }
}
