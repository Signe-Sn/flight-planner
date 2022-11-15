package io.codelex.flightplanner.admin;

import io.codelex.flightplanner.flights.Flight;
import io.codelex.flightplanner.flights.FlightRepository;
import io.codelex.flightplanner.flights.FlightService;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    FlightService flightService;
    FlightRepository flightRepository;

    public AdminService(FlightService flightService, FlightRepository flightRepository) {
        this.flightService = flightService;
        this.flightRepository = flightRepository;
    }

    public Flight addFlight(AddFlightRequest flightRequest) {
        Flight flight = new Flight(flightRequest);
        return flightService.addFlight(flight);
    }

    public void deleteFlight(long id) {
        flightRepository.deleteFlight(id);
    }

    public Flight fetchFlight(long id) {
        return flightService.fetchFlight(id);
    }

}
