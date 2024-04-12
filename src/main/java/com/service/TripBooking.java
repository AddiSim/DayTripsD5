package com.service;

import com.data.BookingTable;
import com.data.TripTable;
import com.model.Booking;
import com.model.Trip;
import com.model.User;

import java.time.LocalDate;
import java.util.List;

public class TripBooking implements Service {
    private String serviceName;
    private Double servicePrice;
    private String serviceDescription;
    private BookingTable bookingTable;
    private TripTable tripTable;

    public TripBooking(String serviceName, Double servicePrice, String serviceDescription) throws Exception {
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
        this.serviceDescription = serviceDescription;
        this.bookingTable = new BookingTable();
        this.tripTable = new TripTable();
    }

    public Trip[] searchByName(String name) throws Exception {
        List<Trip> trips = tripTable.searchTripsByName(name);
        return trips.toArray(new Trip[0]);
    }

    public Trip[] searchByLocation(String location) throws Exception {
        List<Trip> trips = tripTable.searchTripsByLocation(location);
        return trips.toArray(new Trip[0]);
    }

    public Booking createBooking(Trip trip, User user, LocalDate Date) throws Exception {
        String bookingId = "BOOKING" + System.currentTimeMillis();
        Booking booking = new Booking(bookingId, user.getId(), trip.getTripID(), Date);
        bookingTable.save(booking);
        return booking;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Double getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(Double servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }
}
