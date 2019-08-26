package com.github.msuwanc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListHelper {
    public static <T> List<T> reverse(List<T> list) {
        List<T> reverse = new ArrayList<>(list);
        Collections.reverse(reverse);
        return reverse;
    }

    public static <T> List<T> concat(List<T> listA, List<T> listB) {
        return Stream.of(listA, listB)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
