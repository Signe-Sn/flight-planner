package io.codelex.flightplanner.domain;

import io.codelex.flightplanner.dto.PageResult;
import io.codelex.flightplanner.dto.SearchFlightsRequest;
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
    public void deleteFlight(long id) {
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

    @Override
    public void clear() {
    }
}
