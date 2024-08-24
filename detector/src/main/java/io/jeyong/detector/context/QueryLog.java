package io.jeyong.detector.context;

import java.util.HashMap;
import java.util.Map;

final class QueryLog {

    private final Map<String, Long> queryOccurrences = new HashMap<>();

    void addQueryOccurrence(final String query) {
        queryOccurrences.put(query, queryOccurrences.getOrDefault(query, 0L) + 1);
    }

    public Map<String, Long> getNPlusOneQueries() {
        return Map.copyOf(queryOccurrences);
    }
}
