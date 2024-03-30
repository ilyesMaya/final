package com.fsb.networked;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ForgotPasswordController {
	@FXML
	private Button sendMailButton;

	@FXML
	private TextArea contextTextArea;

	@FXML
	private TextField recipientEmailField;

	@FXML
	private TextField objectTextField;



	private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final int LENGTH = 6;

	private String generateRandomString() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < LENGTH; i++) {
			int index = random.nextInt(ALPHABET.length());
			sb.append(ALPHABET.charAt(index));
		}

		return sb.toString();
	}

	@FXML
	void initialize() {
		// Set the constant text for objectTextField
		objectTextField.setText("VERIFICATION CODE FROM NETWORKED");
	}

	@FXML
	public void sendMail(ActionEvent event) {
		String to = recipientEmailField.getText();
		String object = objectTextField.getText();
		String verificationCode = VerificationCodeGenerator.generateRandomString(); // Generate verification code using Utils class
		String context = "Hello,\n\n"
				+ "We've received a request to reset your password. Your verification code is: " + verificationCode + "\n\n"
				+ "Please use the following code to proceed with the password reset process. This code is valid for a limited time (30 min at Max).\n\n"
				+ "If you have further questions you can contact us via email.\n\n"
				+ "Best regards,\n"
				+ "Networked";

		// Sender's email and password
		final String username = "networkedapplication@gmail.com"; // Enter your email
		final String password = "noooafzaogtieuro"; // Enter your password

		// SMTP server configuration
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.ssl.trust", "*");

		// Create a Session with authentication
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// Create a MimeMessage
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(
					Message.RecipientType.TO,
					InternetAddress.parse(to)
			);
			message.setSubject(object);
			message.setText(context);

			// Send the message
			Transport.send(message);

			System.out.println("Mail sent successfully!");

			// Start a timer for 30 minutes to regenerate verification code
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					// Regenerate verification code
					String newVerificationCode = VerificationCodeGenerator.generateRandomString();
					System.out.println("New verification code generated: " + newVerificationCode);
					// You may want to update the UI with the new code if necessary
				}
			}, 30* 60 * 1000); // 30 minutes in milliseconds

			try {
				App.setRoot("verificationCode"); // Assuming "verificationCode.fxml" is the FXML file for the verification code scene
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (SendFailedException e) {
			// Check if the exception is due to an invalid recipient address for Gmail
			if (e.getMessage().contains("553-5.1.3 The recipient address")) {
				// Display an alert informing the user about the invalid email address
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Invalid Email");
				alert.setHeaderText(null);
				alert.setContentText("The recipient email address is not valid. Please enter a valid Gmail address.");
				alert.showAndWait();
			} else {
				e.printStackTrace();
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}


	public void gotoSignIn(ActionEvent event) throws IOException {
		App.setRoot("SignInPage");
	}

	public void gotoSignUp(ActionEvent event) throws IOException {
		App.setRoot("SignUpScenes/SignUpPageCommon");

	}

	public void goBack(ActionEvent event) throws IOException {
		App.setRoot("LoginPage");

	}
}
