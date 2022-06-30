package com.dglapps.simuloc.domain.player;

import com.dglapps.simuloc.domain.trip.Position;
import com.dglapps.simuloc.domain.player.listener.TripPlayerListener;
import com.dglapps.simuloc.domain.stepcalculator.StepCalculator;

import java.util.LinkedList;
import java.util.List;

public abstract class BaseTripPlayer implements TripPlayer {

    private final List<TripPlayerListener> listeners = new LinkedList<>();

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
    public final void execute(List<StepCalculator> stepCalculators) {
        notifyStart();

        if (stepCalculators == null) {
            notifyEnd();
            return;
        }

        for (StepCalculator stepCalculator : stepCalculators) {
            executeStep(stepCalculator);
        }

        notifyEnd();
    }

    private void executeStep(StepCalculator stepCalculator) {
        stepStarted(stepCalculator);
        for (Position position : stepCalculator.generatePositions()) {
            execute(position);
            positionEmitted(position);
        }
        stepCompleted(stepCalculator);
    }

    protected abstract void execute(Position position);

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
