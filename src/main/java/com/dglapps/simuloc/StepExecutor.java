package com.dglapps.simuloc;

import java.util.Iterator;

public class StepExecutor implements StepListener {

	Iterator<Step> iterator;
	
	public StepExecutor(Iterator<Step> iterator) {
		this.iterator = iterator;
	}
	
	/**
	 * Perform a long task
	 */
	public void execute() {
		if (iterator != null && iterator.hasNext()) {
			execute(iterator.next());
		}
	}
	
	public void execute(Step step) {
		step.setListener(this);
		step.execute();
	}
	
	@Override
	public void onStepFinished() {
		execute();
	}
	
}
