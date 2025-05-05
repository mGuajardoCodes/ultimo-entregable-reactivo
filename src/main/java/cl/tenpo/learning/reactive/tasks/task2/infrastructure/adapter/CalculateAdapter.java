package cl.tenpo.learning.reactive.tasks.task2.infrastructure.adapter;

import cl.tenpo.learning.reactive.tasks.task2.domain.exception.RetryExhaustedException;
import cl.tenpo.learning.reactive.tasks.task2.domain.port.CalculatePort;
import cl.tenpo.learning.reactive.tasks.task2.domain.port.EventPublisherPort;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.client.CalculateClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import cl.tenpo.learning.reactive.tasks.task2.domain.model.event.ErrorEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class CalculateAdapter implements CalculatePort {

    private final String className = this.getClass().getSimpleName();
    private final CalculateClient calculateClient;
    private final EventPublisherPort publisherPort;

    @Override
    public Mono<Double> getPercentage() {
        return calculateClient.retrievePercentage()
                .doOnSuccess(resp ->
                        log.info("[{}] External service responds satisfactorily -> [{}]" , className, resp.toString()))
                .onErrorResume(RetryExhaustedException.class, this::handleRetryExhausted);
    }

    private Mono<Double> handleRetryExhausted(final RetryExhaustedException ex) {
        return Mono.just(ex.getMessage())
                .map(ErrorEvent::new)
                .flatMap(publisherPort::publish)
                .then(Mono.error(ex));
    }
}
