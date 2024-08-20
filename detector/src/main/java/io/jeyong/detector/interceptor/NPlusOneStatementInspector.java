package io.jeyong.detector.interceptor;

import io.jeyong.detector.context.LoggingContext;
import org.hibernate.resource.jdbc.spi.StatementInspector;

public final class NPlusOneStatementInspector implements StatementInspector {

    private static final String SELECT_KEYWORD = "select";
    private final LoggingContext loggingContext;

    public NPlusOneStatementInspector(final LoggingContext loggingContext) {
        this.loggingContext = loggingContext;
    }

    @Override
    public String inspect(final String sql) {
        if (isSelectQuery(sql)) {
            loggingContext.logQueryOccurrence(sql);
        }
        return sql;
    }

    private boolean isSelectQuery(final String sql) {
        return sql.trim().toLowerCase().startsWith(SELECT_KEYWORD);
    }
}
