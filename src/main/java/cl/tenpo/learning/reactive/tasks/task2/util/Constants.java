package cl.tenpo.learning.reactive.tasks.task2.util;

import java.time.Duration;

public final class Constants {

    private Constants() { }

    // Redis
    public static final String PERCENTAGE_CACHE_KEY = "external:percentage";
    public static final Duration PERCENTAGE_CACHE_TTL = Duration.ofMinutes(30);

    // Kafka
    public static final String CR_RETRY_EXHAUSTED = "CR_RETRY_EXHAUSTED";
}
