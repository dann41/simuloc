package com.dglapps.simuloc.domain.trip;

import com.dglapps.simuloc.shared.validation.NotNull;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Trip {
  private final TripId id;
  private Name name;

  private final CreationDate creationDate;
  private final Coordinates origin;
  private final List<Step> steps;

  public Trip(TripId id, Name name, CreationDate creationDate, Coordinates origin, List<Step> steps) {
    NotNull.validate(id, "id");
    NotNull.validate(name, "name");
    NotNull.validate(creationDate, "creationDate");
    NotNull.validate(origin, "origin cannot be null");
    NotNull.validate(steps, "steps");

    this.id = id;
    this.name = name;
    this.creationDate = creationDate;
    this.origin = origin;
    this.steps = new ArrayList<>(steps);
  }

  public Trip(Coordinates origin, List<Step> steps) {
    this(TripId.random(), new Name("Unnamed"), CreationDate.now(), origin, steps);
  }

  public void addStep(AddStepCommand addStepCommand) {
    Step nextStep = lastStep()
        .map(lastStep -> Step.fromPreviousStep(lastStep, addStepCommand.destination(), addStepCommand.duration(),
            addStepCommand.period(), addStepCommand.type()))
        .orElse(
            Step.fromOrigin(origin, addStepCommand.destination(), addStepCommand.duration(), addStepCommand.period(),
                addStepCommand.type()));

    steps.add(nextStep);
  }

  public void removeStep(int position) {
    validateStepIndex(position);

    Step stepToRemove = steps.get(position);

    if (steps.size() - 1 < position) {
      int nextPosition = position + 1;
      Step nextStep = steps.get(nextPosition);
      Step updatedNextStep = nextStep.withFirstPosition(stepToRemove.firstPosition());
      steps.remove(nextStep);
      steps.add(nextPosition, updatedNextStep);
    }

    steps.remove(stepToRemove);
  }

  public void moveStep(int fromPosition, int toPosition) {
    validateStepIndex(fromPosition);
    validateStepIndex(toPosition);

    Step step = steps.remove(fromPosition);
    steps.add(toPosition, step);
  }

  public TripId id() {
    return id;
  }

  public Name name() {
    return name;
  }

  public CreationDate creationDate() {
    return creationDate;
  }

  public Coordinates firstPosition() {
    return origin;
  }

  public Coordinates lastPosition() {
    return lastStep()
        .map(Step::lastPosition)
        .orElse(firstPosition());
  }

  private Optional<Step> lastStep() {
    if (steps.isEmpty()) {
      return Optional.empty();
    }

    return Optional.ofNullable(steps.get(steps.size() - 1));
  }

  private void validateStepIndex(int fromPosition) {
    if (steps.size() <= fromPosition) {
      throw new IllegalArgumentException("There are no step at position " + fromPosition);
    }
  }

  public Collection<Step> steps() {
    return Collections.unmodifiableList(steps);
  }

  public void rename(Name newName) {
    this.name = newName;
  }

  public static class Builder {

    private TripId tripId = TripId.random();

    private Name name = new Name("Unnamed");

    private CreationDate creationDate = CreationDate.now();

    private final Coordinates firstPosition;
    private Coordinates nextStepStartPosition;
    private final List<Step> steps;

    private Builder(Coordinates firstPosition) {
      this.firstPosition = firstPosition;
      this.nextStepStartPosition = firstPosition;
      this.steps = new ArrayList<>();
    }

    public static Builder aTrip(Coordinates firstPosition) {
      return new Builder(firstPosition);
    }

    public Builder withId(String uuid) {
      tripId = new TripId(uuid);
      return this;
    }

    public Builder withName(String value) {
      name = new Name(value);
      return this;
    }

    public Builder withCreationDate(OffsetDateTime offsetDateTime) {
      creationDate = new CreationDate(offsetDateTime);
      return this;
    }

    public Builder withStandstillStep(Duration duration, Period period) {
      Step standstillStep = new Step(
          nextStepStartPosition,
          nextStepStartPosition,
          duration,
          period,
          StepType.STANDSTILL
      );
      steps.add(standstillStep);
      return this;
    }

    public Builder withStraightStep(Coordinates endPosition, Duration duration, Period period) {
      Step straightStep = new Step(
          nextStepStartPosition,
          endPosition,
          duration,
          period,
          StepType.STRAIGHT
      );
      steps.add(straightStep);
      nextStepStartPosition = endPosition;
      return this;
    }

    public Trip build() {
      return new Trip(tripId, name, creationDate, firstPosition, steps);
    }

  }

}
