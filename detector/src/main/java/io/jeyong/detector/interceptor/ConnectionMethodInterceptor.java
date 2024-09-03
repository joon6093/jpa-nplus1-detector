package io.jeyong.detector.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public final class ConnectionMethodInterceptor implements MethodInterceptor {

    private final Runnable onClose;

    public ConnectionMethodInterceptor(final Runnable onClose) {
        this.onClose = onClose;
    }

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        onClose.run();
        return invocation.proceed();
    }
}
