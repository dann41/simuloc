package com.dglapps.simuloc.application.tripcreator;

public record TripCreatorRequest(
    String tripId,
    String name,
    double latitude,
    double longitude
) {

}
