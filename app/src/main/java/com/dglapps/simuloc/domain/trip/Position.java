package com.dglapps.simuloc.domain.trip;

import com.dglapps.simuloc.shared.validation.NotNull;

import java.time.Duration;
import java.time.OffsetDateTime;

public record Position(
    Coordinates coordinates,
    OffsetDateTime time,
    String source,
    double speed,
    double bearing
) {

  public Position {
    NotNull.validate(coordinates, "Coordinates cannot be null");
    NotNull.validate(time, "time cannot be null");
  }

  public double latitude() {
    return coordinates.latitude();
  }

  public double longitude() {
    return coordinates.longitude();
  }

  public double altitude() {
    return coordinates.altitude();
  }

  public Position afterTime(Duration deltaTime) {
    return aBuilder(coordinates)
        .withSpeed(speed)
        .withSource(source)
        .withBearing(bearing)
        .withTime(time.plus(deltaTime))
        .build();
  }

  public Position afterMoving(double deltaLatitude, double deltaLongitude, Duration deltaTime, double speed) {
    return aBuilder(coordinates.moveDelta(deltaLatitude, deltaLongitude))
        .withSpeed(speed)
        .withSource(source)
        .withBearing(bearing)
        .withTime(time.plus(deltaTime))
        .build();
  }

  public static Builder aBuilder(Coordinates coordinates) {
    return new Builder(coordinates);
  }

  public static class Builder {
    private final Coordinates coordinates;
    private String source;
    private double speed;
    private double bearing;
    private OffsetDateTime time;

    public Builder(Coordinates coordinates) {
      this.coordinates = coordinates;
    }

    public Builder withSource(String source) {
      this.source = source;
      return this;
    }

    public Builder withSpeed(double speed) {
      this.speed = speed;
      return this;
    }

    public Builder withBearing(double bearing) {
      this.bearing = bearing;
      return this;
    }

    public Builder withTime(OffsetDateTime time) {
      this.time = time;
      return this;
    }

    public Position build() {
      return new Position(
          coordinates,
          time,
          source,
          speed,
          bearing
      );
    }
  }
}
