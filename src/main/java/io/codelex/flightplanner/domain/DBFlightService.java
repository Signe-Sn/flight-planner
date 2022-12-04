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
@ConditionalOnProperty(prefix = "planner", name = "memory", havingValue = "database")
public class DBFlightService implements FlightService {

    private AirportRepository airportRepository;
    private FlightRepository flightRepository;

    public DBFlightService(AirportRepository airportRepository, FlightRepository flightRepository) {
        this.airportRepository = airportRepository;
        this.flightRepository = flightRepository;
    }

    @Override
    public Flight addFlight(Flight flight) {
        if (isSameFlight(flight)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        if (isWrongDates(flight)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (isSameAirport(flight)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        airportRepository.save(flight.getFrom());
        airportRepository.save(flight.getTo());
        flightRepository.save(flight);
        return flight;
    }

    public boolean isSameFlight(Flight flight) {
        return flightRepository
                .findAll()
                .stream()
                .anyMatch(a -> a.equals(flight));
    }

    private boolean isWrongDates(Flight flight) {
        return (flight.getArrivalTime().isBefore(flight.getDepartureTime())
                || flight.getDepartureTime().isEqual(flight.getArrivalTime()));
    }

    private boolean isSameAirport(Flight flight) {
        return (flight.getFrom().equals(flight.getTo()))
                || flight.getFrom().getAirport().equalsIgnoreCase(flight.getTo().getAirport())
                && flight.getFrom().getCity().equalsIgnoreCase(flight.getTo().getCity())
                && flight.getFrom().getCountry().equalsIgnoreCase(flight.getTo().getCountry())
                || flight.getFrom().getAirport().equals(flight.getTo().getAirport().trim());
    }

    @Override
    public void deleteFlight(long id) {
        if (flightRepository.existsById(id)) {
            flightRepository.deleteById(id);
        }
    }

    @Override
    public PageResult<Flight> searchFlights(SearchFlightsRequest searchFlightsRequest) {
        if (!searchFlightsRequest.getFrom().equalsIgnoreCase(searchFlightsRequest.getTo())) {
            List<Flight> list = flightRepository.findAll()
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
        return flightRepository.findAll().stream()
                .map(Flight::getFrom)
                .filter(from -> from.getAirport().toLowerCase().contains(fixedSearch)
                        || from.getCity().toLowerCase().contains(fixedSearch)
                        || from.getCountry().toLowerCase().contains(fixedSearch)).toList();
    }

    @Override
    public Flight fetchFlight(long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void clear() {
        flightRepository.deleteAll();
        airportRepository.deleteAll();
    }
}
