package com.dglapps.simuloc.entities;

/**
 * Created by dani on 26/2/16.
 */
public class PositionFactory {

    public static Position createPosition(double lat, double lng) {
        return new StaticPosition(lat, lng);
    }

    public static DynamicPosition createDynamicPosition(double lat, double lng, long time) {
        return new DynamicPosition(lat, lng, time);
    }

    public static Position clonePosition(Position position) {
        return new StaticPosition(position);
    }

    public static DynamicPosition cloneDynamicPosition(Position position) {
        return new DynamicPosition(position);
    }
}
