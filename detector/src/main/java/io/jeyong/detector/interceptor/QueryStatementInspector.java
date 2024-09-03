package io.jeyong.detector.interceptor;

import io.jeyong.detector.context.QueryContextHolder;
import org.hibernate.resource.jdbc.spi.StatementInspector;

public final class QueryStatementInspector implements StatementInspector {

    private static final String SELECT_KEYWORD = "select";
    private static final String IN_CLAUSE_KEYWORD = " in(";

    @Override
    public String inspect(final String sql) {
        if (isSelectQuery(sql) && !isBatchSizeQuery(sql)) {
            QueryContextHolder.saveQuery(sql);
        }
        return sql;
    }

    private boolean isSelectQuery(final String sql) {
        return sql.trim().toLowerCase().startsWith(SELECT_KEYWORD);
    }

    private boolean isBatchSizeQuery(final String sql) {
        return sql.toLowerCase().contains(IN_CLAUSE_KEYWORD);
    }
}
