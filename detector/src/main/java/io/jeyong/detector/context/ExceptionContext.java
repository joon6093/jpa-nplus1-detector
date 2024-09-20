package io.jeyong.detector.context;

import io.jeyong.detector.exception.NPlusOneQueryException;
import java.util.Optional;

public final class ExceptionContext {

    private NPlusOneQueryException primaryException;

    public synchronized void saveException(final NPlusOneQueryException nPlusOneQueryException) {
        if (primaryException != null) {
            primaryException.addSuppressed(nPlusOneQueryException);
        } else {
            primaryException = nPlusOneQueryException;
        }
    }

    public synchronized Optional<NPlusOneQueryException> getContext() {
        return Optional.ofNullable(primaryException);
    }

    public synchronized void clearContext() {
        primaryException = null;
    }
}
