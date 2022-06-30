package com.dglapps.simuloc.utils;

import com.dglapps.simuloc.domain.trip.Position;

import static com.dglapps.simuloc.utils.CoordinatesMother.BCN;
import static com.dglapps.simuloc.utils.OffsetDateTimeMother.FIRST_JULY_MIDNIGHT;

public class PositionMother {

    public static Position BCN_POSITION = Position.aBuilder(BCN)
            .withTime(FIRST_JULY_MIDNIGHT)
            .build();
}
