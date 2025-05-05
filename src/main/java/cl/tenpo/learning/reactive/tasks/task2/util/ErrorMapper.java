package cl.tenpo.learning.reactive.tasks.task2.util;

import cl.tenpo.learning.reactive.tasks.task2.domain.exception.BadRequestException;
import cl.tenpo.learning.reactive.tasks.task2.domain.exception.BaseException;
import cl.tenpo.learning.reactive.tasks.task2.domain.exception.InternalServerException;
import cl.tenpo.learning.reactive.tasks.task2.domain.exception.RestException;
import lombok.AllArgsConstructor;
import lombok.Generated;
import org.springframework.http.HttpStatusCode;

import java.util.Map;

import static cl.tenpo.learning.reactive.tasks.task2.util.ErrorType.percentage_internal_error;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@AllArgsConstructor
public enum ErrorMapper {
    PERCENTAGE(
        Map.of(
            INTERNAL_SERVER_ERROR, new Tuple(percentage_internal_error, ErrorMapper::internalServerException)
        )
    );

    private final Map<HttpStatusCode, Tuple> errorsMap;

    @Generated
    private record Tuple(
        ErrorType type, TriFunction<ErrorType, RestException, Object[], BaseException> function) {
    }

    public static BaseException mapRestException(
        final ErrorMapper mapper, final RestException exception, final Object[] values) {
        final Tuple tuple =
            mapper.errorsMap.getOrDefault(
                exception.getStatus(), mapper.errorsMap.get(INTERNAL_SERVER_ERROR));

        return tuple.function.apply(tuple.type, exception, values);
    }

    private static BadRequestException badRequestException(
        final ErrorType errorType, final RestException exception, final Object[] values) {
        return new BadRequestException(
            errorType.name(), String.format(errorType.message(), values), exception);
    }


    private static InternalServerException internalServerException(
        final ErrorType errorType, final RestException exception, final Object[] values) {
        return new InternalServerException(
            errorType.name(), String.format(errorType.message(), values), exception);
    }
}
