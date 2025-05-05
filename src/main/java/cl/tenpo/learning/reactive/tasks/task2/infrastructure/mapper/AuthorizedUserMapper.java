package cl.tenpo.learning.reactive.tasks.task2.infrastructure.mapper;

import cl.tenpo.learning.reactive.tasks.task2.domain.model.AuthorizedUser;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.internal.AuthUserRequest;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.internal.AuthorizedUserResponse;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.r2dbc.entity.AuthorizedUserEntity;

public final class AuthorizedUserMapper {

    private AuthorizedUserMapper() { }

    public static AuthorizedUserEntity toEntity(final AuthorizedUser model) {
        return AuthorizedUserEntity.builder()
                .userId(model.userId())
                .name(model.name())
                .lastName(model.lastName())
                .email(model.email())
                .rol(model.rol())
                .status(model.status())
                .build();
    }
    public static AuthorizedUser toModel(final AuthorizedUserEntity entity) {
        return new AuthorizedUser(
                entity.userId(),
                entity.name(),
                entity.lastName(),
                entity.email(),
                entity.rol(),
                entity.status()
        );
    }

    public static AuthorizedUser requestToModel(final AuthUserRequest request) {
        return new AuthorizedUser(
                request.userId(),
                request.name(),
                request.lastName(),
                request.email(),
                request.rol(),
                request.status()
        );
    }

    public static AuthorizedUserResponse modelToResponse(final AuthorizedUser model) {
        return AuthorizedUserResponse.builder()
                .userId(model.userId())
                .name(model.name())
                .lastName(model.lastName())
                .email(model.email())
                .rol(model.rol())
                .status(model.status())
                .build();
    }

}
