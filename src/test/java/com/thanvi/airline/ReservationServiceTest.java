package com.thanvi.airline;

import com.thanvi.airline.entity.*;
import com.thanvi.airline.repository.*;
import com.thanvi.airline.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void shouldRejectReservationWhenFlightIsFull() {
        Flight flight = new Flight();
        flight.setFlightId(1L);
        flight.setSeatCapacity(1);

        Seat seat = new Seat();
        seat.setSeatNumber("1A");
        seat.setAvailable(true);

        Booking booking = new Booking();
        booking.setBookingId(10L);

        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        when(seatRepository.findByFlightIdAndSeatNumber(1L, "1A")).thenReturn(Optional.of(seat));
        when(bookingRepository.countByFlightIdAndStatus(1L, BookingStatus.CONFIRMED)).thenReturn(1L);

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> reservationService.createReservation(1L, "1A", "Alice", "Smith"));

        assertEquals("No available seats for this flight.", ex.getMessage());
    }

    @Test
    void shouldCreateReservationWhenSeatAvailable() {
        Flight flight = new Flight();
        flight.setFlightId(2L);
        flight.setSeatCapacity(5);
        flight.setDepartureTime(LocalDateTime.now().plusDays(1));
        flight.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(2));
        flight.setFlightNumber("TA204");

        Seat seat = new Seat();
        seat.setSeatNumber("2A");
        seat.setAvailable(true);

        when(flightRepository.findById(2L)).thenReturn(Optional.of(flight));
        when(seatRepository.findByFlightIdAndSeatNumber(2L, "2A")).thenReturn(Optional.of(seat));
        when(bookingRepository.countByFlightIdAndStatus(2L, BookingStatus.CONFIRMED)).thenReturn(0L);
        when(bookingRepository.save(org.mockito.ArgumentMatchers.any(Booking.class))).thenAnswer(invocation -> {
            Booking booking = invocation.getArgument(0);
            booking.setBookingId(99L);
            booking.setConfirmationNumber("ARS123");
            return booking;
        });

        Booking booking = reservationService.createReservation(2L, "2A", "Bob", "Jones");

        assertEquals("ARS123", booking.getConfirmationNumber());
        assertEquals(BookingStatus.CONFIRMED, booking.getStatus());
    }
}
