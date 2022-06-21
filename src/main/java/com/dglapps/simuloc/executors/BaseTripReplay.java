package com.dglapps.simuloc.executors;

import com.dglapps.simuloc.entities.DynamicPosition;
import com.dglapps.simuloc.listeners.RulesExecutorListener;
import com.dglapps.simuloc.rules.StepCalculator;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dani on 25/2/16.
 */
public abstract class BaseTripReplay implements TripReplay {

    private final List<RulesExecutorListener> listeners = new LinkedList<>();

    @Override
    public final synchronized void addListener(final RulesExecutorListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    @Override
    public final synchronized void removeListener(RulesExecutorListener listener) {
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
            notifyRuleStart(stepCalculator);

            // Do the work
            for (DynamicPosition position : stepCalculator) {
                execute(position);
                notifyPosition(position);
            }

            notifyRuleEnd(stepCalculator);
        }

        notifyEnd();
    }

    private synchronized void notifyRuleStart(StepCalculator stepCalculator) {
        for (RulesExecutorListener listener : listeners) {
            listener.onRuleStart(this, stepCalculator);
        }
    }

    private synchronized void notifyRuleEnd(StepCalculator stepCalculator) {
        for (RulesExecutorListener listener : listeners) {
            listener.onRuleEnd(this, stepCalculator);
        }
    }

    private synchronized void notifyPosition(DynamicPosition position) {
        for (RulesExecutorListener listener : listeners) {
            listener.onPositionGenerated(this, position);
        }
    }

    private synchronized void notifyStart() {
        for (RulesExecutorListener listener : listeners) {
            listener.onExecutorStart(this);
        }
    }

    private synchronized void notifyEnd() {
        for (RulesExecutorListener listener : listeners) {
            listener.onExecutorEnd(this);
        }
    }
}
