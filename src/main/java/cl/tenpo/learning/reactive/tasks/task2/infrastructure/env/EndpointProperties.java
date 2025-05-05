package cl.tenpo.learning.reactive.tasks.task2.infrastructure.env;

public record EndpointProperties(String url, int retries, int retryDelay) { }
