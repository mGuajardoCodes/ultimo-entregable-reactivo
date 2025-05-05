package cl.tenpo.learning.reactive.tasks.task2.infrastructure.env;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.data.redis")
public record RedisProperties(String host, int port) { }
