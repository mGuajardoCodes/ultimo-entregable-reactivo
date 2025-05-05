package cl.tenpo.learning.reactive.tasks.task2.infrastructure.env;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "percentage-service")
public record PercentageProperties(EndpointProperties percentage) {
}
