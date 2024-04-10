package com.model;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Trip {
    private SimpleStringProperty tripID = new SimpleStringProperty(this, "tripID");
    private SimpleStringProperty location = new SimpleStringProperty(this, "location");
    private ObjectProperty<LocalDate> tripDate = new SimpleObjectProperty<>(this, "tripDate");
    private SimpleIntegerProperty price = new SimpleIntegerProperty(this, "price");

    public Trip(String tripID, LocalDate tripDate, String location, int price) {
        this.tripID.set(tripID);
        this.location.set(location);
        this.tripDate.set(tripDate);
        this.price.set(price);
    }

    // Getters and Setters for JavaFX properties
    public String getTripID() { return tripID.get(); }
    public void setTripID(String tripID) { this.tripID.set(tripID); }
    public SimpleStringProperty tripIDProperty() { return tripID; }

    public String getLocation() { return location.get(); }
    public void setLocation(String location) { this.location.set(location); }
    public SimpleStringProperty locationProperty() { return location; }

    public LocalDate getTripDate() { return tripDate.get(); }
    public void setTripDate(LocalDate tripDate) { this.tripDate.set(tripDate); }
    public ObjectProperty<LocalDate> tripDateProperty() { return tripDate; }

    public int getPrice() { return price.get(); }
    public void setPrice(int price) { this.price.set(price); }
    public SimpleIntegerProperty priceProperty() { return price; }
}
