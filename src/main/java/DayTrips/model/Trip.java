package DayTrips.model;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Trip {
    private SimpleIntegerProperty tripID = new SimpleIntegerProperty(this, "tripID");
    private SimpleStringProperty location = new SimpleStringProperty(this, "location");
    private SimpleStringProperty tripName = new SimpleStringProperty(this, "tripName");
    private ObjectProperty<LocalDate> tripDate = new SimpleObjectProperty<>(this, "tripDate");
    private SimpleIntegerProperty price = new SimpleIntegerProperty(this, "price");

    public Trip(LocalDate tripDate, String tripName, String location, int price) {
        this.location.set(location);
        this.tripDate.set(tripDate);
        this.tripName.set(tripName);
        this.price.set(price);
    }

    public int getTripID() {
        return tripID.get();
    }

    public void setTripID(int tripID) {
        this.tripID.set(tripID);
    }

    public SimpleIntegerProperty tripIDProperty() {
        return tripID;
    }

    public String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public SimpleStringProperty locationProperty() {
        return location;
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

    public int getPrice() {
        return price.get();
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public SimpleIntegerProperty priceProperty() {
        return price;
    }

    public String getTripName() {
        return this.tripName.get();
    }


}
