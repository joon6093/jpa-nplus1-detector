package io.jeyong.detector.test;

import io.jeyong.detector.context.ExceptionContext;
import io.jeyong.detector.context.QueryContextHolder;
import java.util.Optional;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class DetectNPlusOneExtension implements BeforeAllCallback, BeforeEachCallback, AfterEachCallback {

    private ExceptionContext exceptionContext;

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        ApplicationContext applicationContext = SpringExtension.getApplicationContext(extensionContext);
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
    public void beforeEach(final ExtensionContext extensionContext) throws Exception {
        QueryContextHolder.clearContext();
        getOptionalExceptionContext().ifPresent(ExceptionContext::clearException);
    }

    @Override
    public void afterEach(final ExtensionContext extensionContext) throws Exception {
        getOptionalExceptionContext().ifPresent(context -> {
            try {
                NPlusOneQueryException primaryException = context.getException();
                if (primaryException != null) {
                    throw primaryException;
                }
            } finally {
                context.clearException();
            }
        });
    }

    private Optional<ExceptionContext> getOptionalExceptionContext() {
        return Optional.ofNullable(exceptionContext);
    }
}
