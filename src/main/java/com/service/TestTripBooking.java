package com.service;

import com.data.TripTable;
import com.data.UserTable;
import com.model.Trip;
import com.model.User;
import com.model.Booking;

import java.time.LocalDate;
import java.util.List;

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

            String[] locations = {"London", "New York", "Reykjavík", "Akureyri"};
            for (String location : locations) {
                for (int i = 1; i <= 10; i++) {
                    Trip trip = new Trip(LocalDate.now(), location + " Trip " + i, location, 100 * i);
                    tripTable.createTrip(trip);
                }
            }

            for (String location : locations) {
                for (int i = 1; i <= 10; i++) {
                    Trip[] trips = tripBooking.searchByName(location + " Trip " + i);
                    assert trips.length == 1;
                    assert trips[0].getTripName().equals(location + " Trip " + i);
                }
            }

            for (String location : locations) {
                Trip[] tripsByLocation = tripBooking.searchByLocation(location);
                assert tripsByLocation.length == 10;
                for (Trip trip : tripsByLocation) {
                    assert trip.getLocation().equals(location);
                }
            }

            User user = new User("password", "Arnar", "Simonsen");
            userTable.save(user);

            for (String location : locations) {
                for (int i = 1; i <= 10; i++) {
                    List<Trip> trips = tripTable.searchTripsByName(location + " Trip " + i);
                    if (!trips.isEmpty()) {
                        Trip trip = trips.get(0);
                        // Save the trip to the database before creating a booking
                        tripTable.createTrip(trip);
                        Booking booking = tripBooking.createBooking(trip, user, LocalDate.now());
                        assert booking != null;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
