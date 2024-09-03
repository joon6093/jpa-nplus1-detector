package io.jeyong.detector.factory;

import io.jeyong.detector.interceptor.ConnectionMethodInterceptor;
import java.sql.Connection;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

public final class ConnectionProxyFactory {

    private static final String CLOSE_METHOD_NAME = "close";

    private ConnectionProxyFactory() {
    }

    public static Connection createProxy(final Connection originalConnection, final Runnable onClose) {
        ProxyFactory proxyFactory = new ProxyFactory(originalConnection);
        proxyFactory.setInterfaces(Connection.class);

        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName(CLOSE_METHOD_NAME);

        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new ConnectionMethodInterceptor(onClose));
        proxyFactory.addAdvisor(advisor);

        return (Connection) proxyFactory.getProxy();
    }
}
