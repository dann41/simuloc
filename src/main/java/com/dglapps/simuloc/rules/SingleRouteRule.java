package com.dglapps.simuloc.rules;

import com.dglapps.simuloc.entities.Position;

import java.util.LinkedList;
import java.util.List;

public class SingleRouteRule implements Rule {

	private final Position source;
	private final Position destination;
    private final long duration;
    private final long throughtput;

    /**
     *
     * @param source start position
     * @param destination end position
     * @param duration in milliseconds
     * @param throughput in milliseconds/position
     */
    public SingleRouteRule(Position source, Position destination, long duration, long throughput) {
		this.source = source;
        this.destination = destination;
        this.duration = duration;
        this.throughtput = throughput;
	}

    @Override
    public List<Position> generatePositions() {
        final List<Position> positions = new LinkedList<Position>();

        int numPositions = (int) (duration / throughtput);

        double deltaLat = (destination.getLatitude() - source.getLatitude()) / numPositions;
        double deltaLng = (destination.getLongitude() - source.getLongitude()) / numPositions;
        long deltaTime = (destination.getTime() - source.getTime()) / numPositions;

        Position previousPosition = source;
        positions.add(previousPosition);

        for (int i = 0; i < numPositions; i++) {
            previousPosition = generateNextPosition(previousPosition, deltaLat, deltaLng, deltaTime);
            positions.add(previousPosition);
		}

        positions.add(destination);

        return positions;
	}
	
	private Position generateNextPosition(Position position, double deltaLat, double deltaLng, long deltaTime) {
		Position p = new Position();
		p.setAltitude(position.getAltitude());
		p.setLatitude(position.getLatitude() + deltaLat);
		p.setLongitude(position.getLongitude() + deltaLng);
		p.setTime(position.getTime() + deltaTime);
		p.setBearing(position.getBearing());
		p.setSpeed(position.getSpeed());
		
		return p;
	}
}
