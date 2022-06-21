package com.dglapps.simuloc.listeners;

import com.dglapps.simuloc.entities.DynamicPosition;
import com.dglapps.simuloc.executors.TripReplay;
import com.dglapps.simuloc.rules.StepCalculator;

/**
 * Created by dani on 25/2/16.
 */
public interface RulesExecutorListener {

    void onExecutorStart(TripReplay executor);

    void onRuleStart(TripReplay executor, StepCalculator stepCalculator);

    void onRuleEnd(TripReplay executor, StepCalculator stepCalculator);

    void onPositionGenerated(TripReplay executor, DynamicPosition position);

    void onExecutorEnd(TripReplay executor);
}
