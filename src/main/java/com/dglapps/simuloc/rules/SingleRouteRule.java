package com.dglapps.simuloc.rules;

import com.dglapps.simuloc.Utils;
import com.dglapps.simuloc.entities.DynamicPosition;
import com.dglapps.simuloc.entities.Position;
import com.dglapps.simuloc.entities.PositionFactory;

import java.util.LinkedList;
import java.util.List;

public class SingleRouteRule implements Rule {

    private final DynamicPosition source;
    private final DynamicPosition destination;
    private final long duration;
    private final long throughtput;

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
        this.throughtput = throughput;
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
        final List<DynamicPosition> positions = new LinkedList<DynamicPosition>();

        int numPositions = (int) (duration / throughtput);

        DynamicPosition previousPosition = source;
        positions.add(previousPosition);

        if (numPositions > 0) {
            double deltaLat = (destination.getLatitude() - source.getLatitude()) / numPositions;
            double deltaLng = (destination.getLongitude() - source.getLongitude()) / numPositions;
            long deltaTime = (destination.getTime() - source.getTime()) / numPositions;
            double speed = 1000 * Utils.getDistance(source, destination) / (double) duration;

            for (int i = 0; i < numPositions; i++) {
                previousPosition = generateNextPosition(previousPosition, deltaLat, deltaLng, deltaTime, speed);
                positions.add(previousPosition);
            }
        }
        positions.add(destination);

        return positions;
    }

    private DynamicPosition generateNextPosition(DynamicPosition position, double deltaLat, double deltaLng, long deltaTime, double speed) {
        DynamicPosition p = PositionFactory.cloneDynamicPosition(position);
        p.setLatitude(p.getLatitude() + deltaLat);
        p.setLongitude(p.getLongitude() + deltaLng);
        p.setTime(p.getTime() + deltaTime);

        return p;
    }
}
