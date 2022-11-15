package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.customer.CustomerService;
import io.codelex.flightplanner.customer.PageResult;
import io.codelex.flightplanner.customer.SearchFlightsRequest;
import io.codelex.flightplanner.flights.Airport;
import io.codelex.flightplanner.flights.Flight;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {

    CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/airports")
    public List<Airport> searchAirports(@RequestParam String search) {
        return customerService.searchAirports(search);
    }

    @PostMapping("/flights/search")
    public synchronized PageResult<Flight> searchFlights(@Valid @RequestBody SearchFlightsRequest searchFlightsRequest) {
        return customerService.searchFlights(searchFlightsRequest);
    }

    @GetMapping("/flights/{id}")
    public Flight findFlightById(@PathVariable long id) {
        return customerService.findFlightById(id);
    }
}
