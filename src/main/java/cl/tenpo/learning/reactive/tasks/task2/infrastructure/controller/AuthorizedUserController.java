package cl.tenpo.learning.reactive.tasks.task2.infrastructure.controller;

import cl.tenpo.learning.reactive.tasks.task2.application.port.CreateAuthUserUseCasePort;
import cl.tenpo.learning.reactive.tasks.task2.application.port.GetAllAuthUsersUseCasePort;
import cl.tenpo.learning.reactive.tasks.task2.application.port.GetAuthUserByIdUseCasePort;
import cl.tenpo.learning.reactive.tasks.task2.application.port.UpdateAuthUserUseCasePort;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.internal.AuthUserRequest;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.internal.AuthorizedUserResponse;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.mapper.AuthorizedUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/authorized-user")
@RequiredArgsConstructor
public class AuthorizedUserController {

    private final CreateAuthUserUseCasePort createAuthUserUseCasePort;
    private final GetAuthUserByIdUseCasePort getAuthUserByIdUseCasePort;
    private final GetAllAuthUsersUseCasePort getAllAuthUsersUseCasePort;
    private final UpdateAuthUserUseCasePort updateAuthUserUseCasePort;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AuthorizedUserResponse> create(final @RequestBody Mono<AuthUserRequest> req) {
        return req
                .map(AuthorizedUserMapper::requestToModel)
                .flatMap(createAuthUserUseCasePort::create)
                .map(AuthorizedUserMapper::modelToResponse);
    }

    // Usar patch?
    @PutMapping
    public Mono<AuthorizedUserResponse> update(final @RequestBody Mono<AuthUserRequest> req) {
        return req
                .map(AuthorizedUserMapper::requestToModel)
                .flatMap(updateAuthUserUseCasePort::update)
                .map(AuthorizedUserMapper::modelToResponse);
    }

    @GetMapping("/{userId}")
    public Mono<AuthorizedUserResponse> getByUserId(final @PathVariable UUID userId) {
        return Mono.just(userId)
                .flatMap(getAuthUserByIdUseCasePort::getByUserId)
                .map(AuthorizedUserMapper::modelToResponse);
    }

    // Usar paginación
    @GetMapping
    public Flux<AuthorizedUserResponse> listAll() {
        return getAllAuthUsersUseCasePort
                .listAll()
                .map(AuthorizedUserMapper::modelToResponse);
    }
}