package io.jeyong.detector.aop;

import io.jeyong.detector.context.LoggingContext;
import io.jeyong.detector.dto.RequestLogDto;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public final class NPlusOneDetectionAop {

    private static final Logger logger = LoggerFactory.getLogger(NPlusOneDetectionAop.class);
    private final LoggingContext loggingContext;

    public NPlusOneDetectionAop(final LoggingContext loggingContext) {
        this.loggingContext = loggingContext;
    }

    @AfterReturning("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void logNPlusOneIssuesAfterTransaction() {
        final RequestLogDto logDto = loggingContext.getCurrentLoggingForm();

        logDto.getNPlusOneQueries().forEach((sql, count) -> {
            logger.warn("N+1 issue detected: Query '{}' was executed {} times.", sql, count);
        });

        loggingContext.clearLoggingContext();
    }
}
