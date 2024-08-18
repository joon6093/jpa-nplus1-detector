package io.jeyong.detector.interceptor;

import io.jeyong.detector.context.LoggingContext;
import io.jeyong.detector.dto.RequestLogDto;
import org.hibernate.resource.jdbc.spi.StatementInspector;

public final class NPlusOneStatementInspector implements StatementInspector {

    private final LoggingContext loggingContext;

    public NPlusOneStatementInspector(final LoggingContext loggingContext) {
        this.loggingContext = loggingContext;
    }

    @Override
    public String inspect(final String sql) {
        if (sql.toLowerCase().contains("select")) {
            final RequestLogDto logDto = loggingContext.getCurrentLoggingForm();
            logDto.addQueryOccurrence(sql);
        }
        return sql;
    }
}
