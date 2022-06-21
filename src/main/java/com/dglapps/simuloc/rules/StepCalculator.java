package com.dglapps.simuloc.rules;

import com.dglapps.simuloc.entities.DynamicPosition;

import java.util.List;

/**
 * Created by dani on 25/2/16.
 */
public interface StepCalculator extends Iterable<DynamicPosition> {

    List<DynamicPosition> generatePositions();

    DynamicPosition getFirstPosition();

    DynamicPosition getLastPosition();
}
