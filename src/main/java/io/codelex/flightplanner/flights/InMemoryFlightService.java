package io.codelex.flightplanner.flights;

import io.codelex.flightplanner.configuration.Formatting;
import io.codelex.flightplanner.customer.PageResult;
import io.codelex.flightplanner.customer.SearchFlightsRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@ConditionalOnProperty(prefix = "planner", name = "memory", havingValue = "inmemory")
public class InMemoryFlightService implements FlightService {

    private long idCounter = 0;
    FlightRepository flightRepository;

    public InMemoryFlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public Flight addFlight(Flight flight) {
        if (isSameFlight(flight)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        if (isSameAirport(flight)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (isStrangeDates(flight)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        flight.setId(idCounter++);
        flightRepository.addFlight(flight);
        return flight;
    }

    @Override
    public boolean isSameFlight(Flight flight) {
        return flightRepository.getFlightRepository()
                .stream()
                .anyMatch(a -> a.equals(flight));
    }

    @Override
    public boolean isSameAirport(Flight flight) {
        return (flight.getFrom().equals(flight.getTo()))
                || flight.getFrom().getAirport().equalsIgnoreCase(flight.getTo().getAirport())
                && flight.getFrom().getCity().equalsIgnoreCase(flight.getTo().getCity())
                && flight.getFrom().getCountry().equalsIgnoreCase(flight.getTo().getCountry())
                || flight.getFrom().getAirport().equals(flight.getTo().getAirport().trim());
    }

    @Override
    public boolean isStrangeDates(Flight flight) {
        return (flight.getArrivalTime().isBefore(flight.getDepartureTime())
                || flight.getDepartureTime().isEqual(flight.getArrivalTime()));
    }

    @Override
    public PageResult<Flight> searchFlights(SearchFlightsRequest searchFlightsRequest) {
        List<Flight> list = flightRepository.getFlightRepository()
                .stream()
                .filter(flight -> flight.getFrom().getAirport().equals(searchFlightsRequest.getFrom())
                        && flight.getTo().getAirport().equals(searchFlightsRequest.getTo())
                        && flight.getDepartureTime().toLocalDate().isEqual(Formatting.formatDate(searchFlightsRequest.getDepartureDate())))
                .toList();
        return new PageResult<>(list.size() / 15, list.size(), list);
    }

    @Override
    public List<Airport> searchAirports(String search) {
        String fixedSearch = search.trim().toLowerCase();
        return flightRepository.getFlightRepository()
                .stream()
                .map(Flight::getFrom)
                .filter(from -> from.getAirport().toLowerCase().contains(fixedSearch)
                        || from.getCity().toLowerCase().contains(fixedSearch)
                        || from.getCountry().toLowerCase().contains(fixedSearch)).toList();
    }

    @Override
    public Flight fetchFlight(long id) {
        return flightRepository.getFlightRepository()
                .stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}

