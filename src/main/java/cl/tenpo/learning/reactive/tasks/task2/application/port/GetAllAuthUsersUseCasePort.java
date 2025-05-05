package cl.tenpo.learning.reactive.tasks.task2.application.port;

import cl.tenpo.learning.reactive.tasks.task2.domain.model.AuthorizedUser;
import reactor.core.publisher.Flux;

public interface GetAllAuthUsersUseCasePort {
    Flux<AuthorizedUser> listAll();
}
