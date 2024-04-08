package com.controller;

import com.data.TripTable;
import com.model.Trip;

import java.time.LocalDate;

public class TripController {
    Trip trip;
    TripTable tripTable;

    public void createTrip(String tripID, String location, String date, int price) {
        try {
            trip = new Trip(tripID, location, LocalDate.parse(date), price);
            tripTable.save(trip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTrip(String tripID) {
        try {
            tripTable.deleteTrip(tripID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Trip findTrip(String tripID) {
        try {
            return tripTable.findById(tripID);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateTrip(String tripID, String location, String date, int price) {
        try {
            trip = new Trip(tripID, location, LocalDate.parse(date), price);
            tripTable.update(trip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
