package io.codelex.flightplanner.flights;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightRepository {

    private List<Flight> flightRepository = new ArrayList<>();


    public FlightRepository() {
    }

    public void addFlight(Flight flight) {
        flightRepository.add(flight);
    }

    public void clear() {
        flightRepository.clear();
    }

    public void deleteFlight(long id) {
        flightRepository.removeIf(a -> a.getId() == id);
    }

    public List<Flight> getFlightRepository() {
        return flightRepository;
    }
}
