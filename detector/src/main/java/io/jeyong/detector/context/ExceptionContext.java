package io.jeyong.detector.context;

import io.jeyong.detector.exception.NPlusOneQueryException;
import java.util.Optional;

public final class ExceptionContext {

    private NPlusOneQueryException primaryException;

    public void saveException(final NPlusOneQueryException nPlusOneQueryException) {
        if (primaryException != null) {
            primaryException.addSuppressed(nPlusOneQueryException);
        } else {
            primaryException = nPlusOneQueryException;
        }
    }

    public Optional<NPlusOneQueryException> getContext() {
        return Optional.ofNullable(primaryException);
    }

    public void clearContext() {
        primaryException = null;
    }
}
