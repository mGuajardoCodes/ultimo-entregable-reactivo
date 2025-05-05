package cl.tenpo.learning.reactive.tasks.task2.application.service;

import cl.tenpo.learning.reactive.tasks.task2.domain.model.AuthorizedUser;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.adapter.AuthorizedUserAdapter;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllAuthUsersUseCaseTest {

    @Mock
    private AuthorizedUserAdapter adapter;

    @InjectMocks
    private GetAllAuthUsersUseCase useCase;

    @Nested
    class ListAll {

        @Test
        void shouldReturnAllUsersSuccessfully() {

            AuthorizedUser user1 = new AuthorizedUser(
                    UUID.randomUUID(),
                    "Marcelo",
                    "Guajardo",
                    "marcelo.guajardo@tenpo.cl",
                    "ADMIN",
                    1L
            );
            AuthorizedUser user2 = new AuthorizedUser(
                    UUID.randomUUID(),
                    "Marcela",
                    "Guajajarda",
                    "marcela@tenpo.com",
                    "ADMIN",
                    1L
            );

            when(adapter.findAll()).thenReturn(Flux.just(user1, user2));

            StepVerifier.create(useCase.listAll())
                    .expectNext(user1)
                    .expectNext(user2)
                    .verifyComplete();

            verify(adapter).findAll();
        }

        @Test
        void shouldFailWhenAdapterThrowsError() {
            RuntimeException exception = new RuntimeException("Repository failure");

            when(adapter.findAll()).thenReturn(Flux.error(exception));

            StepVerifier.create(useCase.listAll())
                    .expectErrorMatches(ex -> ex instanceof RuntimeException &&
                            ex.getMessage().equals("Repository failure"))
                    .verify();

            verify(adapter).findAll();
        }
    }
}
