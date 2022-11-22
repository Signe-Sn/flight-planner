package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.domain.FlightService;
import io.codelex.flightplanner.dto.PageResult;
import io.codelex.flightplanner.dto.SearchFlightsRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private FlightService flightService;

    public CustomerController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/airports")
    public List<Airport> searchAirports(@RequestParam String search) {
        return flightService.searchAirports(search);
    }

    @PostMapping("/flights/search")
    public synchronized PageResult<Flight> searchFlights(@Valid @RequestBody SearchFlightsRequest searchFlightsRequest) {
        return flightService.searchFlights(searchFlightsRequest);
    }


    @GetMapping("/flights/{id}")
    public Flight findFlightById(@PathVariable long id) {
        return flightService.fetchFlight(id);
    }
}
