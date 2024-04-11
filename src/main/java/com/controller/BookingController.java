package com.controller;

import com.data.BookingTable;
import com.data.TripTable;
import com.model.Booking;
import com.model.Trip;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.List;

public class BookingController {
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Trip> tripTableView;
    @FXML
    private javafx.scene.control.TableColumn<Trip, String> idColumn;
    @FXML
    private javafx.scene.control.TableColumn<Trip, String> nameColumn;
    @FXML
    private javafx.scene.control.TableColumn<Trip, String> locationColumn;
    @FXML
    private javafx.scene.control.TableColumn<Trip, LocalDate> dateColumn;
    @FXML
    private javafx.scene.control.TableColumn<Trip, Integer> priceColumn;
    @FXML
    private Text feedbackText;

    private BookingTable bookingTable;
    private TripTable tripTable;
    private UserController userController;

    public BookingController() {
        try {
            this.bookingTable = new BookingTable();
            this.tripTable = new TripTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        try {
            idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTripID()));
            nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTripName()));
            locationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocation()));
            dateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTripDate()));
            priceColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPrice()).asObject());

            List<Trip> trips = tripTable.getAllTrips();
            ObservableList<Trip> tripList = FXCollections.observableArrayList(trips);
            tripTableView.setItems(tripList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleSearch() {
        try {
            String tripName = searchField.getText();
            List<Trip> trips = tripTable.searchTripsByLocation(tripName);
            if (trips.isEmpty()) {
                feedbackText.setText("No trips found for location: " + tripName);
            } else {
                feedbackText.setText(trips.size() + " trips found for location: " + tripName);
                ObservableList<Trip> tripList = FXCollections.observableArrayList(trips);
                tripTableView.setItems(tripList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            feedbackText.setText("Error searching for trips.");
        }
    }

    @FXML
    protected void handleBookTrip(ActionEvent actionEvent) {
        try {
            Trip selectedTrip = tripTableView.getSelectionModel().getSelectedItem();
            if (selectedTrip == null) {
                feedbackText.setText("Please select a trip to book.");
                return;
            }
            String userId = userController.getLoggedInUserId();
            String tripId = selectedTrip.getTripID();
            LocalDate tripDate = LocalDate.now();
            String bookingId = "BOOKING" + System.currentTimeMillis();
            Booking booking = new Booking(bookingId, userId, tripId, tripDate);
            bookingTable.save(booking);

            feedbackText.setText("Booking successful for Trip ID: " + tripId);
        } catch (Exception e) {
            e.printStackTrace();
            feedbackText.setText("Booking failed: " + e.getMessage());
        }
    }
}
