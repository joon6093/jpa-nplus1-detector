package io.jeyong.nplus1detector.core.template;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

public final class NPlusOneQueryLogger extends NPlusOneQueryTemplate {

    private static final Logger logger = LoggerFactory.getLogger(NPlusOneQueryLogger.class);
    private final Level level;

    public NPlusOneQueryLogger(final int threshold, final List<String> exclude, final Level level) {
        super(threshold, exclude);
        this.level = level;
    }

    @Override
    protected void handleDetectedNPlusOneQuery(final String query, final Long count) {
        if (level == Level.WARN && logger.isWarnEnabled()) {
            logger.warn("N+1 query detected: '{}' was executed {} times.", query, count);
        } else if (level == Level.TRACE && logger.isTraceEnabled()) {
            logger.trace("N+1 query detected: '{}' was executed {} times.", query, count);
        } else if (level == Level.INFO && logger.isInfoEnabled()) {
            logger.info("N+1 query detected: '{}' was executed {} times.", query, count);
        } else if (level == Level.DEBUG && logger.isDebugEnabled()) {
            logger.debug("N+1 query detected: '{}' was executed {} times.", query, count);
        } else if (level == Level.ERROR && logger.isErrorEnabled()) {
            logger.error("N+1 query detected: '{}' was executed {} times.", query, count);
        }
    }
}
