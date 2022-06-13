package com.dglapps.simuloc.rules;

import com.dglapps.simuloc.Utils;
import com.dglapps.simuloc.entities.DynamicPosition;
import com.dglapps.simuloc.entities.PositionFactory;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SingleRouteRule implements Rule {

    private final DynamicPosition source;
    private final DynamicPosition destination;
    private final long duration;
    private final long throughput;

    /**
     * @param source      start position
     * @param destination end position
     * @param duration    in milliseconds
     * @param throughput  in milliseconds/position
     */
    public SingleRouteRule(DynamicPosition source, DynamicPosition destination, long duration, long throughput) {
        this.source = source;
        this.destination = destination;
        this.duration = duration;
        this.throughput = throughput;
    }

    @Override
    public DynamicPosition getFirstPosition() {
        return source;
    }

    @Override
    public DynamicPosition getLastPosition() {
        return destination;
    }

    @Override
    public List<DynamicPosition> generatePositions() {
        final List<DynamicPosition> positions = new LinkedList<>();
        for (DynamicPosition p : this) {
            positions.add(p);
        }

        return positions;
    }

    @Override
    public Iterator<DynamicPosition> iterator() {
        return new PositionIterator();
    }

    private class PositionIterator implements Iterator<DynamicPosition> {

        private final int numPositions;
        private final double deltaLat;
        private final double deltaLng;
        private final long deltaTime;
        private final double speed;

        private DynamicPosition lastPosition;
        private int generatedPositions;

        public PositionIterator() {
            this.numPositions = (int) (duration / throughput);

            if (numPositions != 0) {
                this.deltaLat = (destination.getLatitude() - source.getLatitude()) / numPositions;
                this.deltaLng = (destination.getLongitude() - source.getLongitude()) / numPositions;
                this.deltaTime = (destination.getTime() - source.getTime()) / numPositions;
                this.speed = 1000 * Utils.getDistance(source, destination) / (double) duration;
            } else {
                this.deltaLat = 0;
                this.deltaLng = 0;
                this.deltaTime = 0;
                this.speed = 0;
            }

            this.generatedPositions = 0;
        }

        @Override
        public boolean hasNext() {
            return generatedPositions < numPositions + 2;
        }

        @Override
        public DynamicPosition next() {
            if (lastPosition == null) {
                lastPosition = source;
            } else if (generatedPositions == numPositions + 1) {
                lastPosition = destination;
            } else {
                lastPosition = generateNextPosition(lastPosition, deltaLat, deltaLng, deltaTime, speed);
            }
            generatedPositions++;
            return lastPosition;
        }

        private DynamicPosition generateNextPosition(DynamicPosition position, double deltaLat, double deltaLng, long deltaTime, double speed) {
            DynamicPosition p = PositionFactory.cloneDynamicPosition(position);
            p.setLatitude(p.getLatitude() + deltaLat);
            p.setLongitude(p.getLongitude() + deltaLng);
            p.setTime(p.getTime() + deltaTime);
            p.setSpeed(speed);
            return p;
        }
    }
}
