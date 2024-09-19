package io.jeyong.detector.context;

import io.jeyong.detector.test.NPlusOneQueryException;

public final class ExceptionContext { // Todo: Consider concurrency issues

    private NPlusOneQueryException primaryException;

    public void saveException(final NPlusOneQueryException exception) {
        if (primaryException != null) {
            primaryException.addSuppressed(exception);
        } else {
            primaryException = exception;
        }
    }

    public NPlusOneQueryException getException() {
        return primaryException;
    }

    public void clearException() {
        primaryException = null;
    }
}
