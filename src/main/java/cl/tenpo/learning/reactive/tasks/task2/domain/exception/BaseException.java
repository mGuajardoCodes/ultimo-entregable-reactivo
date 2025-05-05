package cl.tenpo.learning.reactive.tasks.task2.domain.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private final String type;
    private final String details;

    public BaseException(final String type, final String message) {
        super(message);
        this.type = type;
        this.details = null;
    }

    public BaseException(final String type, final String message, final String details) {
        super(message);
        this.type = type;
        this.details = details;
    }

    public BaseException(final String type, final String message, final Throwable cause) {
        super(message, cause);
        this.type = type;
        this.details = cause.getMessage();
    }
}
