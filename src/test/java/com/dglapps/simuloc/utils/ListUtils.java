package com.dglapps.simuloc.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dani on 4/3/16.
 */
public class ListUtils {

    public static <E> List<E> arrayToList(E... eArray) {
        List<E> list = new ArrayList<>(eArray.length);
        list.addAll(Arrays.asList(eArray));
        return list;
    }
}
