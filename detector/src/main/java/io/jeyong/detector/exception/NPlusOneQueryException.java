package io.jeyong.detector.exception;

public class NPlusOneQueryException extends RuntimeException {

    public NPlusOneQueryException(final String query, final Long count) {
        super(String.format("N+1 query detected: '%s' was executed %d times.", query, count));
    }

    public NPlusOneQueryException(final String message) {
        super(message);
    }
}
