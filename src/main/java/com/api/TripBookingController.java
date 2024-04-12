package com.api;

import com.service.TripBookingService;
import com.model.Trip;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/api/trips")
public class TripBookingController {

    private final TripBookingService tripBookingService;

    public TripBookingController(TripBookingService tripBookingService) {
        this.tripBookingService = tripBookingService;
    }

    @GetMapping
    public List<Trip> getAllTrips() throws Exception {
        return tripBookingService.getAllTrips();
    }

    @GetMapping("/search")
    public List<Trip> searchTripsByLocation(@RequestParam String location) throws Exception {
        return tripBookingService.searchTripsByLocation(location);
    }

    @PostMapping("/book")
    public void bookTrip(@RequestParam String userId, @RequestParam String tripId, @RequestParam LocalDate tripDate) throws Exception {
        tripBookingService.bookTrip(userId, tripId, tripDate);
    }
}
