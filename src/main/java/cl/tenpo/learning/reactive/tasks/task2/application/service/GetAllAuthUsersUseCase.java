package cl.tenpo.learning.reactive.tasks.task2.application.service;

import cl.tenpo.learning.reactive.tasks.task2.application.port.GetAllAuthUsersUseCasePort;
import cl.tenpo.learning.reactive.tasks.task2.domain.model.AuthorizedUser;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.adapter.AuthorizedUserAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetAllAuthUsersUseCase implements GetAllAuthUsersUseCasePort {

    private final String className = this.getClass().getSimpleName();
    private final AuthorizedUserAdapter adapter;

    @Override
    public Flux<AuthorizedUser> listAll() {
        return adapter.findAll()
                .doOnError(ex ->
                        log.error("[{}] Error retrieving all auth users error -> [{}]", className, ex.getMessage())
                );
    }
}
