package com.dglapps.simuloc.application.stepadder.straight;

public record StraightStepAdderRequest(
    String tripId,
    double latitude,
    double longitude,
    long durationInSeconds,
    long periodInSecondsPerPosition
) {
}
