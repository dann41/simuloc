package com.dglapps.simuloc.application.triprename;

public record TripRenamerRequest(
    String tripId,
    String newName
) {
}
