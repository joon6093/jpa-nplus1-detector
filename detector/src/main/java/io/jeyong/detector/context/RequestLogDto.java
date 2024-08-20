package io.jeyong.detector.context;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class RequestLogDto {

    private final Map<String, Long> queryOccurrences = new HashMap<>();

    void addQueryOccurrence(final String query) {
        queryOccurrences.put(query, queryOccurrences.getOrDefault(query, 0L) + 1);
    }

    public Map<String, Long> getNPlusOneQueries() {
        // Using Collections.unmodifiableMap for Java 8 compatibility
        return Collections.unmodifiableMap(queryOccurrences);
    }
}
