package cl.tenpo.learning.reactive.tasks.task2.domain.port;

import reactor.core.publisher.Mono;

import java.time.Duration;

public interface CachePort {
    Mono<Void> put(String key, Object value, Duration ttl);
    <T> Mono<T> get(String key, Class<T> type);
    Mono<Boolean> exists(String key);
    Mono<Void> delete(String key);
}
