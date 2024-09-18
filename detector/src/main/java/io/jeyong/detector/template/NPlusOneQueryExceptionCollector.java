package io.jeyong.detector.template;

import io.jeyong.detector.context.ExceptionContextHolder;
import io.jeyong.detector.test.NPlusOneQueryException;

public final class NPlusOneQueryExceptionCollector extends NPlusOneQueryTemplate {

    public NPlusOneQueryExceptionCollector(final int queryThreshold) {
        super(queryThreshold);
    }

    @Override
    protected void handleDetectedNPlusOneIssue(final String query, final Long count) {
        ExceptionContextHolder.saveException(new NPlusOneQueryException(query, count));
    }
}
