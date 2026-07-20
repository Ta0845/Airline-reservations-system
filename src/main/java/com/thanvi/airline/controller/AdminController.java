package com.thanvi.airline.controller;

import com.thanvi.airline.entity.Aircraft;
import com.thanvi.airline.entity.Airport;
import com.thanvi.airline.entity.Flight;
import com.thanvi.airline.repository.AircraftRepository;
import com.thanvi.airline.repository.AirportRepository;
import com.thanvi.airline.repository.FlightRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AirportRepository airportRepository;
    private final AircraftRepository aircraftRepository;
    private final FlightRepository flightRepository;

    public AdminController(AirportRepository airportRepository, AircraftRepository aircraftRepository, FlightRepository flightRepository) {
        this.airportRepository = airportRepository;
        this.aircraftRepository = aircraftRepository;
        this.flightRepository = flightRepository;
    }

    @PostMapping("/airports")
    public Airport createAirport(@RequestBody Airport airport) {
        return airportRepository.save(airport);
    }

    @GetMapping("/airports")
    public List<Airport> getAirports() {
        return airportRepository.findAll();
    }

    @PostMapping("/aircraft")
    public Aircraft createAircraft(@RequestBody Aircraft aircraft) {
        return aircraftRepository.save(aircraft);
    }

    @PostMapping("/flights")
    public Flight createFlight(@RequestBody Flight flight) {
        return flightRepository.save(flight);
    }

    @PutMapping("/flights/{id}")
    public Flight updateFlight(@PathVariable Long id, @RequestBody Flight flight) {
        flight.setFlightId(id);
        return flightRepository.save(flight);
    }

    @DeleteMapping("/flights/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        flightRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
