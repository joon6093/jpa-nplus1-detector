package io.jeyong.detector.test;

import io.jeyong.detector.context.QueryContextHolder;
import io.jeyong.detector.template.NPlusOneQueryExceptionThrower;
import io.jeyong.detector.template.NPlusOneQueryTemplate;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class DetectNPlusOneExtension implements BeforeEachCallback, AfterEachCallback {

    private final NPlusOneQueryTemplate nPlusOneQueryTemplate;

    public DetectNPlusOneExtension() {
        this(2);
    }

    public DetectNPlusOneExtension(int queryThreshold) {
        this.nPlusOneQueryTemplate = new NPlusOneQueryExceptionThrower(queryThreshold);
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        QueryContextHolder.clearContext();
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        nPlusOneQueryTemplate.handleNPlusOneIssues();
    }
}
