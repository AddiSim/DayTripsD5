package com.service;

import com.data.TripTable;
import com.data.UserTable;
import com.model.Trip;
import com.model.User;
import com.model.Booking;

import java.time.LocalDate;

public class TestTripBooking {
    private static UserTable userTable;
    private static TripTable tripTable;
    public static void main(String[] args) {
        try {
            userTable = new UserTable();
            tripTable = new TripTable();

            TripBooking tripBooking = new TripBooking("Trip Booking", 100.0, "Book your trips here!");

            // Test searchByName method
            Trip[] tripsByName = tripBooking.searchByName("Trip Name");
            for (Trip trip : tripsByName) {
                System.out.println(trip.getTripName());
            }


            Trip[] tripsByLocation = tripBooking.searchByLocation("Location");
            for (Trip trip : tripsByLocation) {
                System.out.println(trip.getLocation());
            }


            User user = new User("2", "password", "Arnar", "Simonsen");

            userTable.save(user);

            Trip trip = new Trip("3", LocalDate.parse("2024-04-04"), "Trip1", "reykjavik", 599);

            tripTable.createTrip(trip);

            LocalDate date = LocalDate.now();
            Booking booking = tripBooking.createBooking(trip, user, date);
            System.out.println(booking.getBookingID());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
