package com.dglapps.simuloc.listeners;

import com.dglapps.simuloc.entities.DynamicPosition;
import com.dglapps.simuloc.executors.RulesExecutor;
import com.dglapps.simuloc.rules.Rule;

/**
 * Created by dani on 25/2/16.
 */
public interface RulesExecutorListener {

    void onExecutorStart(RulesExecutor executor);

    void onRuleStart(RulesExecutor executor, Rule rule);

    void onRuleEnd(RulesExecutor executor, Rule rule);

    void onPositionGenerated(RulesExecutor executor, DynamicPosition position);

    void onExecutorEnd(RulesExecutor executor);
}
