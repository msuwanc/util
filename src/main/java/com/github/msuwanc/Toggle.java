package com.github.msuwanc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.Supplier;

public class Toggle {
    private static final Logger LOGGER = LoggerFactory.getLogger(Toggle.class);

    public static void exec(boolean isToggleOn, Runnable mainLogic) {
        if (isToggleOn) mainLogic.run();
    }

    public static <T> Optional<T> exec(boolean isToggleOn, Supplier<T> mainLogic) {
        Supplier<Optional<T>> handleToggleOff = () -> {
            LOGGER.info("Toggle is off");
            return Optional.empty();
        };

        if (isToggleOn) return Optional.ofNullable(mainLogic.get());
        else return handleToggleOff.get();
    }
}
