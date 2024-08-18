package io.jeyong.detector.aop;

import io.jeyong.detector.context.LoggingContext;
import io.jeyong.detector.dto.RequestLogDto;
import io.jeyong.detector.interceptor.ConnectionMethodInterceptor;
import java.sql.Connection;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public final class NPlusOneDetectionAop {

    private static final Logger logger = LoggerFactory.getLogger(NPlusOneDetectionAop.class);
    private final LoggingContext loggingContext;

    public NPlusOneDetectionAop(final LoggingContext loggingContext) {
        this.loggingContext = loggingContext;
    }

    @Around("execution(* javax.sql.DataSource.getConnection())")
    public Object wrapConnectionWithProxy(ProceedingJoinPoint joinPoint) throws Throwable {
        Connection originalConnection = (Connection) joinPoint.proceed();

        return ConnectionMethodInterceptor.createProxy(originalConnection, this::logNPlusOneIssues);
    }

    private void logNPlusOneIssues() {
        final RequestLogDto logDto = loggingContext.getCurrentLoggingForm();

        logDto.getNPlusOneQueries().forEach((sql, count) -> {
            logger.warn("N+1 issue detected: Query '{}' was executed {} times.", sql, count);
        });

        loggingContext.clearLoggingContext();
    }
}
