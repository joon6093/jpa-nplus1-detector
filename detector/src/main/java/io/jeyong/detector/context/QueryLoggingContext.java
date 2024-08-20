package io.jeyong.detector.context;

import java.util.Map;

public final class QueryLoggingContext {

    private final ThreadLocal<QueryLog> currentLoggingForm = ThreadLocal.withInitial(QueryLog::new);

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
