package com.dglapps.simuloc.listeners;

import com.dglapps.simuloc.entities.Position;
import com.dglapps.simuloc.executors.RulesExecutor;

public class ConsoleRulesExecutorListener implements RulesExecutorListener {

	private long start;

	@Override
	public void onExecutorStart(RulesExecutor executor) {
		System.out.println("Executor started");
        start = System.currentTimeMillis();
	}

	@Override
	public void onExecutorPositionGenerated(RulesExecutor executor, Position position) {
		System.out.println(position.getLatitude() + "," + position.getLongitude());
	}

	@Override
	public void onExecutorEnd(RulesExecutor executor) {
        long end = System.currentTimeMillis();
        System.out.println("Executor finished. Execution took " + (start - end) + " ms.");

	}
}
