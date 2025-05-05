package cl.tenpo.learning.reactive.tasks.task2.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ServiceUnavailableException extends ResponseStatusException {

    public ServiceUnavailableException(final String reason) {
        super(HttpStatus.SERVICE_UNAVAILABLE, reason);
    }

    public ServiceUnavailableException(final Throwable cause) {
        super(HttpStatus.SERVICE_UNAVAILABLE, "Service unavailable", cause);
    }
}
