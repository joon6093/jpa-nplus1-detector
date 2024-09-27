package io.jeyong.detector.exception;

public class NPlusOneQueryException extends RuntimeException {

    public NPlusOneQueryException(final String query, final Long count) {
        super(String.format("N+1 query detected: '%s' was executed %d times.", query, count));
    }

    public NPlusOneQueryException(String message, Throwable cause) {
        super(message, cause);
    }

    public NPlusOneQueryException(String message) {
        super(message);
    }

    public NPlusOneQueryException(Throwable cause) {
        super(cause);
    }
}
