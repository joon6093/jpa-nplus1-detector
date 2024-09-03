package io.jeyong.detector.context;

import java.util.Map;

public final class QueryLogContextHolder {

    private static final ThreadLocal<QueryLogContext> queryLogThreadLocal =
            ThreadLocal.withInitial(QueryLogContext::new);

    private QueryLogContextHolder() {
    }

    public static void logQuery(final String query) {
        queryLogThreadLocal.get().incrementQueryCount(query);
    }

    public static Map<String, Long> getCurrentQueryLog() {
        return queryLogThreadLocal.get().getLoggedQueries();
    }

    public static void clearQueryLog() {
        queryLogThreadLocal.remove();
    }
}
