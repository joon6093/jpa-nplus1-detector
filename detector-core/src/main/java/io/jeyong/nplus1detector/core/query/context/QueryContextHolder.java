package io.jeyong.nplus1detector.core.query.context;

import org.springframework.core.NamedThreadLocal;

public final class QueryContextHolder {

    private static final ThreadLocal<QueryContext> queryContext =
            new NamedThreadLocal<>("NPlus1Detector query context") {
                @Override
                protected QueryContext initialValue() {
                    return new QueryContext();
                }
            };

    private QueryContextHolder() {
    }

    public static void saveQuery(final String query) {
        queryContext.get().incrementQueryCount(query);
    }

    public static QueryContext getContext() {
        return queryContext.get();
    }

    public static void clearContext() {
        queryContext.remove();
    }
}
