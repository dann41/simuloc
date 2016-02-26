package com.dglapps.simuloc.executors;

import com.dglapps.simuloc.entities.DynamicPosition;
import com.dglapps.simuloc.entities.Position;
import com.dglapps.simuloc.listeners.RulesExecutorListener;
import com.dglapps.simuloc.rules.Rule;

import java.util.List;

/**
 * Created by dani on 25/2/16.
 */
public interface RulesExecutor {

    void execute(List<Rule> rules);

    void execute(DynamicPosition position);

    void addListener(RulesExecutorListener listener);

    void removeListener(RulesExecutorListener listener);
}
