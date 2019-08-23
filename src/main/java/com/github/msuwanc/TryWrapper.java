package com.github.msuwanc;

import java.util.Optional;
import java.util.function.Consumer;

public class TryWrapper {
    public static <A, T> Optional<T> tryOpt (Fn<A, T> f, A arg, Consumer<Exception> errorHandler) {
        try {
            return Optional.ofNullable(f.apply(arg));
        } catch (Exception e) {
            errorHandler.accept(e);
            return Optional.empty();
        }
    }

    public static <A, T> Optional<T> tryOpt (Fn<A, T> f, A arg) {
        try {
            return Optional.ofNullable(f.apply(arg));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public interface Fn<A, T> {
        T apply(A a) throws Exception;
    }
}
