package cl.tenpo.learning.reactive.tasks.task2.infrastructure.r2dbc.entity;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Builder
@Table("authorized_user")
public record AuthorizedUserEntity(
        @Id
        Long id,
        UUID userId,
        String name,
        String lastName,
        String email,
        String rol,
        Long status
) {
}
