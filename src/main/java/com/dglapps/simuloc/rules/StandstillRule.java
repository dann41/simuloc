package com.dglapps.simuloc.rules;

import com.dglapps.simuloc.entities.DynamicPosition;
import com.dglapps.simuloc.entities.Position;
import com.dglapps.simuloc.entities.PositionFactory;

import java.util.Iterator;
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
        List<DynamicPosition> positions = new LinkedList<DynamicPosition>();
        for (DynamicPosition pos : this) {
            positions.add(pos);
        }

        return positions;
    }

    @Override
    public Iterator<DynamicPosition> iterator() {
        return new PositionIterator();
    }


    private DynamicPosition getNewPosition(DynamicPosition position, long deltaTime) {
        DynamicPosition p = PositionFactory.cloneDynamicPosition(position);
        p.setSpeed(0);
        p.setTime(p.getTime() + deltaTime);
        return p;
    }

    private class PositionIterator implements Iterator<DynamicPosition> {

        private final int numPositions;
        private int generatedPositions;
        private DynamicPosition lastPosition;

        public PositionIterator() {
            this.numPositions = (int) (duration / throughput);
            this.generatedPositions = 0;
        }

        @Override
        public boolean hasNext() {
            return generatedPositions < numPositions + 2;
        }

        @Override
        public DynamicPosition next() {
            if (lastPosition == null) {
                lastPosition = getFirstPosition();
            } else if (generatedPositions == numPositions + 1) {
                lastPosition = getLastPosition();
            } else {
                lastPosition = getNewPosition(lastPosition, throughput);
            }
            generatedPositions++;
            return lastPosition;
        }

    }
}
