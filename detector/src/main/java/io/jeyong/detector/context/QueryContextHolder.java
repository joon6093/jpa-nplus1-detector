package io.jeyong.detector.context;

import java.util.Map;

public final class QueryContextHolder {

    private static final ThreadLocal<QueryContext> queryLogThreadLocal =
            ThreadLocal.withInitial(QueryContext::new);

    private QueryContextHolder() {
    }

    public static void saveQuery(final String query) {
        queryLogThreadLocal.get().incrementQueryCount(query);
    }

    public static Map<String, Long> getSavedQueries() {
        return queryLogThreadLocal.get().getQueryCounts();
    }

    public static void clearSavedQueries() {
        queryLogThreadLocal.remove();
    }
}
