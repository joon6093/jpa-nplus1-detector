package io.jeyong.core.context;

import io.jeyong.core.exception.NPlusOneQueryException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public final class ExceptionContext {

    private final AtomicReference<NPlusOneQueryException> primaryException = new AtomicReference<>();

    public void saveException(final NPlusOneQueryException nPlusOneQueryException) {
        primaryException.updateAndGet(existingException -> {
            if (existingException != null) {
                existingException.addSuppressed(nPlusOneQueryException);
                return existingException;
            }

            return nPlusOneQueryException;
        });
    }

    public Optional<NPlusOneQueryException> getContext() {
        return Optional.ofNullable(primaryException.get());
    }

    public void clearContext() {
        primaryException.set(null);
    }
}
