package io.jeyong.detector.context;

import java.util.Map;

public final class LoggingContext {

    private final ThreadLocal<RequestLogDto> currentLoggingForm = ThreadLocal.withInitial(RequestLogDto::new);

    public void logQueryOccurrence(final String query) {
        currentLoggingForm.get().addQueryOccurrence(query);
    }
    
    public Map<String, Long> getCurrentQueryOccurrences() {
        return currentLoggingForm.get().getNPlusOneQueries();
    }

    public void clearLoggingContext() {
        currentLoggingForm.remove();
    }
}
