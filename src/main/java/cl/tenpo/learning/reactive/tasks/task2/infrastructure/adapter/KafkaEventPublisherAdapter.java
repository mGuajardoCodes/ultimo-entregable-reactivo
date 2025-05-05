package cl.tenpo.learning.reactive.tasks.task2.infrastructure.adapter;

import cl.tenpo.learning.reactive.tasks.task2.domain.model.event.ErrorEvent;
import cl.tenpo.learning.reactive.tasks.task2.domain.port.EventPublisherPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

import static cl.tenpo.learning.reactive.tasks.task2.util.Constants.CR_RETRY_EXHAUSTED;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaEventPublisherAdapter implements EventPublisherPort {

    private final String className = this.getClass().getSimpleName();
    private static final Integer MAX_ATTEMPTS = 3;
    private static final Integer RETRY_INITIAL_DELAY = 2;

    private final ReactiveKafkaProducerTemplate<String, ErrorEvent> template;

    // TODO: Mejorable?
    @Override
    public Mono<ErrorEvent> publish(final ErrorEvent errorEvent) {
        Mono.just(errorEvent)
                .flatMap(event -> template.send(CR_RETRY_EXHAUSTED, event))
                .retryWhen(Retry.backoff(MAX_ATTEMPTS, Duration.ofSeconds(RETRY_INITIAL_DELAY))
                        .doBeforeRetry(rs -> log.info("[{}] Retry attempt [{}] to send event [{}] to [{}]",
                                className, rs.totalRetries() + 1, errorEvent, CR_RETRY_EXHAUSTED))
                        .onRetryExhaustedThrow((spec, rs) -> rs.failure())
                )
                .doOnSuccess(sr -> log.info("[{}] Success sending event {} to topic [{}]",
                        className, errorEvent, CR_RETRY_EXHAUSTED))
                .doOnError(err -> log.error("[{}] Could not send event [{}] to [{}] after [{}] attempts",
                        className, errorEvent, CR_RETRY_EXHAUSTED, MAX_ATTEMPTS, err))
                .onErrorResume(err -> Mono.empty())
                .subscribe();

        return Mono.just(errorEvent);
    }
}
