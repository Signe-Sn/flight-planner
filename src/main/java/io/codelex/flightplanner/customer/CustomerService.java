package io.codelex.flightplanner.customer;

import io.codelex.flightplanner.flights.Airport;
import io.codelex.flightplanner.flights.Flight;
import io.codelex.flightplanner.flights.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CustomerService {

    FlightService flightService;

    public CustomerService(FlightService flightService) {
        this.flightService = flightService;
    }

    public List<Airport> searchAirports(String search) {
        return flightService.searchAirports(search);
    }

    public PageResult<Flight> searchFlights(SearchFlightsRequest searchFlightsRequest) {
        if (isSameAirport(searchFlightsRequest)) {
            return flightService.searchFlights(searchFlightsRequest);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    public Flight findFlightById(long id) {
        return flightService.fetchFlight(id);
    }

    public boolean isSameAirport(SearchFlightsRequest searchFlightsRequest) {
        return !searchFlightsRequest.getFrom().equalsIgnoreCase(searchFlightsRequest.getTo());
    }
}
