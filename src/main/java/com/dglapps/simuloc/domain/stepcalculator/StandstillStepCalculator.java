package com.dglapps.simuloc.domain.stepcalculator;

import com.dglapps.simuloc.domain.trip.Period;
import com.dglapps.simuloc.domain.trip.Position;
import com.dglapps.simuloc.domain.trip.Step;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Created by dani on 25/2/16.
 */
public class StandstillStepCalculator implements StepCalculator {
  private final Position position;
  private final Duration duration;
  private final Period period;

  public StandstillStepCalculator(Position position, Duration duration, Period period) {
    this.position = position;
    this.duration = duration;
    this.period = period;
  }

  public StandstillStepCalculator(Step step, OffsetDateTime initialDateTime) {
    this(
        Position.aBuilder(step.firstPosition()).withTime(initialDateTime).build(),
        step.duration(),
        step.period()
    );
  }

  @Override
  public Position getFirstPosition() {
    return position;
  }

  @Override
  public Position getLastPosition() {
    return getNewPosition(position, duration);
  }

  @Override
  public List<Position> generatePositions() {
    List<Position> positions = new LinkedList<>();
    for (Iterator<Position> it = iterator(); it.hasNext(); ) {
      Position pos = it.next();
      positions.add(pos);
    }

    return positions;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StandstillStepCalculator that = (StandstillStepCalculator) o;
    return Objects.equals(position, that.position) && Objects.equals(duration, that.duration) &&
        Objects.equals(period, that.period);
  }

  @Override
  public int hashCode() {
    return Objects.hash(position, duration, period);
  }

  private Iterator<Position> iterator() {
    return new PositionIterator();
  }

  private Position getNewPosition(Position position, Duration deltaTime) {
    return position.afterTime(deltaTime);
  }

  private class PositionIterator implements Iterator<Position> {

    private final long numPositions;
    private long generatedPositions;
    private Position lastPosition;

    PositionIterator() {
      this.numPositions = (long) Math.floor(duration.toMillis() / period.value().toMillis());
      this.generatedPositions = 0;
    }

    @Override
    public boolean hasNext() {
      return generatedPositions < numPositions + 2;
    }

    @Override
    public Position next() {
      if (lastPosition == null) {
        lastPosition = getFirstPosition();
      } else if (generatedPositions == numPositions + 1) {
        lastPosition = getLastPosition();
      } else {
        lastPosition = getNewPosition(lastPosition, period.value());
      }
      generatedPositions++;
      return lastPosition;
    }

  }
}
