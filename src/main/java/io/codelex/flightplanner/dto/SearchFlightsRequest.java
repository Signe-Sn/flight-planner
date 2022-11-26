package io.codelex.flightplanner.dto;

import javax.validation.constraints.NotNull;

public class SearchFlightsRequest {
    @NotNull
    private String from;
    @NotNull
    private String to;
    @NotNull
    private String departureDate;

    public SearchFlightsRequest(String from, String to, String departureDate) {
        this.from = from;
        this.to = to;
        this.departureDate = departureDate;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getDepartureDate() {
        return departureDate;
    }
}
