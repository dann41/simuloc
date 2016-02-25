package com.dglapps.simuloc;

import static org.mockito.Mockito.mock;

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
