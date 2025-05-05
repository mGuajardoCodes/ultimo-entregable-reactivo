package cl.tenpo.learning.reactive.tasks.task2.infrastructure.adapter;

import cl.tenpo.learning.reactive.tasks.task2.domain.model.AuthorizedUser;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.r2dbc.entity.AuthorizedUserEntity;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.r2dbc.repository.AuthorizedUserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorizedUserAdapterTest {

    @Mock
    private AuthorizedUserRepository repository;

    @InjectMocks
    private AuthorizedUserAdapter adapter;

    private final UUID userId = UUID.randomUUID();

    private final AuthorizedUser user = new AuthorizedUser(
            userId,
            "Marcelo",
            "Guajardo",
            "marcelo.guajardo@tenpo.cl",
            "ADMIN",
            1L
    );
    private final AuthorizedUserEntity entity = new AuthorizedUserEntity(
            1L,
            userId,
            "Marcelo",
            "Guajardo",
            "marcelo.guajardo@tenpo.cl",
            "ADMIN",
            1L
    );;

    @Nested
    class SaveMethod {
        @Test
        void shouldSaveAuthorizedUserSuccessfully() {
            when(repository.save(any(AuthorizedUserEntity.class)))
                    .thenReturn(Mono.just(entity));

            StepVerifier.create(adapter.save(user))
                    .expectNextMatches(savedUser -> savedUser.userId().equals(user.userId()))
                    .verifyComplete();

            verify(repository).save(any(AuthorizedUserEntity.class));
        }
    }

    @Nested
    class FindByIdMethod {
        @Test
        void shouldReturnAuthorizedUserById() {
            when(repository.findByUserId(user.userId())).thenReturn(Mono.just(entity));

            StepVerifier.create(adapter.findById(user.userId()))
                    .expectNextMatches(result -> result.userId().equals(user.userId()))
                    .verifyComplete();

            verify(repository).findByUserId(user.userId());
        }
    }

    @Nested
    class FindAllMethod {
        @Test
        void shouldReturnAllAuthorizedUsers() {
            AuthorizedUserEntity anotherUser = new AuthorizedUserEntity(
                    2L,
                    UUID.randomUUID(),
                    "Marcelo",
                    "Guajardo",
                    "marcelo.guajardo@tenpo.cl",
                    "OPERATION",
                    1L
            );

            when(repository.findAll()).thenReturn(Flux.just(entity, anotherUser));

            StepVerifier.create(adapter.findAll())
                    .expectNextCount(2)
                    .verifyComplete();

            verify(repository).findAll();
        }
    }
}
