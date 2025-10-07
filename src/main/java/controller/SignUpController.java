package controller;

import dao.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;

public class SignUpController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    private UserDao userDao = new UserDao();

    @FXML
    private void handleSignUp(ActionEvent event) throws IOException {
        // Modified: Trim input to remove leading/trailing spaces
        String username = usernameField.getText().trim(); // ← modified
        String password = passwordField.getText().trim(); // ← modified
        String confirmPassword = confirmPasswordField.getText().trim(); // ← modified

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please fill all fields!");
            return;
        }

        // Added: Check if password and confirmPassword match
        if (!password.equals(confirmPassword)) { // ← added
            showAlert("Error", "Passwords do not match!"); // ← added
            return; // ← added
        }

        // Reverted: Use login instead of findByUsername to avoid altering UserDao
        User existingUser = userDao.login(username, password); // ← reverted
        if (existingUser != null) {
            showAlert("Error", "Username already exists!");
            return;
        }

        User newUser = new User(username, password, confirmPassword);
        userDao.register(newUser);

        showAlert("Success", "Account created successfully!");
        handleLoginRedirect(event);
    }

    @FXML
    private void handleLoginRedirect(ActionEvent event) throws IOException {
        Parent loginRoot = FXMLLoader.load(getClass().getResource("/Login.fxml"));
        Scene loginScene = new Scene(loginRoot);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(loginScene);
        window.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
