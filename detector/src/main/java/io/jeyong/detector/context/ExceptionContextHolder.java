package io.jeyong.detector.context;

import io.jeyong.detector.test.NPlusOneQueryException;

public final class ExceptionContextHolder {

    private static final ThreadLocal<ExceptionContext> exceptionContextThreadLocal =
            ThreadLocal.withInitial(ExceptionContext::new);

    private ExceptionContextHolder() {
    }

    public static void saveException(final NPlusOneQueryException exception) {
        exceptionContextThreadLocal.get().addSuppressPrimaryException(exception);
    }

    public static ExceptionContext getContext() {
        return exceptionContextThreadLocal.get();
    }

    public static void clearContext() {
        exceptionContextThreadLocal.remove();
    }
}
