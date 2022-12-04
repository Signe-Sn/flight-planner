package io.codelex.flightplanner.domain;

import io.codelex.flightplanner.dto.PageResult;
import io.codelex.flightplanner.dto.SearchFlightsRequest;
import io.codelex.flightplanner.utility.Formatting;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@ConditionalOnProperty(prefix = "planner", name = "memory", havingValue = "inmemory")
public class InMemoryFlightService implements FlightService {

    private Long idCounter = 0L;
    private InMemoryFlightRepository inMemoryFlightRepository;

    public InMemoryFlightService(InMemoryFlightRepository inMemoryFlightRepository) {
        this.inMemoryFlightRepository = inMemoryFlightRepository;
    }

    @Override
    public Flight addFlight(Flight flight) {
        if (isSameFlight(flight)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        if (isSameAirport(flight)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (isWrongDates(flight)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        flight.setId(idCounter++);
        inMemoryFlightRepository.addFlight(flight);
        return flight;
    }

    private boolean isSameFlight(Flight flight) {
        return inMemoryFlightRepository.getFlightRepository()
                .stream()
                .anyMatch(a -> a.equals(flight));
    }

    private boolean isSameAirport(Flight flight) {
        return (flight.getFrom().equals(flight.getTo()))
                || flight.getFrom().getAirport().equalsIgnoreCase(flight.getTo().getAirport())
                && flight.getFrom().getCity().equalsIgnoreCase(flight.getTo().getCity())
                && flight.getFrom().getCountry().equalsIgnoreCase(flight.getTo().getCountry())
                || flight.getFrom().getAirport().equals(flight.getTo().getAirport().trim());
    }

    public void deleteFlight(long id) {
        inMemoryFlightRepository.deleteFlight(id);
    }

    private boolean isWrongDates(Flight flight) {
        return (flight.getArrivalTime().isBefore(flight.getDepartureTime())
                || flight.getDepartureTime().isEqual(flight.getArrivalTime()));
    }

    @Override
    public PageResult<Flight> searchFlights(SearchFlightsRequest searchFlightsRequest) {
        if (!searchFlightsRequest.getFrom().equalsIgnoreCase(searchFlightsRequest.getTo())) {
            List<Flight> list = inMemoryFlightRepository.getFlightRepository()
                    .stream()
                    .filter(flight -> flight.getFrom().getAirport().equals(searchFlightsRequest.getFrom())
                            && flight.getTo().getAirport().equals(searchFlightsRequest.getTo())
                            && flight.getDepartureTime().toLocalDate().isEqual(Formatting.formatDate(searchFlightsRequest.getDepartureDate())))
                    .toList();
            return new PageResult<>(list.size() / 15, list.size(), list);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @Override
    public List<Airport> searchAirports(String search) {
        String fixedSearch = search.trim().toLowerCase();
        return inMemoryFlightRepository.getFlightRepository()
                .stream()
                .map(Flight::getFrom)
                .filter(from -> from.getAirport().toLowerCase().contains(fixedSearch)
                        || from.getCity().toLowerCase().contains(fixedSearch)
                        || from.getCountry().toLowerCase().contains(fixedSearch)).toList();
    }

    @Override
    public Flight fetchFlight(long id) {
        return inMemoryFlightRepository.getFlightRepository()
                .stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void clear() {
        inMemoryFlightRepository.clear();
    }
}

