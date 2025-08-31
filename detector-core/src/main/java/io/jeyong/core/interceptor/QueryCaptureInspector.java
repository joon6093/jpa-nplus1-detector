package io.jeyong.core.interceptor;

import io.jeyong.core.context.QueryContextHolder;
import org.hibernate.resource.jdbc.spi.StatementInspector;

public final class QueryCaptureInspector implements StatementInspector {

    @Override
    public String inspect(final String query) {
        QueryContextHolder.saveQuery(query);

        return query;
    }
}
