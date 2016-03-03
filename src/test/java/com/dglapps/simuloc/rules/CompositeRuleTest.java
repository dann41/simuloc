package com.dglapps.simuloc.rules;

import com.dglapps.simuloc.AssertUtils;
import com.dglapps.simuloc.entities.DynamicPosition;
import com.dglapps.simuloc.entities.PositionFactory;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by dani on 3/3/16.
 */
public class CompositeRuleTest {

    @Test
    public void testCompositeRuleWithNoRules() {
        Rule rule = new CompositeRule();

        List<DynamicPosition> list = rule.generatePositions();
        assertNotNull(list);
        assertTrue(list.isEmpty());

        assertNull(rule.getFirstPosition());
        assertNull(rule.getLastPosition());

        Iterator<DynamicPosition> iterator = rule.iterator();
        assertNotNull(iterator);
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testCompositeRuleWithSingleRule() {
        Rule rule1 = new StandstillRule(PositionFactory.createDynamicPosition(0.0, 0.0, 0), 5000, 1000);

        Rule rule = new CompositeRule(rule1);

        List<DynamicPosition> list = rule.generatePositions();
        assertNotNull(list);
        assertEquals(rule1.generatePositions().size(), list.size());

        assertNotNull(rule.getFirstPosition());
        AssertUtils.assertEqualsPosition(rule1.getFirstPosition(), rule.getFirstPosition());

        assertNotNull(rule.getLastPosition());
        AssertUtils.assertEqualsPosition(rule1.getLastPosition(), rule.getLastPosition());

        Iterator<DynamicPosition> iterator = rule.iterator();
        assertNotNull(iterator);
        assertTrue(iterator.hasNext());
    }

    @Test
    public void testCompositeRuleWith2Rules() {
        Rule rule1 = new StandstillRule(PositionFactory.createDynamicPosition(0.0, 0.0, 0), 5000, 1000);
        Rule rule2 = new StandstillRule(PositionFactory.createDynamicPosition(1.0, 1.0, 0), 15000, 2000);

        Rule rule = new CompositeRule(rule1, rule2);

        List<DynamicPosition> list = rule.generatePositions();
        assertNotNull(list);

        int totalExpected = rule1.generatePositions().size() + rule2.generatePositions().size();
        assertEquals(totalExpected, list.size());

        assertNotNull(rule.getFirstPosition());
        AssertUtils.assertEqualsPosition(rule1.getFirstPosition(), rule.getFirstPosition());

        assertNotNull(rule.getLastPosition());
        AssertUtils.assertEqualsPosition(rule2.getLastPosition(), rule.getLastPosition());

        Iterator<DynamicPosition> iterator = rule.iterator();
        assertNotNull(iterator);
        assertTrue(iterator.hasNext());
    }

}
