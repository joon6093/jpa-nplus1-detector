package io.jeyong.detector.interceptor;

import io.jeyong.detector.dto.RequestLogDto;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.lang.reflect.Method;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;

public final class ConnectionProxyInterceptor implements MethodInterceptor {

    private static final String JDBC_PREPARE_STATEMENT_METHOD_NAME = "prepareStatement";

    private final Object connection;
    private final RequestLogDto requestLogDto;

    public ConnectionProxyInterceptor(final Object connection, final RequestLogDto requestLogDto) {
        this.connection = connection;
        this.requestLogDto = requestLogDto;
    }

    @Nullable
    @Override
    public Object invoke(@Nonnull final MethodInvocation invocation) throws Throwable {
        final Object result = invocation.proceed();

        if (isConnectionValid(result) && isPrepareStatementMethod(invocation)) {
            final ProxyFactory proxyFactory = new ProxyFactory(result);
            proxyFactory.addAdvice(new PreparedStatementProxyInterceptor(requestLogDto));
            return proxyFactory.getProxy();
        }

        return result;
    }

    private boolean isConnectionValid(final Object result) {
        return result != null;
    }

    private boolean isPrepareStatementMethod(final MethodInvocation invocation) {
        final Object targetObject = invocation.getThis();
        if (targetObject == null) {
            return false;
        }
        final Method targetMethod = invocation.getMethod();
        return targetMethod.getName().equals(JDBC_PREPARE_STATEMENT_METHOD_NAME);
    }

    public Object createProxy() {
        final ProxyFactory proxyFactory = new ProxyFactory(connection);
        proxyFactory.addAdvice(this);
        return proxyFactory.getProxy();
    }
}
