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
class GetAuthUserByIdUseCaseTest {

    @Mock
    private AuthorizedUserAdapter adapter;

    @InjectMocks
    private GetAuthUserByIdUseCase useCase;


    @Nested
    class GetByUserId {

        @Test
        void shouldReturnUserSuccessfully() {
            UUID userId = UUID.randomUUID();
            AuthorizedUser user = new AuthorizedUser(
                    userId,
                    "Marcelo",
                    "Guajardo",
                    "marcelo.guajardo@tenpo.cl",
                    "ADMIN",
                    1L
            );
            when(adapter.findById(userId)).thenReturn(Mono.just(user));

            StepVerifier.create(useCase.getByUserId(userId))
                    .expectNext(user)
                    .verifyComplete();

            verify(adapter).findById(userId);
        }

        @Test
        void shouldFailWhenAdapterThrowsError() {
            UUID userId = UUID.randomUUID();
            RuntimeException exception = new RuntimeException("DB down");

            when(adapter.findById(userId)).thenReturn(Mono.error(exception));

            StepVerifier.create(useCase.getByUserId(userId))
                    .expectErrorMatches(ex -> ex instanceof RuntimeException &&
                            ex.getMessage().equals("DB down"))
                    .verify();

            verify(adapter).findById(userId);
        }
    }
}
