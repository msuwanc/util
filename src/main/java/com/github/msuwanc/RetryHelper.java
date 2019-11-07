package com.github.msuwanc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.Supplier;

public class RetryHelper {
    private static final Logger LOG = LoggerFactory.getLogger(RetryHelper.class);

    public static <T> Optional<T> retry(Supplier<Optional<T>> mainLogic, int intervalSec, int retryCount) {
        Optional<T> optResult = mainLogic.get();

        if(retryCount > 0 && !optResult.isPresent()) {
            try {
                Thread.sleep(intervalSec * 1000);
            } catch (InterruptedException e) {
                LOG.warn(e.getMessage(), e);
            }

            return retry(mainLogic, intervalSec, retryCount - 1);
        } else {
            return optResult;
        }
    }
}
