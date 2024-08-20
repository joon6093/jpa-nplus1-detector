package io.jeyong.detector.aop;

import io.jeyong.detector.context.LoggingContext;
import io.jeyong.detector.interceptor.ConnectionMethodInterceptor;
import java.sql.Connection;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public final class NPlusOneDetectionAop {

    private static final Logger logger = LoggerFactory.getLogger(NPlusOneDetectionAop.class);
    private final LoggingContext loggingContext;
    private final int queryThreshold;

    public NPlusOneDetectionAop(final LoggingContext loggingContext,
                                final int queryThreshold) {
        this.loggingContext = loggingContext;
        this.queryThreshold = queryThreshold;
    }

    @Around("execution(* javax.sql.DataSource.getConnection())")
    public Object wrapConnectionWithProxy(final ProceedingJoinPoint joinPoint) throws Throwable {
        final Connection originalConnection = (Connection) joinPoint.proceed();
        return ConnectionMethodInterceptor.createProxy(originalConnection, this::logNPlusOneIssues);
    }

    private void logNPlusOneIssues() {
        loggingContext.getCurrentQueryOccurrences().forEach((sql, count) -> {
            if (count >= queryThreshold) {
                logger.warn("N+1 issue detected: Query '{}' was executed {} times.", sql, count);
            }
        });

        loggingContext.clearLoggingContext();
    }
}
