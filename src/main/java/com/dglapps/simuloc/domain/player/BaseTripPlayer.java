package com.dglapps.simuloc.domain.player;

import com.dglapps.simuloc.domain.player.listener.TripPlayerListener;
import com.dglapps.simuloc.domain.stepcalculator.StepCalculator;
import com.dglapps.simuloc.domain.stepcalculator.StepCalculatorFactory;
import com.dglapps.simuloc.domain.trip.Position;
import com.dglapps.simuloc.domain.trip.Step;
import com.dglapps.simuloc.domain.trip.Trip;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BaseTripPlayer implements TripPlayer, TripPlayerObservable {

    private final StepCalculatorFactory stepCalculatorFactory;
    private final PositionPlayer positionPlayer;
    private final List<TripPlayerListener> listeners = new LinkedList<>();

    public BaseTripPlayer(StepCalculatorFactory stepCalculatorFactory, PositionPlayer positionPlayer) {
        this.stepCalculatorFactory = stepCalculatorFactory;
        this.positionPlayer = positionPlayer;
    }


    @Override
    public final synchronized void addListener(final TripPlayerListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    @Override
    public final synchronized void removeListener(TripPlayerListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void play(Trip trip, OffsetDateTime startingDateTime) {
        notifyStart();

        Collection<Step> steps = trip.steps();

        if (steps == null) {
            notifyEnd();
            return;
        }

        OffsetDateTime nextStartingDateTime = startingDateTime;
        for (Step step : steps) {
            executeStep(step, nextStartingDateTime);
            nextStartingDateTime = nextStartingDateTime.plus(step.duration());
        }

        notifyEnd();
    }

    private void executeStep(Step step, OffsetDateTime startingDateTime) {
        StepCalculator stepCalculator = stepCalculatorFactory.stepCalculatorForStep(step, startingDateTime);

        stepStarted(stepCalculator);
        for (Position position : stepCalculator.generatePositions()) {
            try {
                positionPlayer.play(position).get();
                positionEmitted(position);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        stepCompleted(stepCalculator);
    }

    private synchronized void stepStarted(StepCalculator stepCalculator) {
        for (TripPlayerListener listener : listeners) {
            listener.onRuleStart(this, stepCalculator);
        }
    }

    private synchronized void stepCompleted(StepCalculator stepCalculator) {
        for (TripPlayerListener listener : listeners) {
            listener.onRuleEnd(this, stepCalculator);
        }
    }

    private synchronized void positionEmitted(Position position) {
        for (TripPlayerListener listener : listeners) {
            listener.onPositionGenerated(this, position);
        }
    }

    private synchronized void notifyStart() {
        for (TripPlayerListener listener : listeners) {
            listener.onExecutorStart(this);
        }
    }

    private synchronized void notifyEnd() {
        for (TripPlayerListener listener : listeners) {
            listener.onExecutorEnd(this);
        }
    }
}
