package io.jeyong.detector.aop;

import io.jeyong.detector.context.LoggingContext;
import io.jeyong.detector.dto.RequestLogDto;
import io.jeyong.detector.interceptor.ConnectionProxyInterceptor;
import org.aspectj.lang.annotation.AfterReturning;
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
    public Object interceptConnectionCreation(final org.aspectj.lang.ProceedingJoinPoint joinPoint) throws Throwable {
        final Object connection = joinPoint.proceed();
        return new ConnectionProxyInterceptor(connection, loggingContext.getCurrentLoggingForm()).createProxy();
    }

    @AfterReturning("execution(@org.springframework.transaction.annotation.Transactional * *(..))")
    public void logNPlusOneIssuesAfterTransaction() {
        final RequestLogDto logDto = loggingContext.getCurrentLoggingForm();

        logDto.getNPlusOneQueries().forEach((sql, count) -> {
            logger.warn("N+1 issue detected: Query '{}' was executed {} times.", sql, count);
        });

        loggingContext.clearLoggingContext();
    }
}
