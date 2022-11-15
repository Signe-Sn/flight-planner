package io.codelex.flightplanner.flights;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.codelex.flightplanner.admin.AddFlightRequest;
import io.codelex.flightplanner.configuration.Formatting;

import java.time.LocalDateTime;
import java.util.Objects;

public class Flight {

    private long id;

    private Airport from;

    private Airport to;

    private String carrier;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime departureTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime arrivalTime;

    public Flight(AddFlightRequest addFlightRequest) {
        this.from = addFlightRequest.getFrom();
        this.to = addFlightRequest.getTo();
        this.carrier = addFlightRequest.getCarrier();
        this.departureTime = Formatting.formatDateTime(addFlightRequest.getDepartureTime());
        this.arrivalTime = Formatting.formatDateTime(addFlightRequest.getArrivalTime());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Airport getFrom() {
        return from;
    }

    public Airport getTo() {
        return to;
    }

    public String getCarrier() {
        return carrier;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flight flight)) return false;
        return from.equals(flight.from) && to.equals(flight.to) && carrier.equals(flight.carrier) && departureTime.equals(flight.departureTime) && arrivalTime.equals(flight.arrivalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, carrier, departureTime, arrivalTime);
    }

}
