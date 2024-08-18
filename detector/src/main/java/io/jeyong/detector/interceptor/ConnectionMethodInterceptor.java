package io.jeyong.detector.interceptor;

import java.sql.Connection;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;

public class ConnectionMethodInterceptor implements MethodInterceptor {

    private static final String CLOSE_METHOD_NAME = "close";

    private final Runnable onClose;

    public ConnectionMethodInterceptor(Runnable onClose) {
        this.onClose = onClose;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (CLOSE_METHOD_NAME.equals(invocation.getMethod().getName())) {
            onClose.run();
        }
        return invocation.proceed();
    }

    public static Connection createProxy(Connection originalConnection, Runnable onClose) {
        ProxyFactory proxyFactory = new ProxyFactory(originalConnection);
        proxyFactory.setInterfaces(Connection.class);
        proxyFactory.addAdvice(new ConnectionMethodInterceptor(onClose));
        return (Connection) proxyFactory.getProxy();
    }
}
