package com.dglapps.simuloc.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dani on 4/3/16.
 */
public class ListUtils {

    public static <E> List<E> arrayToList(E... eArray) {
        List<E> list = new ArrayList<E>(eArray.length);

        for (E e : eArray) {
            list.add(e);
        }

        return list;
    }
}
