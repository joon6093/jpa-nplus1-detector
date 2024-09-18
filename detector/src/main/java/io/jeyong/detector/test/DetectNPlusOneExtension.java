package io.jeyong.detector.test;

import io.jeyong.detector.context.ExceptionContextHolder;
import io.jeyong.detector.context.QueryContextHolder;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class DetectNPlusOneExtension implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        QueryContextHolder.clearContext();
        ExceptionContextHolder.clearContext();
    }

    @Override
    public void afterEach(final ExtensionContext context) throws Exception {
        try {
            final NPlusOneQueryException primaryException = ExceptionContextHolder.getContext().getPrimaryException();
            if (primaryException != null) {
                throw primaryException;
            }
        } finally {
            ExceptionContextHolder.clearContext();
        }
    }
}
