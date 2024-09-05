package io.jeyong.detector.interceptor;

import io.jeyong.detector.context.QueryContextHolder;
import org.hibernate.resource.jdbc.spi.StatementInspector;

public final class QueryStatementInspector implements StatementInspector {

    private static final String SELECT_KEYWORD = "select";
    private static final String IN_CLAUSE_KEYWORD = " in(";

    @Override
    public String inspect(final String query) {
        if (isSelectQuery(query) && !isBatchSizeQuery(query)) {
            QueryContextHolder.saveQuery(query);
        }
        return query;
    }

    private boolean isSelectQuery(final String query) {
        return query.stripLeading().toLowerCase().startsWith(SELECT_KEYWORD);
    }

    private boolean isBatchSizeQuery(final String query) {
        return query.toLowerCase().contains(IN_CLAUSE_KEYWORD);
    }
}
