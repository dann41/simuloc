package com.dglapps.simuloc.domain.player;

import com.dglapps.simuloc.domain.trip.Trip;
import com.dglapps.simuloc.utils.CoordinatesMother;
import com.dglapps.simuloc.utils.DurationMother;
import com.dglapps.simuloc.utils.PeriodMother;

public class TripObjectMother {
    public static Trip aTripWithTwoSteps() {
        return Trip.Builder.aTrip(CoordinatesMother.BCN)
                .withStandstillStep(DurationMother.TEN_SECONDS, PeriodMother.EVERY_SECOND)
                .withStraightStep(CoordinatesMother.MAD, DurationMother.TEN_SECONDS, PeriodMother.EVERY_SECOND)
                .build();
    }

    public static Trip aTripWithoutSteps() {
        return Trip.Builder.aTrip(CoordinatesMother.BCN)
                .build();

    }
}
