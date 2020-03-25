package com.github.msuwanc;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MapHelper {
    public static <A, B> Map<B, Set<A>> reverse(Map<A, Collection<B>> map) {
        return map.entrySet()
                .stream()
                .flatMap(entry -> entry.getValue().stream().map(bundleId -> Pair.of(bundleId, entry.getKey())))
                .collect(Collectors.groupingBy(Pair::getKey, HashMap::new, Collectors.mapping(Pair::getValue, Collectors.toSet())));
    }
}
