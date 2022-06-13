package com.dglapps.simuloc.entities;

/**
 * Created by dani on 26/2/16.
 */
public class DynamicPosition implements Position {

    private final Position position;

    private String source;
    private double speed;
    private double bearing;
    private double error;
    private long time;

    public DynamicPosition(double lat, double lng, long time) {
        this.position = PositionFactory.createPosition(lat, lng);
        this.time = time;
    }

    public DynamicPosition(Position position) {
        this.position = PositionFactory.clonePosition(position);

        if (position instanceof DynamicPosition) {
            DynamicPosition p = (DynamicPosition) position;
            this.source = p.getSource();
            this.speed = p.getSpeed();
            this.bearing = p.getBearing();
            this.error = p.getError();
            this.time = p.getTime();
        }
    }


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return speed in m/s
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * @param speed in m/s
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getBearing() {
        return bearing;
    }

    public void setBearing(double bearing) {
        this.bearing = bearing;
    }

    /**
     * @return error in meters
     */
    public double getError() {
        return error;
    }

    /**
     * @param error in meters
     */
    public void setError(double error) {
        this.error = error;
    }

    /**
     * @return ms since 1970/1/1
     */
    public long getTime() {
        return time;
    }

    /**
     * @param time ms since 1970/1/1
     */
    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public double getLatitude() {
        return position.getLatitude();
    }

    @Override
    public void setLatitude(double latitude) {
        position.setLatitude(latitude);
    }

    @Override
    public double getLongitude() {
        return position.getLongitude();
    }

    @Override
    public void setLongitude(double longitude) {
        position.setLongitude(longitude);
    }

    @Override
    public double getAltitude() {
        return position.getAltitude();
    }

    @Override
    public void setAltitude(double altitude) {
        position.setAltitude(altitude);
    }
}
