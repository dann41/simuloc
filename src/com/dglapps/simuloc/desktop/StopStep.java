package com.dglapps.simuloc.desktop;

import com.dglapps.simuloc.Position;

public class StopStep extends BaseStep implements Runnable {

	private Position position;
	
	private boolean abort;
	
	public StopStep(Position p) {
		this.position = p;
		this.abort = false;
	}	
	
	@Override
	public void run() {
		long positionsPending = getEstimatedPositions();
		long timeBetweenPositions = getTimeBetweenPositions();
		
		while (!abort && positionsPending > 0) {
			try {
				Thread.sleep(timeBetweenPositions);
				Position p = generatePosition();
				getReceiver().onPositionReceived(p);
			} catch (InterruptedException e) { }
			positionsPending--;
		}
		getListener().onStepFinished();
	}
	
	@Override
	public void execute() {
		new Thread(this).start();
	}

	@Override
	public void abort() {
		abort = true;
	}

	private Position generatePosition() {
		Position p = new Position();
		p.setAltitude(this.position.getAltitude());
		p.setLatitude(this.position.getLatitude());
		p.setLongitude(this.position.getLongitude());
		p.setTime(System.currentTimeMillis());
		p.setBearing(this.position.getBearing());
		p.setSpeed(this.position.getSpeed());
		
		return p;
	}
}
