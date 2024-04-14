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

            assert tripBooking.getServiceName().equals("Trip Booking");
            assert tripBooking.getServicePrice().equals(100.0);
            assert tripBooking.getServiceDescription().equals("Book your trips here!");

            Trip[] tripsByName = tripBooking.searchByName("Trip Name");
            for (Trip trip : tripsByName) {
                assert trip.getTripName().equals("Trip Name");
                System.out.println(trip.getTripName());
            }

            Trip[] tripsByLocation = tripBooking.searchByLocation("Location");
            for (Trip trip : tripsByLocation) {
                assert trip.getLocation().equals("Location");
                System.out.println(trip.getLocation());
            }

            User user = new User("password", "Arnar", "Simonsen");

            userTable.save(user);

            Trip trip = new Trip(LocalDate.parse("2024-04-04"), "Trip1", "reykjavik", 599);

            tripTable.createTrip(trip);

            LocalDate date = LocalDate.now();
            Booking booking = tripBooking.createBooking(trip, user, date);
            System.out.println(booking.getBookingID());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
