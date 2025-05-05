package cl.tenpo.learning.reactive.tasks.task2.infrastructure.adapter;

import cl.tenpo.learning.reactive.tasks.task2.domain.port.CachePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisCacheAdapter implements CachePort {

    private final ReactiveRedisTemplate<String, Object> redis;

    @Override
    public Mono<Void> put(String key, Object value, Duration ttl) {
        return redis
                .opsForValue()
                .set(key, value, ttl)
                .then();
    }

    @Override
    public <T> Mono<T> get(String key, Class<T> type) {
        return redis
                .opsForValue()
                .get(key)
                .cast(type);
    }

    @Override
    public Mono<Boolean> exists(String key) {
        return redis
                .hasKey(key);
    }

    @Override
    public Mono<Void> delete(String key) {
        return redis
                .opsForValue()
                .delete(key)
                .then();
    }
}