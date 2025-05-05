package cl.tenpo.learning.reactive.tasks.task2.domain.exception;

import lombok.Getter;

@Getter
public class InternalServerException extends BaseException {

    public InternalServerException(final String type, final String message) {
        super(type, message);
    }

    public InternalServerException(final String type, final String message, final String details) {
        super(type, message, details);
    }

    public InternalServerException(final String type, final String message, final Throwable cause) {
        super(type, message, cause);
    }
}
