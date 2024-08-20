package io.jeyong.detector.interceptor;

import io.jeyong.detector.context.QueryLoggingContext;
import org.hibernate.resource.jdbc.spi.StatementInspector;

public final class NPlusOneStatementInspector implements StatementInspector {

    private static final String SELECT_KEYWORD = "select";
    private final QueryLoggingContext queryLoggingContext;

    public NPlusOneStatementInspector(final QueryLoggingContext queryLoggingContext) {
        this.queryLoggingContext = queryLoggingContext;
    }

    @Override
    public String inspect(final String sql) {
        if (isSelectQuery(sql)) {
            queryLoggingContext.logQueryOccurrence(sql);
        }
        return sql;
    }

    private boolean isSelectQuery(final String sql) {
        return sql.trim().toLowerCase().startsWith(SELECT_KEYWORD);
    }
}
