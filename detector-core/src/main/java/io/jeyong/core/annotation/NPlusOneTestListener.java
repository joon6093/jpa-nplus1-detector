package io.jeyong.core.annotation;

import io.jeyong.core.context.ExceptionContext;
import io.jeyong.core.context.QueryContextHolder;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public final class NPlusOneTestListener extends AbstractTestExecutionListener {

    public static final int ORDER = Integer.MIN_VALUE;

    @Override
    public int getOrder() {
        return ORDER;
    }

    @Override
    public void beforeTestMethod(final TestContext testContext) {
        QueryContextHolder.clearContext();

        final ExceptionContext exceptionContext = getExceptionContext(testContext.getApplicationContext());
        if (exceptionContext != null) {
            exceptionContext.clearContext();
        }
    }

    @Override
    public void afterTestMethod(final TestContext testContext) {
        final ExceptionContext exceptionContext = getExceptionContext(testContext.getApplicationContext());
        if (exceptionContext == null) {
            return;
        }

        try {
            exceptionContext.getContext().ifPresent(exception -> {
                throw exception;
            });
        } finally {
            exceptionContext.clearContext();
        }
    }

    private ExceptionContext getExceptionContext(final ApplicationContext applicationContext) {
        try {
            return applicationContext.getBean(ExceptionContext.class);
        } catch (final BeansException e) {
            return null;
        }
    }
}
