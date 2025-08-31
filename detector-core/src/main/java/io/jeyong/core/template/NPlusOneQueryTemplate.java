package io.jeyong.core.template;

import io.jeyong.core.query.context.QueryContextHolder;
import java.util.List;

public abstract class NPlusOneQueryTemplate {

    private static final String SELECT_KEYWORD = "select";
    private static final String IN_CLAUSE_KEYWORD = " in(";

    private final int threshold;
    private final List<String> exclude;

    public NPlusOneQueryTemplate(final int threshold, final List<String> exclude) {
        this.threshold = threshold;
        this.exclude = exclude;
    }

    public final void handleQueryContext() {
        try {
            QueryContextHolder.getContext().getQueryCounts().forEach((query, count) -> {
                if (isValidQuery(query, count)) {
                    handleDetectedNPlusOneQuery(query, count);
                }
            });
        } finally {
            QueryContextHolder.clearContext();
        }
    }

    private boolean isValidQuery(final String query, final Long count) {
        return isSelectQuery(query)
                && !isBatchSizeQuery(query)
                && isExceedingThreshold(count)
                && !isExcludedQuery(query);
    }

    private boolean isSelectQuery(final String query) {
        return query.startsWith(SELECT_KEYWORD);
    }

    private boolean isBatchSizeQuery(final String query) {
        return query.contains(IN_CLAUSE_KEYWORD);
    }

    private boolean isExceedingThreshold(final Long count) {
        return count >= threshold;
    }

    private boolean isExcludedQuery(final String query) {
        return exclude.contains(query);
    }

    protected abstract void handleDetectedNPlusOneQuery(final String query, final Long count);
}
