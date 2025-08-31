package io.jeyong.core.query.context;

public final class QueryContextHolder {

    private static final ThreadLocal<QueryContext> queryContextThreadLocal =
            ThreadLocal.withInitial(QueryContext::new);

    private QueryContextHolder() {
    }

    public static void saveQuery(final String query) {
        queryContextThreadLocal.get().incrementQueryCount(query);
    }

    public static QueryContext getContext() {
        return queryContextThreadLocal.get();
    }

    public static void clearContext() {
        queryContextThreadLocal.remove();
    }
}
