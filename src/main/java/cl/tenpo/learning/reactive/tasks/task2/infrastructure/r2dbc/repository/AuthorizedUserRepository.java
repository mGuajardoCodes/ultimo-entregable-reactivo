package cl.tenpo.learning.reactive.tasks.task2.infrastructure.r2dbc.repository;

import cl.tenpo.learning.reactive.tasks.task2.infrastructure.r2dbc.entity.AuthorizedUserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AuthorizedUserRepository extends R2dbcRepository<AuthorizedUserEntity, Long> {
    Mono<AuthorizedUserEntity> findByUserId(final UUID userId);
}
