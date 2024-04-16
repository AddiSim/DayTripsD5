package DayTrips.controller;

import DayTrips.data.TripTable;
import DayTrips.model.Trip;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.time.LocalDate;

public class TripController {
    private TripTable tripTable;

    @FXML
    private TextField tripIdField;
    @FXML
    private TextField tripNameField;
    @FXML
    private TextField locationField;
    @FXML
    private TextField dateField;
    @FXML
    private TextField priceField;
    @FXML
    private Text feedbackText;

    @FXML
    private void initialize() throws Exception {
        tripTable = new TripTable();
    }

    @FXML
    private void handleCreateTrip() {
        String tripName = tripNameField.getText();
        String location = locationField.getText();
        LocalDate date = LocalDate.parse(dateField.getText());
        int price = Integer.parseInt(priceField.getText());

        createTrip(date, tripName, location, price);
    }

    @FXML
    private void handleDeleteTrip() {
        String tripID = tripIdField.getText();
        deleteTrip(tripID);
    }

    @FXML
    private void handleUpdateTrip() {
        LocalDate date = LocalDate.parse(dateField.getText());
        String tripName = tripNameField.getText();
        String location = locationField.getText();
        int price = Integer.parseInt(priceField.getText());

        updateTrip(date, tripName, location, price);
    }

    private void createTrip(LocalDate date, String tripName, String location, int price) {
        try {
            Trip trip = new Trip(date, tripName, location, price);
            tripTable.createTrip(trip);
            feedbackText.setText("Trip created successfully.");
        } catch (Exception e) {
            feedbackText.setText("Error creating trip.");
            e.printStackTrace();
        }
    }

    private void deleteTrip(String tripID) {
        try {
            tripTable.deleteTrip(tripID);
            feedbackText.setText("Trip deleted successfully.");
        } catch (Exception e) {
            feedbackText.setText("Error deleting trip.");
            e.printStackTrace();
        }
    }

    private Trip findTrip(String tripID) {
        try {
            return tripTable.findById(tripID);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void updateTrip(LocalDate date, String tripName, String location, int price) {
        try {
            Trip trip = new Trip(date, tripName, location, price);
            tripTable.updateTrip(trip);
            feedbackText.setText("Trip updated successfully.");
        } catch (Exception e) {
            feedbackText.setText("Error updating trip.");
            e.printStackTrace();
        }
    }
}
