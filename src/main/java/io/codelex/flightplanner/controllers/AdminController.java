package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.flights.Flight;
import io.codelex.flightplanner.admin.AddFlightRequest;
import io.codelex.flightplanner.admin.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin-api")
public class AdminController {

    AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PutMapping("/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public synchronized Flight addFlight(@Valid @RequestBody AddFlightRequest addFlightRequest) {
        return adminService.addFlight(addFlightRequest);
    }

    @DeleteMapping("/flights/{id}")
    public synchronized void deleteFlight(@PathVariable long id) {
        adminService.deleteFlight(id);
    }

    @GetMapping("/flights/{id}")
    public Flight fetchFlight(@PathVariable long id) {
        return adminService.fetchFlight(id);
    }
}
