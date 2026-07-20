package com.thanvi.airline.repository;

import com.thanvi.airline.entity.Booking;
import com.thanvi.airline.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    long countByFlightIdAndStatus(Long flightId, BookingStatus status);
}
