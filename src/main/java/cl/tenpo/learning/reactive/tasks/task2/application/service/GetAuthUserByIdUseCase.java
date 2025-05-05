package cl.tenpo.learning.reactive.tasks.task2.application.service;

import cl.tenpo.learning.reactive.tasks.task2.application.port.GetAuthUserByIdUseCasePort;
import cl.tenpo.learning.reactive.tasks.task2.domain.model.AuthorizedUser;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.adapter.AuthorizedUserAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class GetAuthUserByIdUseCase implements GetAuthUserByIdUseCasePort {

    private final String className = this.getClass().getSimpleName();
    private final AuthorizedUserAdapter adapter;

    @Override
    public Mono<AuthorizedUser> getByUserId(final UUID userId) {

        return Mono.just(userId)
                .doOnSubscribe(s -> log.info("[{}] Getting user by userId [{}]", className, userId))
                .flatMap(adapter::findById)
                .doOnError(ex ->
                        log.error("[{}] Error getting user by id [{}] error -> [{}]",
                                className, userId, ex.getMessage())
                );
    }
}
