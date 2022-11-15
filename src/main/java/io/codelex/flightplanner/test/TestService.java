package io.codelex.flightplanner.test;

import io.codelex.flightplanner.flights.FlightRepository;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    FlightRepository flightRepository;

    public TestService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public void clear() {
        flightRepository.clear();
    }
}
