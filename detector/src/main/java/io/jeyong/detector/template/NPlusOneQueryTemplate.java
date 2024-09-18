package io.jeyong.detector.template;

import io.jeyong.detector.context.QueryContextHolder;

public abstract class NPlusOneQueryTemplate {

    private final int queryThreshold;

    public NPlusOneQueryTemplate(final int queryThreshold) {
        this.queryThreshold = queryThreshold;
    }

    public final void handleNPlusOneIssues() {
        try {
            QueryContextHolder.getContext().getQueryCounts().forEach((query, count) -> {
                if (count >= queryThreshold) {
                    handleDetectedNPlusOneIssue(query, count);
                }
            });
        } finally {
            QueryContextHolder.clearContext();
        }
    }

    protected abstract void handleDetectedNPlusOneIssue(final String query, final Long count);
}
