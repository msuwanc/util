package com.github.msuwanc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;
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

    public static <T> List<T> page(int pageSize,
                                   Function<Integer, List<T>> fetchLogic) {
        AtomicInteger pageCounter = new AtomicInteger(0);

        Supplier<Optional<List<T>>> fetch = () -> {
            return TryWrapper.tryOpt(
                    fetchLogic::apply,
                    pageCounter.getAndIncrement()
            );
        };

        boolean hasNextPage = true;
        List<T> result = new ArrayList<>();

        while (hasNextPage) {
            Optional<List<T>> optFetchResult = fetch.get();
            List<T> fetchResult = optFetchResult.orElse(Collections.emptyList());

            result.addAll(fetchResult);

            if(fetchResult.size() < pageSize) hasNextPage = false;
        }

        return result;
    }

    public static <T> List<T> convertNullToEmpty(List<T> list) {
        return list == null ? Collections.emptyList() : list;
    }
}
