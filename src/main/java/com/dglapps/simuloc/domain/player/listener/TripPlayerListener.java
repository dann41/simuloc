package com.dglapps.simuloc.domain.player.listener;

import com.dglapps.simuloc.domain.player.TripPlayer;
import com.dglapps.simuloc.domain.trip.Position;
import com.dglapps.simuloc.domain.stepcalculator.StepCalculator;

public interface TripPlayerListener {

    void onExecutorStart(TripPlayer executor);

    void onRuleStart(TripPlayer executor, StepCalculator stepCalculator);

    void onRuleEnd(TripPlayer executor, StepCalculator stepCalculator);

    void onPositionGenerated(TripPlayer executor, Position position);

    void onExecutorEnd(TripPlayer executor);
}
