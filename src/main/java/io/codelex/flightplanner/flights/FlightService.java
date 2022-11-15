package io.codelex.flightplanner.flights;

import io.codelex.flightplanner.customer.PageResult;
import io.codelex.flightplanner.customer.SearchFlightsRequest;

import java.util.List;

public interface FlightService {

    Flight addFlight(Flight flight);

    boolean isSameFlight(Flight flight);

    boolean isSameAirport(Flight flight);

    boolean isStrangeDates(Flight flight);

    PageResult<Flight> searchFlights(SearchFlightsRequest searchFlightsRequest);

    List<Airport> searchAirports(String search);

    Flight fetchFlight(long id);
}
