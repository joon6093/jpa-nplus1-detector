package io.jeyong.detector.template;

import io.jeyong.detector.context.QueryContextHolder;
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

    private boolean isExceedingThreshold(final Long count) {
        return count >= threshold;
    }

    protected abstract void handleDetectedNPlusOneQuery(final String query, final Long count);
}
