package cl.tenpo.learning.reactive.tasks.task2.application.service;

import cl.tenpo.learning.reactive.tasks.task2.application.port.CreateAuthUserUseCasePort;
import cl.tenpo.learning.reactive.tasks.task2.domain.model.AuthorizedUser;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.adapter.AuthorizedUserAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateAuthUserUseCase implements CreateAuthUserUseCasePort {

    private final String className = this.getClass().getSimpleName();
    private final AuthorizedUserAdapter adapter;

    @Override
    public Mono<AuthorizedUser> create(final AuthorizedUser user) {
        return Mono.just(user)
                .doOnSubscribe(s -> log.info("[{}] Creating user [{}]", className, user))
                .flatMap(adapter::save)
                .doOnSuccess(resp -> log.info("[{}] User [{}] created successfully", className, user))
                .doOnError(ex ->
                        log.error("[{}] Error creating user [{}] error -> [{}]",
                        className, user, ex.getMessage())
                );
    }
}
