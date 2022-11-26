package io.codelex.flightplanner.domain;

import io.codelex.flightplanner.dto.PageResult;
import io.codelex.flightplanner.dto.SearchFlightsRequest;

import java.util.List;

public interface FlightService {

    Flight addFlight(Flight flight);

    void deleteFlight(long id);

    PageResult<Flight> searchFlights(SearchFlightsRequest searchFlightsRequest);

    List<Airport> searchAirports(String search);

    Flight fetchFlight(long id);

    void clear();
}
