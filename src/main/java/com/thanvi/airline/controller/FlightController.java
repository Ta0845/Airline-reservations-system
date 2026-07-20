package com.thanvi.airline.controller;

import com.thanvi.airline.entity.Flight;
import com.thanvi.airline.repository.FlightRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {
    private final FlightRepository flightRepository;

    public FlightController(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @GetMapping("/search")
    public List<Flight> searchFlights(@RequestParam String origin,
                                      @RequestParam String destination,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate) {
        LocalDateTime start = departureDate.atStartOfDay();
        LocalDateTime end = departureDate.atTime(LocalTime.MAX);
        return flightRepository.findAll().stream()
                .filter(f -> f.getOrigin().equalsIgnoreCase(origin))
                .filter(f -> f.getDestination().equalsIgnoreCase(destination))
                .filter(f -> !f.getDepartureTime().isBefore(start) && !f.getDepartureTime().isAfter(end))
                .toList();
    }
}
