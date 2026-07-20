package com.thanvi.airline.repository;

import com.thanvi.airline.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> findByFlightIdAndSeatNumber(Long flightId, String seatNumber);
}
