package com.dglapps.simuloc.executors;

import static org.mockito.Mockito.mock;

import com.dglapps.simuloc.executors.StepExecutor;
import org.junit.Test;

public class StepExecutorTest {

	StepExecutor stepExecutor;
	
	private void initEmptyExecutor() {
		stepExecutor = new StepExecutor(null);
	}
	
	@Test
	public void testExecuteNullSteps() {
		stepExecutor = mock(StepExecutor.class);
		
		
	}
	
	@Test
	public void testExecute0Steps() {
		
	}
	
	@Test
	public void testExecuteStep() {
		
	}
	
}
