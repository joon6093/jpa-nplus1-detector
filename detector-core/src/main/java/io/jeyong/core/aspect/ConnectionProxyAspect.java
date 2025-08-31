package io.jeyong.core.aspect;

import io.jeyong.core.aspect.factory.ConnectionProxyFactory;
import io.jeyong.core.template.NPlusOneQueryTemplate;
import java.sql.Connection;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public final class ConnectionProxyAspect {

    private final NPlusOneQueryTemplate nPlusOneQueryTemplate;

    public ConnectionProxyAspect(final NPlusOneQueryTemplate nPlusOneQueryTemplate) {
        this.nPlusOneQueryTemplate = nPlusOneQueryTemplate;
    }

    @Around("execution(* javax.sql.DataSource.getConnection())")
    public Object wrapConnectionWithProxy(final ProceedingJoinPoint joinPoint) throws Throwable {
        final Connection originalConnection = (Connection) joinPoint.proceed();

        return ConnectionProxyFactory.createProxy(originalConnection, nPlusOneQueryTemplate::handleQueryContext);
    }
}
