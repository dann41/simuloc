package com.dglapps.simuloc.executors;

import java.time.Clock;

public class StepExecutorTest {

    private RealTimeRulesExecutor realTimeRulesExecutor;

    private void initEmptyExecutor() {
        realTimeRulesExecutor = new RealTimeRulesExecutor(Clock.systemUTC());
    }


}
