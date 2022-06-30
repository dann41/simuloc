package com.dglapps.simuloc.domain.player;

import com.dglapps.simuloc.domain.player.listener.TripPlayerListener;
import com.dglapps.simuloc.domain.stepcalculator.StepCalculator;

import java.util.List;

/**
 * Created by dani on 25/2/16.
 */
public interface TripPlayer {

    void execute(List<StepCalculator> stepCalculators);

    void addListener(TripPlayerListener listener);

    void removeListener(TripPlayerListener listener);
}
