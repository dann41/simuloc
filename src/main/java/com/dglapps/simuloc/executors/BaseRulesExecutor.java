package com.dglapps.simuloc.executors;

import com.dglapps.simuloc.entities.DynamicPosition;
import com.dglapps.simuloc.listeners.RulesExecutorListener;
import com.dglapps.simuloc.rules.Rule;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dani on 25/2/16.
 */
public abstract class BaseRulesExecutor implements RulesExecutor {

    private List<RulesExecutorListener> listeners = new LinkedList<RulesExecutorListener>();

    @Override
    public final synchronized void addListener(final RulesExecutorListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    @Override
    public final synchronized void removeListener(RulesExecutorListener listener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    @Override
    public final void execute(List<Rule> rules) {
        notifyStart();

        if (rules != null) {
            for (Rule rule : rules) {
                notifyRuleStart(rule);

                // Do the work
                List<DynamicPosition> positions = rule.generatePositions();
                if (positions != null) {
                    for (DynamicPosition position : positions) {
                        execute(position);
                        notifyPosition(position);
                    }
                }

                notifyRuleEnd(rule);
            }
        }

        notifyEnd();
    }

    private synchronized void notifyRuleStart(Rule rule) {
        for (RulesExecutorListener listener : listeners) {
            listener.onRuleStart(this, rule);
        }
    }

    private synchronized void notifyRuleEnd(Rule rule) {
        for (RulesExecutorListener listener : listeners) {
            listener.onRuleEnd(this, rule);
        }
    }

    private synchronized void notifyPosition(DynamicPosition position) {
        for (RulesExecutorListener listener : listeners) {
            listener.onPositionGenerated(this, position);
        }
    }

    private synchronized void notifyStart() {
        for (RulesExecutorListener listener : listeners) {
            listener.onExecutorStart(this);
        }
    }

    private synchronized void notifyEnd() {
        for (RulesExecutorListener listener : listeners) {
            listener.onExecutorEnd(this);
        }
    }
}
