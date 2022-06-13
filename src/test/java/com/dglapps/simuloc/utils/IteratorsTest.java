package com.dglapps.simuloc.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by dani on 3/3/16.
 */
public class IteratorsTest {

    @Test
    public void testConcat() {
        String[] testData = new String[] {"a", "b", "c"};

        List<String> list1 = new ArrayList<>();
        list1.add(testData[0]);

        List<String> list2 = new LinkedList<>();
        list2.add(testData[1]);
        list2.add(testData[2]);

        Iterator<String> result = Iterators.concat(list1.iterator(), list2.iterator());
        assertNotNull(result);
        int i = 0;
        while (result.hasNext()) {
            String s = result.next();
            assertEquals(testData[i], s);
            i++;
        }
        assertEquals(testData.length, i);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testContactRemoveUnsupported() {
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new LinkedList<>();

        Iterator<String> result = Iterators.concat(list1.iterator(), list2.iterator());
        result.remove();
    }
}
