package com.dglapps.simuloc.application.stepadder.standstill;

public record StandstillStepAdderRequest(
    String tripId,
    long durationInSeconds,
    long periodInSecondsPerPosition
) {
}
