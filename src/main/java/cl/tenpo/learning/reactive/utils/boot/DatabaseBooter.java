package cl.tenpo.learning.reactive.utils.boot;

import io.r2dbc.spi.Connection;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.r2dbc.connection.init.ScriptUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class DatabaseBooter {

    private final ResourceLoader resourceLoader;
    private final R2dbcEntityTemplate r2dbcTemplate;
    private final ReactiveRedisTemplate<String, Object> redisTemplate;

    @PostConstruct
    public void initSql() {
        Publisher<? extends Connection> publisher = r2dbcTemplate.getDatabaseClient()
                .getConnectionFactory()
                .create();
        Mono.from(publisher)
                .flatMap(connection -> ScriptUtils.executeSqlScript(connection, resourceLoader.getResource("classpath:db/init.sql")))
                .subscribe();
    }


    @PostConstruct
    public void initRedis() {
        redisTemplate.opsForValue()
                .set("up", true)
                .subscribe();
    }

}
