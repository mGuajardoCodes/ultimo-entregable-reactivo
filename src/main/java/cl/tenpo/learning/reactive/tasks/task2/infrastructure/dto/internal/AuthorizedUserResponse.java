package cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.internal;

import lombok.Builder;

import java.util.UUID;

@Builder
public record AuthorizedUserResponse(
        UUID userId,
        String name,
        String lastName,
        String email,
        String rol,
        Long status
) {
}
