package com.dglapps.simuloc;

public interface Step {

	public void execute();
	
	public void abort();
	
	public void setDuration(long seconds);
	
	public void setFrequency(long positionsPerMinute);
	
	public void setListener(StepListener listener);
	
	public void setPositionReceiver(PositionReceiver receiver);
}
