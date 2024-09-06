package io.jeyong.detector.logging;

import io.jeyong.detector.context.QueryContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class NPlusOneQueryLogger {

    private static final Logger logger = LoggerFactory.getLogger(NPlusOneQueryLogger.class);
    private final int queryThreshold;

    public NPlusOneQueryLogger(final int queryThreshold) {
        this.queryThreshold = queryThreshold;
    }

    public void logNPlusOneIssues() {
        QueryContextHolder.getContext().getQueryCounts().forEach((query, count) -> {
            if (count >= queryThreshold) {
                logger.warn("N+1 issue detected: Query '{}' was executed {} times.", query, count);
            }
        });

        QueryContextHolder.clearContext();
    }
}
