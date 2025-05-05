package cl.tenpo.learning.reactive.tasks.task2.util;

import cl.tenpo.learning.reactive.tasks.task2.domain.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Map<Class<?>, HttpStatus> STATUS_MAP = Map.of(
            ServiceUnavailableException.class, HttpStatus.SERVICE_UNAVAILABLE,
            BadRequestException.class, HttpStatus.BAD_REQUEST,
            RetryExhaustedException.class, HttpStatus.GATEWAY_TIMEOUT
    );

    @ExceptionHandler({ServiceUnavailableException.class, BadRequestException.class, RetryExhaustedException.class})
    public Mono<Map<String, Object>> handleKnownExceptions(final RuntimeException ex,
                                                           final ServerWebExchange exchange) {
        final HttpStatus status = resolveHttpStatus(ex);

        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> response = new HashMap<>();
        response.put("status", status.value());
        response.put("error", status.getReasonPhrase());
        response.put("message", ex.getMessage());
        response.put("path", exchange.getRequest().getPath().value());
        response.put("timestamp", System.currentTimeMillis());

        log.warn("[{}] -> {}", ex.getClass().getSimpleName(), ex.getMessage());

        return Mono.just(response);
    }

    private HttpStatus resolveHttpStatus(Throwable ex) {
        return STATUS_MAP.getOrDefault(ex.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
