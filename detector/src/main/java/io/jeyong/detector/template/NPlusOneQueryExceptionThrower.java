package io.jeyong.detector.template;

import io.jeyong.detector.test.NPlusOneQueryException;

public final class NPlusOneQueryExceptionThrower extends NPlusOneQueryTemplate {

    public NPlusOneQueryExceptionThrower(final int queryThreshold) {
        super(queryThreshold);
    }

    @Override
    protected void handleDetectedNPlusOneIssue(final String query, final Long count) {
        throw new NPlusOneQueryException(query, count);
    }
}
