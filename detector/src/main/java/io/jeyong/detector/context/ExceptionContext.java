package io.jeyong.detector.context;

import io.jeyong.detector.exception.NPlusOneQueryException;

public final class ExceptionContext { // Todo: Consider concurrency issues

    private NPlusOneQueryException primaryException;

    public void saveException(final NPlusOneQueryException nPlusOneQueryException) {
        if (primaryException != null) {
            primaryException.addSuppressed(nPlusOneQueryException);
        } else {
            primaryException = nPlusOneQueryException;
        }
    }

    public NPlusOneQueryException getException() {
        return primaryException;
    }

    public void clearException() {
        primaryException = null;
    }
}
