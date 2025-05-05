package cl.tenpo.learning.reactive.tasks.task2.application.service;

import cl.tenpo.learning.reactive.tasks.task2.application.port.UpdateAuthUserUseCasePort;
import cl.tenpo.learning.reactive.tasks.task2.domain.model.AuthorizedUser;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.adapter.AuthorizedUserAdapter;
import cl.tenpo.learning.reactive.tasks.task2.util.ErrorHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class UpdateAuthUserUseCase implements UpdateAuthUserUseCasePort {

    private final String className = this.getClass().getSimpleName();
    private final AuthorizedUserAdapter adapter;

    @Override
    public Mono<AuthorizedUser> update(final AuthorizedUser user) {
        return Mono.just(user)
                .flatMap(req -> adapter.findById(req.userId()))
                .switchIfEmpty(Mono.defer(
                                () -> Mono.error(ErrorHandler.invalidRequest(String.valueOf(user.userId())))))
                .flatMap(r -> adapter.save(user))
                .doOnSuccess(resp -> log.info("[{}] User [{}] updated successfully", className, user))
                .doOnError(ex ->
                        log.error("[{}] Error updating user [{}] error -> [{}]",
                                className, user, ex.getMessage())
                );
    }
}
