package io.jeyong.detector.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class RequestLogDto {

    private final Map<String, Long> queryOccurrences = new HashMap<>();

    public void addQueryOccurrence(final String query) {
        queryOccurrences.put(query, queryOccurrences.getOrDefault(query, 0L) + 1);
    }

    public Map<String, Long> getNPlusOneQueries() {
        return queryOccurrences.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
