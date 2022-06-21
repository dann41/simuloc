package com.dglapps.simuloc;

import com.dglapps.simuloc.entities.DynamicPosition;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Created by dani on 26/2/16.
 */
public class AssertUtils {

    public static void assertEqualsPosition(DynamicPosition expected, DynamicPosition result) {
        assertEquals(expected.getLatitude(), result.getLatitude(), 0.000002);
        assertEquals(expected.getLongitude(), result.getLongitude(), 0.000002);
        assertEquals(expected.getTime(), result.getTime());
    }

    public static void assertTimeIncreasing(List<DynamicPosition> positions) {
        DynamicPosition previous = null;
        for (DynamicPosition p : positions) {
            if (previous == null) {
                previous = p;
            } else {
                assertTrue(previous.getTime() <= p.getTime());
            }
        }
    }
}
