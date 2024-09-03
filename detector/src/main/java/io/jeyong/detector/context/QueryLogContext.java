package io.jeyong.detector.context;

import java.util.HashMap;
import java.util.Map;

final class QueryLogContext {

    private final Map<String, Long> queryExecutionCounts = new HashMap<>();

    void incrementQueryCount(final String query) {
        queryExecutionCounts.put(query, queryExecutionCounts.getOrDefault(query, 0L) + 1);
    }

    Map<String, Long> getLoggedQueries() {
        return Map.copyOf(queryExecutionCounts);
    }
}
