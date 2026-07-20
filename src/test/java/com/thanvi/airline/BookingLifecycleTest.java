package com.thanvi.airline;

import com.thanvi.airline.entity.Booking;
import com.thanvi.airline.entity.BookingStatus;
import com.thanvi.airline.repository.BookingRepository;
import com.thanvi.airline.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingLifecycleTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void shouldCancelBookingWhenRequested() {
        Booking booking = new Booking();
        booking.setBookingId(7L);
        booking.setStatus(BookingStatus.CONFIRMED);

        when(bookingRepository.findById(7L)).thenReturn(java.util.Optional.of(booking));
        when(bookingRepository.save(org.mockito.ArgumentMatchers.any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Booking cancelled = reservationService.cancelBooking(7L);

        assertEquals(BookingStatus.CANCELLED, cancelled.getStatus());
    }
}
