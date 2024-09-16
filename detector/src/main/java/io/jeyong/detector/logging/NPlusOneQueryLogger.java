package io.jeyong.detector.logging;

import io.jeyong.detector.context.QueryContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

public final class NPlusOneQueryLogger {

    private static final Logger logger = LoggerFactory.getLogger(NPlusOneQueryLogger.class);
    private final int queryThreshold;
    private final Level level;

    public NPlusOneQueryLogger(final int queryThreshold, final Level level) {
        this.queryThreshold = queryThreshold;
        this.level = level;
    }

    public void logNPlusOneIssues() {
        try {
            QueryContextHolder.getContext().getQueryCounts().forEach((query, count) -> {
                if (count >= queryThreshold) {
                    logBasedOnLevel(query, count);
                }
            });
        } finally {
            QueryContextHolder.clearContext();
        }
    }

    private void logBasedOnLevel(String query, long count) {
        if (level == Level.WARN && logger.isWarnEnabled()) {
            logger.warn("N+1 issue detected: '{}' was executed {} times.", query, count);
        } else if (level == Level.TRACE && logger.isTraceEnabled()) {
            logger.trace("N+1 issue detected: '{}' was executed {} times.", query, count);
        } else if (level == Level.INFO && logger.isInfoEnabled()) {
            logger.info("N+1 issue detected: '{}' was executed {} times.", query, count);
        } else if (level == Level.DEBUG && logger.isDebugEnabled()) {
            logger.debug("N+1 issue detected: '{}' was executed {} times.", query, count);
        } else if (level == Level.ERROR && logger.isErrorEnabled()) {
            logger.error("N+1 issue detected: '{}' was executed {} times.", query, count);
        }
    }
}
