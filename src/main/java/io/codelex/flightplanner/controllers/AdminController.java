package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.domain.FlightService;
import io.codelex.flightplanner.dto.AddFlightRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin-api")
public class AdminController {

    private FlightService flightService;

    public AdminController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PutMapping("/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public synchronized Flight addFlight(@Valid @RequestBody AddFlightRequest addFlightRequest) {
        Flight flight = new Flight(addFlightRequest);
        return flightService.addFlight(flight);
    }

    @DeleteMapping("/flights/{id}")
    public synchronized void deleteFlight(@PathVariable long id) {
        flightService.deleteFlight(id);
    }

    @GetMapping("/flights/{id}")
    public Flight fetchFlight(@PathVariable long id) {
        return flightService.fetchFlight(id);
    }
}
