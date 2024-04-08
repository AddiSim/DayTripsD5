package com.controller;

import com.data.UserTable;
import com.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class UserController {
    @FXML
    private TextField userIdField; // User ID input field in the FXML
    @FXML
    private PasswordField passwordField; // Password input field in the FXML
    @FXML
    private Text actionTarget; // Text element for displaying feedback messages

    private UserTable userTable; // For database operations
    private User user; // The currently logged-in user

    public UserController() {
        // It's usually not possible to handle exceptions like this in the constructor because of FXML loading,
        // but let's ensure UserTable is initialized properly elsewhere (e.g., in an initialize method)
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialization logic here
        try {
            userTable = new UserTable();
        } catch (Exception e) {
            e.printStackTrace();
            actionTarget.setText("Failed to initialize database connection.");
        }
    }

    /**
     * Called when the user presses the Log In button.
     */
    @FXML
    private void logIn() {
        String id = userIdField.getText();
        String password = passwordField.getText();

        try {
            if (userTable.isValid(id, password)) {
                this.user = userTable.findById(id);
                actionTarget.setText("Login successful.");
            } else {
                actionTarget.setText("Login failed. Please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            actionTarget.setText("An error occurred. Please try again later.");
        }
    }

    /**
     * Logs out the currently logged-in user.
     */
    public void logOut() {
        this.user = null;
        actionTarget.setText("You have been logged out.");
        // Optional: Return to the login screen or clear sensitive UI elements.
    }

    /**
     * Creates a new user account with the provided details.
     * This could be called from a registration form in your application.
     */
    public boolean createUser(String id, String password, String firstName, String lastName) {
        try {
            if (userTable.findById(id) == null) {
                userTable.save(id, password, firstName, lastName);
                actionTarget.setText("User created successfully.");
                return true;
            } else {
                actionTarget.setText("User ID already exists.");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            actionTarget.setText("Failed to create user.");
            return false;
        }
    }

    public boolean delUser(String id, String password) {
        try {
            if (user != null && userTable.isValid(id, password)) {
                userTable.deleteUser(id);
                this.user = null;
                actionTarget.setText("User successfully deleted.");
                return true;
            } else {
                actionTarget.setText("User deletion failed. Incorrect ID or password.");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            actionTarget.setText("An error occurred. Please try again later.");
            return false;
        }
    }

    /**
     * Getter for the currently logged-in user.
     * This could be used to display user information in the UI.
     */
    public User getUser() {
        return this.user;
    }
}
