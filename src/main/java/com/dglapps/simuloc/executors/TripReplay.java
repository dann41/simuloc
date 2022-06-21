package com.dglapps.simuloc.executors;

import com.dglapps.simuloc.entities.DynamicPosition;
import com.dglapps.simuloc.listeners.RulesExecutorListener;
import com.dglapps.simuloc.rules.StepCalculator;

import java.util.List;

/**
 * Created by dani on 25/2/16.
 */
public interface TripReplay {

    void execute(List<StepCalculator> stepCalculators);

    void execute(DynamicPosition position);

    void addListener(RulesExecutorListener listener);

    void removeListener(RulesExecutorListener listener);
}
