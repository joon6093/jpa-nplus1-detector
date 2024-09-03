package io.jeyong.detector.aop;

import io.jeyong.detector.factory.ConnectionProxyFactory;
import io.jeyong.detector.logging.NPlusOneQueryLogger;
import java.sql.Connection;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public final class NPlusOneDetectionAspect {

    private final NPlusOneQueryLogger nPlusOneQueryLogger;

    public NPlusOneDetectionAspect(final NPlusOneQueryLogger nPlusOneQueryLogger) {
        this.nPlusOneQueryLogger = nPlusOneQueryLogger;
    }

    @Around("execution(* javax.sql.DataSource.getConnection())")
    public Object wrapConnectionWithProxy(final ProceedingJoinPoint joinPoint) throws Throwable {
        final Connection originalConnection = (Connection) joinPoint.proceed();
        return ConnectionProxyFactory.createProxy(originalConnection, nPlusOneQueryLogger::logNPlusOneIssues);
    }
}
