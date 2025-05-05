package cl.tenpo.learning.reactive.tasks.task2.domain.port;

import cl.tenpo.learning.reactive.tasks.task2.domain.model.AuthorizedUser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AuthorizedUserPort {
    Mono<AuthorizedUser> save(AuthorizedUser user);
    Mono<AuthorizedUser> findById(UUID userId);
    Flux<AuthorizedUser> findAll();
}
