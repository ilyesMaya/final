package com.fsb.networked.controllers.ChildWindowControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class ChangePasswordChildWindowController {
    @FXML
    PasswordField oldPasswordField;
    @FXML
    PasswordField newPasswordField;
    @FXML
    PasswordField confirmNewPasswordField;
    @FXML
    Label statusLabel;
    @FXML
    Button showHideBtn;
    @FXML
    Button submitBtn;

    private boolean passwordVisible = false; // Declare passwordVisible variable

    @FXML
    private void showHidePassword(ActionEvent event) {
        passwordVisible = !passwordVisible;
        String visibilityIcon = passwordVisible ? "eye.png" : "eye-off.png";

        // Set visibility of password fields
        oldPasswordField.setManaged(passwordVisible);
        oldPasswordField.setVisible(passwordVisible);
        newPasswordField.setManaged(passwordVisible);
        newPasswordField.setVisible(passwordVisible);
        confirmNewPasswordField.setManaged(passwordVisible);
        confirmNewPasswordField.setVisible(passwordVisible);

        // Change button text and icon
        String buttonText = passwordVisible ? "Hide" : "Show";
        showHideBtn.setText(buttonText);
    }

    @FXML
    private void submitNewPassword()
    {

    }
}
