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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.List;

public class BookingController {
    @FXML
    private TextField bookingIdField;
    @FXML
    private TextField userIdField;
    @FXML
    private TextField tripIdField;
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Trip> tripTableView;
    @FXML
    private javafx.scene.control.TableColumn<Trip, String> idColumn;
    @FXML
    private javafx.scene.control.TableColumn<Trip, String> locationColumn;
    @FXML
    private javafx.scene.control.TableColumn<Trip, LocalDate> dateColumn;
    @FXML
    private javafx.scene.control.TableColumn<Trip, Integer> priceColumn;
    @FXML
    private DatePicker tripDateField;
    @FXML
    private Text feedbackText;

    private BookingTable bookingTable;
    private TripTable tripTable;

    public BookingController() {
        try {
            this.bookingTable = new BookingTable();
            this.tripTable = new TripTable();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an alert dialog)
        }
    }

    @FXML
    private void initialize() {
        try {
            // Set cell value factories for the TableView columns
            idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTripID()));
            locationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocation()));
            dateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTripDate()));
            priceColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPrice()).asObject());

            List<Trip> trips = tripTable.getAllTrips();
            ObservableList<Trip> tripList = FXCollections.observableArrayList(trips);
            tripTableView.setItems(tripList);

            tripTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    updateFields(newSelection);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }

    private void updateFields(Trip newSelection) {
        tripIdField.setText(newSelection.getTripID());
    }

    @FXML
    protected void addBooking() {
        try {
            String bookingID = bookingIdField.getText();
            String userID = userIdField.getText();
            String tripID = tripIdField.getText();
            LocalDate tripDate = tripDateField.getValue();

            Booking booking = new Booking(bookingID, userID, tripID, tripDate);
            bookingTable.save(booking);
            // Update UI or show confirmation message
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }

    @FXML
    protected void deleteBooking() {
        try {
            String bookingID = bookingIdField.getText();
            bookingTable.deleteBooking(bookingID);
            // Update UI or show confirmation message
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }

    @FXML
    protected void findBooking() {
        try {
            String bookingID = bookingIdField.getText();
            Booking booking = bookingTable.findById(bookingID);
            if (booking != null) {
                userIdField.setText(booking.getUserID());
                tripIdField.setText(booking.getTripID());
                tripDateField.setValue(booking.getTripDate());
            } else {
                // Show a message that the booking was not found
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }

    @FXML
    protected void clearFields() {
        bookingIdField.clear();
        userIdField.clear();
        tripIdField.clear();
        tripDateField.setValue(null);
    }

    public void close() {
        bookingTable.disCon();
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
                tripTableView.refresh(); // Add this line
            }
        } catch (Exception e) {
            e.printStackTrace();
            feedbackText.setText("Error searching for trips.");
            // Handle the exception (e.g., show an error message)
        }
    }

    @FXML
    protected void handleBookTrip(ActionEvent actionEvent) {
        try {
            String userId = userIdField.getText(); // The ID of the user making the booking
            String tripId = tripIdField.getText(); // The ID of the trip to book
            LocalDate tripDate = tripDateField.getValue(); // The date of the trip

            if (userId.isEmpty() || tripId.isEmpty() || tripDate == null) {
                feedbackText.setText("Please select a trip to book.");
                return;
            }

            // Generate a unique booking ID for simplicity, replace this as needed
            String bookingId = "BOOKING" + System.currentTimeMillis();

            Booking booking = new Booking(bookingId, userId, tripId, tripDate);
            bookingTable.save(booking);

            feedbackText.setText("Booking successful for Trip ID: " + tripId);
            clearFields(); // Optionally clear fields after booking
        } catch (Exception e) {
            e.printStackTrace();
            feedbackText.setText("Booking failed: " + e.getMessage());
        }
    }
}
