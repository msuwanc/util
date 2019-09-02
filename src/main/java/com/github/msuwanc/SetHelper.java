package com.github.msuwanc;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SetHelper {
    public static <T> Set<T> concat(Set<T> setA, Set<T> setB) {
        return Stream.of(setA, setB)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }
}
