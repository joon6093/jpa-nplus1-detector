package io.jeyong.detector.template;

import io.jeyong.detector.context.ExceptionContext;
import io.jeyong.detector.exception.NPlusOneQueryException;

public final class NPlusOneQueryCollector extends NPlusOneQueryTemplate {

    private final ExceptionContext exceptionContext;

    public NPlusOneQueryCollector(final int queryThreshold, final ExceptionContext exceptionContext) {
        super(queryThreshold);
        this.exceptionContext = exceptionContext;
    }

    @Override
    protected void handleDetectedNPlusOneIssue(final String query, final Long count) {
        exceptionContext.saveException(new NPlusOneQueryException(query, count));
    }
}
