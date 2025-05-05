package cl.tenpo.learning.reactive.tasks.task2.domain.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends BaseException {

    public BadRequestException(final String type, final String message) {
        super(type, message);
    }

    public BadRequestException(final String type, final String message, final String details) {
        super(type, message, details);
    }

    public BadRequestException(final String type, final String message, final Throwable cause) {
        super(type, message, cause);
    }
}
