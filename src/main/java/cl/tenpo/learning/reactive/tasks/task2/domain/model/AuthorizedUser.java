package cl.tenpo.learning.reactive.tasks.task2.domain.model;

import java.util.UUID;

public record AuthorizedUser(
        UUID userId,
        String name,
        String lastName,
        String email,
        String rol,
        Long status
) {
}
