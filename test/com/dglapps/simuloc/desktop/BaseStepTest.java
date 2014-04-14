package com.dglapps.simuloc.desktop;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BaseStepTest {

	private BaseStep baseStep;
	
	@Before
	public void setUp() {
		baseStep = new TestBaseStep();
	}
	
	@Test
	public void testGetMillisecondsBetweenPositions() {
		baseStep.setDuration(120);	// 2 minutes
		baseStep.setFrequency(5);	// 5 pos x minute
		
		Assert.assertEquals(12000, baseStep.getTimeBetweenPositions());
	}
	
	@Test
	public void testGetEstimatedPositions() {
		baseStep.setDuration(120);	// 2 minutes
		baseStep.setFrequency(5);	// 5 pos per minute

		Assert.assertEquals(10, baseStep.getEstimatedPositions());
	}
	
	
	class TestBaseStep extends BaseStep {

		@Override
		public void execute() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void abort() {
			// TODO Auto-generated method stub
			
		}
		
	}
}
