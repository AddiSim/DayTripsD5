package com.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.time.LocalDate;

public class Booking {
    private SimpleStringProperty bookingID = new SimpleStringProperty(this, "bookingID");
    private SimpleStringProperty userID = new SimpleStringProperty(this, "userID");
    private SimpleStringProperty tripID = new SimpleStringProperty(this, "tripID");
    private ObjectProperty<LocalDate> tripDate = new SimpleObjectProperty<>(this, "tripDate");

    public Booking(String bookingID, String userID, String tripID, LocalDate tripDate) {
        this.bookingID.set(bookingID);
        this.userID.set(userID);
        this.tripID.set(tripID);
        this.tripDate.set(tripDate);
    }

    // Getters and Setters for JavaFX properties
    public String getBookingID() { return bookingID.get(); }
    public void setBookingID(String bookingID) { this.bookingID.set(bookingID); }
    public SimpleStringProperty bookingIDProperty() { return bookingID; }

    public String getUserID() {
        return userID.get();
    }
    public void setUserID(String userID) { this.userID.set(userID); }
    public SimpleStringProperty userIDProperty() { return userID; }

    public String getTripID() { return tripID.get(); }
    public void setTripID(String tripID) { this.tripID.set(tripID); }
    public SimpleStringProperty tripIDProperty() { return tripID; }

    public LocalDate getTripDate() { return tripDate.get(); }
    public void setTripDate(LocalDate tripDate) { this.tripDate.set(tripDate); }
    public ObjectProperty<LocalDate> tripDateProperty() { return tripDate; }
}
