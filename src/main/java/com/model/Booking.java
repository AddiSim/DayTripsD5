package com.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Booking {
    private SimpleStringProperty bookingID;
    private IntegerProperty userID;
    private IntegerProperty tripID;
    private SimpleObjectProperty<LocalDate> tripDate;

    public Booking(String bookingID, int userID, int tripID, LocalDate tripDate) {
        this.bookingID = new SimpleStringProperty(bookingID);
        this.userID = new SimpleIntegerProperty(userID);
        this.tripID = new SimpleIntegerProperty(tripID);
        this.tripDate = new SimpleObjectProperty<>(tripDate);
    }

    public int getUserID() {
        return userID.get();
    }

    public void setUserID(int userID) {
        this.userID.set(userID);
    }

    public IntegerProperty userIDProperty() {
        return userID;
    }

    public String getBookingID() {
        return bookingID.get();
    }

    public void setBookingID(String bookingID) {
        this.bookingID.set(bookingID);
    }

    public SimpleStringProperty bookingIDProperty() {
        return bookingID;
    }

    public int getTripID() {
        return tripID.get();
    }

    public void setTripID(int tripID) {
        this.tripID.set(tripID);
    }

    public IntegerProperty tripIDProperty() {
        return tripID;
    }

    public LocalDate getTripDate() {
        return tripDate.get();
    }

    public void setTripDate(LocalDate tripDate) {
        this.tripDate.set(tripDate);
    }

    public ObjectProperty<LocalDate> tripDateProperty() {
        return tripDate;
    }
}
