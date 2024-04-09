package com.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUp implements Initializable {
    public ImageView closeWindow;
    public TextField Customer_fName;
    public TextField Customer_sName;
    public TextField CustomerPassword;


    public void BackToUserLogin(ActionEvent actionEvent) throws IOException {
        CommonTask.pageNavigation("/DayTripsD5/Login.fxml", Main.stage,this.getClass(),"Login", 600, 400);
    }

    public void UserSignUp(ActionEvent event) throws IOException {
        CommonTask.pageNavigation("/DayTripsD5/Login.fxml", Main.stage,this.getClass(),"Sign Up", 600, 400);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        closeWindow.setOnMouseClicked(event -> {
            System.exit(0);
        });
    }
}
