package com.dglapps.simuloc.domain.trip;

public record Coordinates(
        double latitude,
        double longitude,
        double altitude
) {

    public Coordinates(double latitude, double longitude) {
        this(latitude, longitude, 0);
    }

    public Coordinates(Coordinates position) {
        this(position.latitude, position.longitude, position.altitude);
    }

    public Coordinates moveDelta(double deltaLatitude, double deltaLongitude) {
        return new Coordinates(
                latitude + deltaLatitude,
                longitude + deltaLongitude
        );
    }
}
