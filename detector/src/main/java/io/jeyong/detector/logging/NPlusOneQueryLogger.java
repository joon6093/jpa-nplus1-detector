package io.jeyong.detector.logging;

import io.jeyong.detector.context.QueryLoggingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class NPlusOneQueryLogger {

    private static final Logger logger = LoggerFactory.getLogger(NPlusOneQueryLogger.class);
    private final QueryLoggingContext queryLoggingContext;
    private final int queryThreshold;

    public NPlusOneQueryLogger(final QueryLoggingContext queryLoggingContext, final int queryThreshold) {
        this.queryLoggingContext = queryLoggingContext;
        this.queryThreshold = queryThreshold;
    }

    public void logNPlusOneIssues() {
        queryLoggingContext.getCurrentQueryOccurrences().forEach((sql, count) -> {
            if (count >= queryThreshold) {
                logger.warn("N+1 issue detected: Query '{}' was executed {} times.", sql, count);
            }
        });

        queryLoggingContext.clearLoggingContext();
    }
}
