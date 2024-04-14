package com.controller;

import com.data.UserTable;
import com.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;

public class UserController {
    public ImageView closeWindow;
    @FXML
    private TextField userIdField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Text actionTarget;

    private UserTable userTable;
    private User user;

    private static Integer loggedInUserId;

    public UserController() {
        try {
            userTable = new UserTable();
        } catch (Exception e) {
            e.printStackTrace();
            actionTarget.setText("Failed to initialize database connection.");
        }
    }

    @FXML
    private void initialize() {
        try {
            userTable = new UserTable();
        } catch (Exception e) {
            e.printStackTrace();
            actionTarget.setText("Failed to initialize database connection.");
        }
    }

    @FXML
    private void logIn() throws IOException {
        int id = Integer.parseInt(userIdField.getText());
        String password = passwordField.getText();

        try {
            if (userTable.isValid(id, password)) {
                this.user = userTable.findById(id);
                loggedInUserId = id;
                actionTarget.setText("Login successful.");

                CommonTask.pageNavigation("/DayTripsD5/UserBookingView.fxml", Main.stage, this.getClass(), "User Booking", 600, 400);
            } else {
                actionTarget.setText("Login failed. Please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            actionTarget.setText("An error occurred. Please try again later.");
        }
    }

    public void logOut() {
        this.user = null;
        actionTarget.setText("You have been logged out.");
    }

    public boolean createUser(User user) {
        try {
            if (userTable.findById(user.getId()) == null) {
                userTable.save(user);
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

    public boolean delUser(Number id, String password) {
        try {
            if (user != null && userTable.isValid(id, password)) {
                userTable.deleteUser(id.toString());
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

    public User getUser() {
        return this.user;
    }

    public static Integer getLoggedInUserId() {
        return loggedInUserId;
    }

    @FXML
    public void UserSignUp(ActionEvent mouseEvent) throws IOException {
        CommonTask.pageNavigation("/DayTripsD5/Sample.fxml", Main.stage ,this.getClass(),"User Signup", 600, 400);
    }

}
