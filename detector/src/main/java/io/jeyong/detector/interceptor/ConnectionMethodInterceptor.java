package io.jeyong.detector.interceptor;

import java.sql.Connection;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

public final class ConnectionMethodInterceptor implements MethodInterceptor {

    private static final String CLOSE_METHOD_NAME = "close";
    private final Runnable onClose;

    public ConnectionMethodInterceptor(final Runnable onClose) {
        this.onClose = onClose;
    }

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        onClose.run();
        return invocation.proceed();
    }

    public static Connection createProxy(final Connection originalConnection, final Runnable onClose) {
        final ProxyFactory proxyFactory = new ProxyFactory(originalConnection);
        proxyFactory.setInterfaces(Connection.class);
        final NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName(CLOSE_METHOD_NAME);
        final DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut,
                new ConnectionMethodInterceptor(onClose));
        proxyFactory.addAdvisor(advisor);
        return (Connection) proxyFactory.getProxy();
    }
}
