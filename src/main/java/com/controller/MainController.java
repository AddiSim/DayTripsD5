package com.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public ImageView closeWindow;
    public Button customerLoginBtn;
    public Button managerLoginBtn;

    public void Manager_Login(ActionEvent event) throws IOException {
        CommonTask.pageNavigation("/DayTripsD5/AdminTripView.fxml",Main.stage,this.getClass(),"Manager Login", 600, 400);
    }
    public void Customer_Login(ActionEvent event) throws IOException {
        CommonTask.pageNavigation("/DayTripsD5/login.fxml",Main.stage,this.getClass(),"Customer Login", 600, 400);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        closeWindow.setOnMouseClicked(event -> {
            System.exit(0);
        });

    }



}
