package com.thanvi.airline.controller;

import com.thanvi.airline.entity.Booking;
import com.thanvi.airline.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestParam Long flightId,
                                                 @RequestParam String seatNumber,
                                                 @RequestParam String firstName,
                                                 @RequestParam String lastName) {
        Booking booking = reservationService.createReservation(flightId, seatNumber, firstName, lastName);
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

    @GetMapping("/my-bookings")
    public ResponseEntity<java.util.List<Booking>> getMyBookings(@RequestParam String username) {
        return ResponseEntity.ok(reservationService.getBookingsForUser(username));
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Booking> cancelBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(reservationService.cancelBooking(bookingId));
    }
}
