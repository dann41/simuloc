package com.dglapps.simuloc.desktop;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import com.dglapps.simuloc.Position;
import com.dglapps.simuloc.PositionReceiver;
import com.dglapps.simuloc.StepListener;

public class StopStepTest {

	StopStep stopStep;
	StepListener listener;
	PositionReceiver receiver;
	Position position;
	
	@Before
	public void setup() {
		position = new Position();
		
		stopStep = new StopStep(position);
		
		stopStep.setDuration(20);
		stopStep.setFrequency(5);
		
		listener = mock(TestStepListener.class);
		stopStep.setListener(listener);
		
		receiver = mock(TestPositionReceiver.class);
		stopStep.setPositionReceiver(receiver);
	}
	
	@Test
	public void testExecute() {
		stopStep.run();	// instead of execute method, avoid running in another threads
		
		verify(listener, times(1)).onStepFinished();
		verify(receiver, times((int) stopStep.getEstimatedPositions())).onPositionReceived((Position)anyObject());
	}
	
	class TestStepListener implements StepListener {
		
		@Override
		public void onStepFinished() {
			
		}
	}
	
	class TestPositionReceiver implements PositionReceiver {
		@Override
		public void onPositionReceived(Position p) {
			
		}
	}
	
}
