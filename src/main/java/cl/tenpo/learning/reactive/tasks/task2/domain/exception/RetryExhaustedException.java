package cl.tenpo.learning.reactive.tasks.task2.domain.exception;

import lombok.Getter;

@Getter
public class RetryExhaustedException extends RuntimeException {

    public RetryExhaustedException(String message) {
        super(message);
    }

    public RetryExhaustedException(Throwable cause) {
        super("All retry attempts exhausted", cause);
    }
}
