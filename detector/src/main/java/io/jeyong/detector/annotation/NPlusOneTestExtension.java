package io.jeyong.detector.annotation;

import io.jeyong.detector.context.ExceptionContext;
import io.jeyong.detector.context.QueryContextHolder;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public final class NPlusOneTestExtension implements BeforeAllCallback, BeforeEachCallback, AfterEachCallback {

    private ExceptionContext exceptionContext;

    @Override
    public void beforeAll(final ExtensionContext extensionContext) {
        final ApplicationContext applicationContext = SpringExtension.getApplicationContext(extensionContext);
        exceptionContext = getExceptionContext(applicationContext);
    }

    private ExceptionContext getExceptionContext(final ApplicationContext applicationContext) {
        try {
            return applicationContext.getBean(ExceptionContext.class);
        } catch (BeansException e) {
            return null;
        }
    }

    @Override
    public void beforeEach(final ExtensionContext extensionContext) {
        QueryContextHolder.clearContext();
        if (exceptionContext != null) {
            exceptionContext.clearContext();
        }
    }

    @Override
    public void afterEach(final ExtensionContext extensionContext) {
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
}
