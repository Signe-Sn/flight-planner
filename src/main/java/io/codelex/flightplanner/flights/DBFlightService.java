package io.codelex.flightplanner.flights;

import io.codelex.flightplanner.customer.PageResult;
import io.codelex.flightplanner.customer.SearchFlightsRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ConditionalOnProperty(prefix = "planner", name = "memory", havingValue = "database")
public class DBFlightService implements FlightService {

    @Override
    public Flight addFlight(Flight flight) {
        return null;
    }

    @Override
    public boolean isSameFlight(Flight flight) {
        return false;
    }

    @Override
    public boolean isSameAirport(Flight flight) {
        return false;
    }

    @Override
    public boolean isStrangeDates(Flight flight) {
        return false;
    }

    @Override
    public PageResult<Flight> searchFlights(SearchFlightsRequest searchFlightsRequest) {
        return null;
    }

    @Override
    public List<Airport> searchAirports(String search) {
        return null;
    }

    @Override
    public Flight fetchFlight(long id) {
        return null;
    }
}
