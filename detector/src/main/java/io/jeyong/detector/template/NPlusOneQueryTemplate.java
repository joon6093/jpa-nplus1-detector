package io.jeyong.detector.template;

import io.jeyong.detector.context.QueryContextHolder;

public abstract class NPlusOneQueryTemplate {

    private final int queryThreshold;

    public NPlusOneQueryTemplate(final int queryThreshold) {
        this.queryThreshold = queryThreshold;
    }

    public final void handleQueryContext() {
        try {
            QueryContextHolder.getContext().getQueryCounts().forEach((query, count) -> {
                if (count >= queryThreshold) {
                if (isSelectQuery(query) && !isBatchSizeQuery(query) && isExceedingThreshold(count)) {
                    handleDetectedNPlusOneQuery(query, count);
                }
            });
        } finally {
            QueryContextHolder.clearContext();
        }
    }

    private boolean isSelectQuery(final String query) {
        return query.stripLeading().startsWith(SELECT_KEYWORD);
    }

    private boolean isBatchSizeQuery(final String query) {
        return query.contains(IN_CLAUSE_KEYWORD);
    }

    protected abstract void handleDetectedNPlusOneQuery(final String query, final Long count);
}
