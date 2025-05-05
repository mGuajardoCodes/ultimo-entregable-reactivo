package cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.internal;

import java.util.UUID;

public record AuthUserRequest(
        UUID userId,
        String name,
        String lastName,
        String email,
        String rol,
        Long status
) {
}
