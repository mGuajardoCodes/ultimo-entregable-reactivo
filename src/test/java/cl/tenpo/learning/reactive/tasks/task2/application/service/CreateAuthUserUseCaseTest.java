package cl.tenpo.learning.reactive.tasks.task2.application.service;

import cl.tenpo.learning.reactive.tasks.task2.domain.model.AuthorizedUser;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.adapter.AuthorizedUserAdapter;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateAuthUserUseCaseTest {

    @Mock
    private AuthorizedUserAdapter adapter;

    @InjectMocks
    private CreateAuthUserUseCase useCase;

    private final UUID userId = UUID.randomUUID();
    private final AuthorizedUser user = new AuthorizedUser(
            userId,
            "Marcelo",
            "Guajardo",
            "marcelo.guajardo@tenpo.cl",
            "ADMIN",
            1L
    );

    @Nested
    class Create {

        @Test
        void shouldCreateUserSuccessfully() {
            when(adapter.save(user)).thenReturn(Mono.just(user));

            StepVerifier.create(useCase.create(user))
                    .expectNext(user)
                    .verifyComplete();

            verify(adapter).save(user);
        }

        @Test
        void shouldFailToCreateUserWhenAdapterFails() {
            RuntimeException exception = new RuntimeException("DB down");

            when(adapter.save(user)).thenReturn(Mono.error(exception));

            StepVerifier.create(useCase.create(user))
                    .expectErrorMatches(ex -> ex instanceof RuntimeException &&
                            ex.getMessage().equals("DB down"))
                    .verify();

            verify(adapter).save(user);
        }
    }
}
