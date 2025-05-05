package cl.tenpo.learning.reactive.tasks.task2.application.service;

import cl.tenpo.learning.reactive.tasks.task2.domain.exception.BadRequestException;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateAuthUserUseCaseTest {

    @Mock
    private AuthorizedUserAdapter adapter;

    @InjectMocks
    private UpdateAuthUserUseCase useCase;

    private final UUID userId = UUID.randomUUID();

    private final AuthorizedUser user = new AuthorizedUser(
            userId,
            "Marcelo",
            "Guajardo",
            "marcelo.guajardo@tenpo.cl",
            "USER",
            1L
    );

    @Nested
    class Update {

        @Test
        void shouldUpdateUserSuccessfully() {
            when(adapter.findById(userId)).thenReturn(Mono.just(user));
            when(adapter.save(user)).thenReturn(Mono.just(user));

            StepVerifier.create(useCase.update(user))
                    .expectNext(user)
                    .verifyComplete();

            verify(adapter).findById(userId);
            verify(adapter).save(user);
        }

        @Test
        void shouldFailWhenUserNotFound() {
            when(adapter.findById(userId)).thenReturn(Mono.empty());

            StepVerifier.create(useCase.update(user))
                    .expectErrorMatches(ex -> ex instanceof BadRequestException &&
                            ex.getMessage()
                                    .contains("Cannot update a user that does not exist in the database"))
                    .verify();

            verify(adapter).findById(userId);
            verify(adapter, never()).save(any());
        }

        @Test
        void shouldFailWhenAdapterThrowsError() {
            RuntimeException exception = new RuntimeException("DB error");

            when(adapter.findById(userId)).thenReturn(Mono.just(user));
            when(adapter.save(user)).thenReturn(Mono.error(exception));

            StepVerifier.create(useCase.update(user))
                    .expectErrorMatches(ex -> ex instanceof RuntimeException &&
                            ex.getMessage().equals("DB error"))
                    .verify();

            verify(adapter).findById(userId);
            verify(adapter).save(user);
        }
    }
}
