package com.service;

import com.data.BookingTable;
import com.data.TripTable;
import com.model.Booking;
import com.model.Trip;

import java.time.LocalDate;
import java.util.List;

public class TripBookingService {
    private BookingTable bookingTable;
    private TripTable tripTable;

    public TripBookingService() throws Exception {
        this.bookingTable = new BookingTable();
        this.tripTable = new TripTable();
    }

    public List<Trip> getAllTrips() throws Exception {
        return tripTable.getAllTrips();
    }

    public List<Trip> searchTripsByLocation(String location) throws Exception {
        return tripTable.searchTripsByLocation(location);
    }

    public void bookTrip(String userId, String tripId, LocalDate tripDate) throws Exception {
        String bookingId = "BOOKING" + System.currentTimeMillis();
        Booking booking = new Booking(bookingId, userId, tripId, tripDate);
        bookingTable.save(booking);
    }
}
