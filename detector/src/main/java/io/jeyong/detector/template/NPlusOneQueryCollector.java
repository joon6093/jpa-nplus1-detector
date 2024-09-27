package io.jeyong.detector.template;

import io.jeyong.detector.context.ExceptionContext;
import io.jeyong.detector.exception.NPlusOneQueryException;
import java.util.List;

public final class NPlusOneQueryCollector extends NPlusOneQueryTemplate {

    private final ExceptionContext exceptionContext;

    public NPlusOneQueryCollector(final int threshold, final List<String> exclude,
                                  final ExceptionContext exceptionContext) {
        super(threshold, exclude);
        this.exceptionContext = exceptionContext;
    }

    @Override
    protected void handleDetectedNPlusOneQuery(final String query, final Long count) {
        exceptionContext.saveException(new NPlusOneQueryException(query, count));
    }
}
