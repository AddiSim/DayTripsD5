package com.controller;

import com.data.BookingTable;
import com.model.Booking;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.time.LocalDate;

public class BookingController {
    @FXML
    private TextField bookingIdField;
    @FXML
    private TextField userIdField;
    @FXML
    private TextField tripIdField;
    @FXML
    private DatePicker tripDateField;

    private BookingTable bookingTable;

    public BookingController() {
        try {
            this.bookingTable = new BookingTable();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an alert dialog)
        }
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

}
