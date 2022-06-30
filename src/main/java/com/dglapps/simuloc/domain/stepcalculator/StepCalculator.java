package com.dglapps.simuloc.domain.stepcalculator;

import com.dglapps.simuloc.domain.trip.Position;

import java.util.List;

/**
 * Created by dani on 25/2/16.
 */
public interface StepCalculator {

    List<Position> generatePositions();

    Position getFirstPosition();

    Position getLastPosition();
}
