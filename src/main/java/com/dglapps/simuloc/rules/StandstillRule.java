package com.dglapps.simuloc.rules;

import com.dglapps.simuloc.entities.DynamicPosition;
import com.dglapps.simuloc.entities.Position;
import com.dglapps.simuloc.entities.PositionFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dani on 25/2/16.
 */
public class StandstillRule implements Rule {

    private final DynamicPosition position;
    private final long duration;
    private final long throughput;

    /**
     * @param position
     * @param duration   in milliseconds
     * @param throughput in milliseconds/position
     */
    public StandstillRule(DynamicPosition position, long duration, long throughput) {
        this.position = position;
        this.duration = duration;
        this.throughput = throughput;
    }

    @Override
    public DynamicPosition getFirstPosition() {
        return position;
    }

    @Override
    public DynamicPosition getLastPosition() {
        return getNewPosition(position, duration);
    }

    @Override
    public List<DynamicPosition> generatePositions() {
        int numPositions = (int) (duration / throughput);

        List<DynamicPosition> positions = new LinkedList<DynamicPosition>();

        positions.add(getFirstPosition());

        DynamicPosition lastPosition = position;
        for (int i = 0; i < numPositions; i++) {
            lastPosition = getNewPosition(lastPosition, throughput);
            positions.add(lastPosition);
        }

        positions.add(getLastPosition());

        return positions;
    }

    private DynamicPosition getNewPosition(DynamicPosition position, long deltaTime) {
        DynamicPosition p = PositionFactory.cloneDynamicPosition(position);
        p.setSpeed(0);
        p.setTime(p.getTime() + deltaTime);
        return p;
    }
}
