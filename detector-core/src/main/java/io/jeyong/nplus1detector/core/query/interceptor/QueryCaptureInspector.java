package io.jeyong.nplus1detector.core.query.interceptor;

import io.jeyong.nplus1detector.core.query.context.QueryContextHolder;
import org.hibernate.resource.jdbc.spi.StatementInspector;

public final class QueryCaptureInspector implements StatementInspector {

    @Override
    public String inspect(final String query) {
        QueryContextHolder.saveQuery(query);

        return query;
    }
}
