package com.dglapps.simuloc.desktop;

import org.omg.CORBA.FREE_MEM;

import com.dglapps.simuloc.PositionReceiver;
import com.dglapps.simuloc.Step;
import com.dglapps.simuloc.StepListener;

public abstract class BaseStep implements Step {

	private StepListener listener;
	private PositionReceiver receiver;
	
	private long totalTimeMillis; 
	private long positionFrequency; // pos / minute
	
	@Override
	public void setDuration(long seconds) {
		this.totalTimeMillis = seconds * 1000;
	}

	@Override
	public void setFrequency(long positionsPerMinute) {
		this.positionFrequency = positionsPerMinute;
	}

	@Override
	public void setListener(StepListener listener) {
		this.listener = listener;
	}

	@Override
	public void setPositionReceiver(PositionReceiver receiver) {
		this.receiver = receiver;
	}

	protected StepListener getListener() {
		return listener;
	}
	
	protected PositionReceiver getReceiver() {
		return receiver;
	}
	
	protected long getTotalTimeMillis() {
		return totalTimeMillis;
	}
	
	protected long getTimeBetweenPositions() {
		return 60000 / this.positionFrequency;
	}
	
	protected long getEstimatedPositions() {
		return positionFrequency * totalTimeMillis / 60000;
	}
}
