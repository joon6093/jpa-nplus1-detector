package io.jeyong.detector.interceptor;

import java.sql.Connection;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;

public final class ConnectionMethodInterceptor implements MethodInterceptor {

    private static final String CLOSE_METHOD_NAME = "close";
    private final Runnable onClose;

    public ConnectionMethodInterceptor(final Runnable onClose) {
        this.onClose = onClose;
    }

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        if (CLOSE_METHOD_NAME.equals(invocation.getMethod().getName())) {
            onClose.run();
        }
        return invocation.proceed();
    }

    public static Connection createProxy(final Connection originalConnection, final Runnable onClose) {
        final ProxyFactory proxyFactory = new ProxyFactory(originalConnection);
        proxyFactory.setInterfaces(Connection.class);
        proxyFactory.addAdvice(new ConnectionMethodInterceptor(onClose));
        return (Connection) proxyFactory.getProxy();
    }
}
