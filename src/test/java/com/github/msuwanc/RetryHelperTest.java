package com.github.msuwanc;

import org.junit.Test;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static org.junit.Assert.*;

public class RetryHelperTest {

    @Test
    public void onRetryShouldReturnOptionalOfResult() {
        // GIVEN
        Optional<String> expected = Optional.of("");
        Supplier<Optional<String>> someSupplier = () -> expected;
        int intervalSec = 5;
        int retryCount = 3;

        // WHEN
        Optional<String> actual = RetryHelper.retry(
                someSupplier,
                intervalSec,
                retryCount
        );

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    public void onRetryShouldReturnOptionalOfResultAfterSomeFailed() {
        // GIVEN
        Optional<String> expected = Optional.of("");
        AtomicInteger simulatedFailedTime = new AtomicInteger(0);
        Supplier<Optional<String>> someSupplier = () -> TryWrapper.tryOpt(
                arg -> {
                    if(arg == 2) {
                        return "";
                    } else {
                        throw new RuntimeException("Pretend to be some exception from external service");
                    }
                },
                simulatedFailedTime.getAndIncrement()
        );
        int intervalSec = 5;
        int retryCount = 3;

        // WHEN
        Optional<String> actual = RetryHelper.retry(
                someSupplier,
                intervalSec,
                retryCount
        );

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    public void onRetryShouldReturnOptionalOfEmptyAfterFailedManyTimes() {
        // GIVEN
        Optional<String> expected = Optional.empty();
        AtomicInteger simulatedFailedTime = new AtomicInteger(0);
        Supplier<Optional<String>> someSupplier = () -> TryWrapper.tryOpt(
                arg -> {
                    if(arg == 4) {
                        return "";
                    } else {
                        throw new RuntimeException("Pretend to be some exception from external service");
                    }
                },
                simulatedFailedTime.getAndIncrement()
        );
        int intervalSec = 5;
        int retryCount = 3;

        // WHEN
        Optional<String> actual = RetryHelper.retry(
                someSupplier,
                intervalSec,
                retryCount
        );

        // THEN
        assertEquals(expected, actual);
    }
}
