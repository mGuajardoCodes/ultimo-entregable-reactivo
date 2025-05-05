package cl.tenpo.learning.reactive.tasks.task2.infrastructure.mapper;

import com.fasterxml.jackson.databind.JsonNode;

public final class CalculateMapper {

    private CalculateMapper() { }

    public static Double responseToDouble(final JsonNode node) {
        return Double.parseDouble(node.path("percentage").asText().replace(',', '.'));
    }
}
