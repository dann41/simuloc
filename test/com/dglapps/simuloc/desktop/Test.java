package com.dglapps.simuloc.desktop;

import java.util.ArrayList;
import java.util.List;

import com.dglapps.simuloc.Step;
import com.dglapps.simuloc.StepExecutor;
import com.dglapps.simuloc.entities.Position;

public class Test {

	public static void main(String[] args) {
		List<Step> list = new ArrayList<Step>();
		
		ConsolePositionReceiver receiver = new ConsolePositionReceiver();
		
		Position p = getPosition();
		
		Step step = new StopStep(p);
		step.setDuration(60);
		step.setFrequency(6);
		step.setPositionReceiver(receiver);
		
		list.add(step);
		
		StepExecutor stepExecutor = new StepExecutor(list.iterator());
		stepExecutor.execute();
	}
	
	private static Position getPosition() {
		Position p = new Position();
		
		p.setLatitude(2.554646);
		p.setLongitude(38.45684);
		
		return p;
	}
}
