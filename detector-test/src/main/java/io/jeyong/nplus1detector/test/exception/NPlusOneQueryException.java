package io.jeyong.nplus1detector.test.exception;

public class NPlusOneQueryException extends RuntimeException {

    public NPlusOneQueryException(final String query, final Long count) {
        super(String.format("N+1 query detected: '%s' was executed %d times.", query, count));
    }
}
