package io.jeyong.detector.context;

import io.jeyong.detector.test.NPlusOneQueryException;

public final class ExceptionContext {

    private NPlusOneQueryException primaryException;

    void addSuppressPrimaryException(final NPlusOneQueryException exception) {
        if (primaryException != null) {
            primaryException.addSuppressed(exception);
        } else {
            primaryException = exception;
        }
    }

    public NPlusOneQueryException getPrimaryException() {
        return primaryException;
    }
}
