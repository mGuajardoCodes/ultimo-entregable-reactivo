package cl.tenpo.learning.reactive.tasks.task2.infrastructure.adapter;

import cl.tenpo.learning.reactive.tasks.task2.domain.model.AuthorizedUser;
import cl.tenpo.learning.reactive.tasks.task2.domain.port.AuthorizedUserPort;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.mapper.AuthorizedUserMapper;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.r2dbc.repository.AuthorizedUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizedUserAdapter implements AuthorizedUserPort {

    private final AuthorizedUserRepository repository;

    @Override
    public Mono<AuthorizedUser> save(final AuthorizedUser user) {
        return Mono.just(user)
                .map(AuthorizedUserMapper::toEntity)
                .flatMap(repository::save)
                .map(AuthorizedUserMapper::toModel);
    }

    @Override
    public Mono<AuthorizedUser> findById(final UUID userId) {
        return Mono.just(userId)
                .flatMap(repository::findByUserId)
                .map(AuthorizedUserMapper::toModel);
    }

    @Override
    // Hacer paginación para la performance
    public Flux<AuthorizedUser> findAll() {
        return repository.findAll()
                .map(AuthorizedUserMapper::toModel);
    }
}
