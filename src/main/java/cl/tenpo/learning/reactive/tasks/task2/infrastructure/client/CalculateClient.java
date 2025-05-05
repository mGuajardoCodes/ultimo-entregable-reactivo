package cl.tenpo.learning.reactive.tasks.task2.infrastructure.client;

import cl.tenpo.learning.reactive.tasks.task2.domain.exception.RestException;
import cl.tenpo.learning.reactive.tasks.task2.domain.exception.RetryExhaustedException;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.env.PercentageProperties;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.mapper.CalculateMapper;
import cl.tenpo.learning.reactive.tasks.task2.util.ErrorHandler;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

import static cl.tenpo.learning.reactive.tasks.task2.util.ErrorMapper.PERCENTAGE;

@Slf4j
@Component
@RequiredArgsConstructor
public class CalculateClient {

    private final String className = this.getClass().getSimpleName();
    private final PercentageProperties properties;
    private final WebClient webClient;

    public Mono<Double> retrievePercentage() {
        return webClient.get()
                .uri(properties.percentage().url())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .timeout(Duration.ofSeconds(5))
                .doOnError(RestException.class, ex ->
                        log.error("[{}] There was an error getting percentage -> error [code: {}, message: {}]",
                                className, ex.getStatus(), ex.getMessage()))
                .onErrorMap(RestException.class, ex -> ErrorHandler.restException(PERCENTAGE, ex))
                .retryWhen(configRetryOption())
                .map(CalculateMapper::responseToDouble);
    }

    private Retry configRetryOption() {
        return Retry.fixedDelay(
                        properties.percentage().retries(), Duration.ofMillis(properties.percentage().retryDelay()))
                .onRetryExhaustedThrow((spec, sig) ->
                        new RetryExhaustedException(sig.failure())
                );
    }
}
