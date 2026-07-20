package com.thanvi.airline.service;

import com.thanvi.airline.entity.Booking;
import com.thanvi.airline.entity.BookingStatus;
import com.thanvi.airline.entity.Flight;
import com.thanvi.airline.entity.Seat;
import com.thanvi.airline.repository.BookingRepository;
import com.thanvi.airline.repository.FlightRepository;
import com.thanvi.airline.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReservationService {
    private final FlightRepository flightRepository;
    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;

    public ReservationService(FlightRepository flightRepository, BookingRepository bookingRepository, SeatRepository seatRepository) {
        this.flightRepository = flightRepository;
        this.bookingRepository = bookingRepository;
        this.seatRepository = seatRepository;
    }

    public Booking createReservation(Long flightId, String seatNumber, String firstName, String lastName) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new IllegalArgumentException("Flight not found."));

        Seat seat = seatRepository.findByFlightIdAndSeatNumber(flightId, seatNumber)
                .orElseThrow(() -> new IllegalArgumentException("Seat not found."));

        if (!seat.isAvailable()) {
            throw new IllegalStateException("Seat is already booked.");
        }

        long confirmedCount = bookingRepository.countByFlightIdAndStatus(flightId, BookingStatus.CONFIRMED);
        if (confirmedCount >= flight.getSeatCapacity()) {
            throw new IllegalStateException("No available seats for this flight.");
        }

        seat.setAvailable(false);
        seatRepository.save(seat);

        Booking booking = new Booking();
        booking.setFlightId(flightId);
        booking.setSeatNumber(seatNumber);
        booking.setPassengerFirstName(firstName);
        booking.setPassengerLastName(lastName);
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setConfirmationNumber("ARS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        return bookingRepository.save(booking);
    }

    public java.util.List<Booking> getBookingsForUser(String username) {
        return bookingRepository.findAll().stream()
                .filter(booking -> booking.getPassengerFirstName().equalsIgnoreCase(username) || booking.getPassengerLastName().equalsIgnoreCase(username))
                .toList();
    }

    public Booking cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found."));
        booking.setStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }
}
