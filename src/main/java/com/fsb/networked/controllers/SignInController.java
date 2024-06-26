package com.fsb.networked.controllers;

import com.fsb.networked.App;
import com.fsb.networked.service.EntrepriseService;
import com.fsb.networked.service.IndividualService;
import com.fsb.networked.utils.Alerts;
import com.fsb.networked.utils.Regexes;
import com.fsb.networked.utils.SessionManager;
import com.fsb.networked.utils.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class SignInController {
	@FXML
	private TextField invisibleTextField;

	@FXML
	private Button btnTogglePasswordVisible;


	@FXML
	private TextField emailAddressField;
	
	@FXML
	private PasswordField passwordField;

	@FXML
	private Button btnForgotPassword;
	
	@FXML
	private Button btnSignIn;
	
	@FXML
	private Button btnSignUp;
	
	@FXML
    private void gotoSignUp() throws IOException
	{
        App.setRoot("SignUpScenes/SignUpPageCommon");
    }
	
	@FXML
	private void resetPassword() throws IOException
	{
		App.setRoot("ForgotPassword");
	}

	@FXML
	private void doSignIn() throws IOException, SQLException {
		IndividualService individualService = new IndividualService();
		EntrepriseService entrepriseService = new EntrepriseService();

		// Validate if email and password fields are filled
		if (validateBasicInfo()) {
			// Check if individual exists
			int individualResult = individualService.individualExists(emailAddressField.getText(), passwordField.getText());
			if (individualResult != -1) {
				// Individual exists, redirect to individual home page
				SessionManager.setID(individualResult);
				SessionManager.setSessionIDIndividual();
				App.setRoot("HomePageScenes/HomePageIndividual");
				System.out.println("Session ID indiv after setting :" + SessionManager.getSessionIDEntreprise());
				System.out.println("Signed in as individual");
				return;
			} else {
				// Individual does not exist, check if enterprise exists
				int enterpriseResult = entrepriseService.entrepriseExists(emailAddressField.getText(), passwordField.getText());
				if (enterpriseResult != -1) {
					// Enterprise exists, redirect to enterprise home page
					SessionManager.setID(enterpriseResult);
					SessionManager.setSessionIDEntreprise();
					App.setRoot("HomePageScenes/HomePageEntreprise");
					System.out.println("Session ID entreprise after setting :" + SessionManager.getSessionIDEntreprise());
					System.out.println("Signed in as entreprise");
					return;
				}
			}
		}
		// No user found or invalid input, show alert

	}

	private boolean validateBasicInfo() {
		boolean isValid = true;
		isValid &= Validator.validateField(emailAddressField, Regexes.EMAIL_REGEX,Alerts.AlertEmailField());
		isValid &= Validator.validateField(passwordField, Regexes.PASSWORD_REGEX,Alerts.AlertPasswordField());
		return isValid;
	}

	@FXML
	private void togglePasswordVisible() {
		// Get the current visibility state of the password field
		boolean isVisible = passwordField.isVisible();

		// Toggle the visibility state of both password fields
		passwordField.setVisible(!isVisible);
		invisibleTextField.setVisible(isVisible);

		// If switching to visible, update the invisible text field with the password text
		if (isVisible) {
			invisibleTextField.setText(passwordField.getText());
			// Add listener to sync changes made in visible field to invisible field
			passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
				invisibleTextField.setText(newValue);
			});
		} else {
			// If switching to invisible, update the password field with the text from the invisible text field
			passwordField.setText(invisibleTextField.getText());
			// Add listener to sync changes made in invisible field to visible field
			invisibleTextField.textProperty().addListener((observable, oldValue, newValue) -> {
				passwordField.setText(newValue);
			});
		}

		// Update the text of the button based on the new visibility state
		String buttonText = isVisible ? "Hide" : "Show";
		btnTogglePasswordVisible.setText(buttonText);
	}



}
