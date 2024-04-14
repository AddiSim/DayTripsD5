package com.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;



import java.io.IOException;


public class MainController  {

    public Button customerLoginBtn;
    public Button managerLoginBtn;

    public void Manager_Login(ActionEvent event) throws IOException {
        CommonTask.pageNavigation("/DayTripsD5/AdminTripView.fxml",Main.stage,this.getClass(),"Manager Login", 600, 400);
    }
    public void Customer_Login(ActionEvent event) throws IOException {
        CommonTask.pageNavigation("/DayTripsD5/login.fxml",Main.stage,this.getClass(),"Customer Login", 600, 400);
    }



}
