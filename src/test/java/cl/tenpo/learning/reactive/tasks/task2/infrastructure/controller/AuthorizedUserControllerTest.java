package cl.tenpo.learning.reactive.tasks.task2.infrastructure.controller;

import cl.tenpo.learning.reactive.tasks.task2.application.port.CreateAuthUserUseCasePort;
import cl.tenpo.learning.reactive.tasks.task2.application.port.GetAllAuthUsersUseCasePort;
import cl.tenpo.learning.reactive.tasks.task2.application.port.GetAuthUserByIdUseCasePort;
import cl.tenpo.learning.reactive.tasks.task2.application.port.UpdateAuthUserUseCasePort;
import cl.tenpo.learning.reactive.tasks.task2.domain.model.AuthorizedUser;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.internal.AuthUserRequest;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorizedUserControllerTest {

    @InjectMocks
    private AuthorizedUserController controller;
    @Mock
    private CreateAuthUserUseCasePort createPort;
    @Mock
    private GetAuthUserByIdUseCasePort getByIdPort;
    @Mock
    private GetAllAuthUsersUseCasePort getAllPort;
    @Mock
    private UpdateAuthUserUseCasePort updatePort;

    private final UUID userId = UUID.randomUUID();

    private final AuthUserRequest request = new AuthUserRequest(
            userId,
            "Marcelo",
            "Guajardo",
            "marcelo.guajardo@tenpo.cl",
            "ADMIN",
            1L);

    private final AuthorizedUser model = new AuthorizedUser(
            userId,
            "Marcelo",
            "Guajardo",
            "marcelo.guajardo@tenpo.cl",
            "ADMIN",
            1L
    );

    @Nested
    class CreateTests {
        @Test
        void shouldCreateUserSuccessfully() {
            when(createPort.create(any())).thenReturn(Mono.just(model));

            StepVerifier.create(controller.create(Mono.just(request)))
                    .expectNextMatches(response -> response.userId().equals(userId) &&
                            response.email().equals("marcelo.guajardo@tenpo.cl"))
                    .verifyComplete();

            verify(createPort).create(any());
        }
    }

    @Nested
    class UpdateTests {
        @Test
        void shouldUpdateUserSuccessfully() {
            when(updatePort.update(any())).thenReturn(Mono.just(model));

            StepVerifier.create(controller.update(Mono.just(request)))
                    .expectNextMatches(response -> response.userId().equals(userId) &&
                            response.name().equals("Marcelo"))
                    .verifyComplete();

            verify(updatePort).update(any());
        }
    }

    @Nested
    class GetByUserIdTests {
        @Test
        void shouldReturnUserById() {
            when(getByIdPort.getByUserId(userId)).thenReturn(Mono.just(model));

            StepVerifier.create(controller.getByUserId(userId))
                    .expectNextMatches(response -> response.userId().equals(userId))
                    .verifyComplete();

            verify(getByIdPort).getByUserId(userId);
        }
    }

    @Nested
    class ListAllTests {
        @Test
        void shouldReturnAllUsers() {
            when(getAllPort.listAll()).thenReturn(Flux.just(model));

            StepVerifier.create(controller.listAll())
                    .expectNextMatches(response -> response.userId().equals(userId))
                    .verifyComplete();

            verify(getAllPort).listAll();
        }
    }
}
