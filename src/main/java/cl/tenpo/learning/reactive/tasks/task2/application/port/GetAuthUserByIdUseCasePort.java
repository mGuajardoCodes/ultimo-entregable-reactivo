package cl.tenpo.learning.reactive.tasks.task2.application.port;

import cl.tenpo.learning.reactive.tasks.task2.domain.model.AuthorizedUser;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface GetAuthUserByIdUseCasePort {
    Mono<AuthorizedUser> getByUserId(UUID userId);
}
