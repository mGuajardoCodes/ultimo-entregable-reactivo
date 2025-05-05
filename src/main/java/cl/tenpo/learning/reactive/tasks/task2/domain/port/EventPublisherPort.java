package cl.tenpo.learning.reactive.tasks.task2.domain.port;

import cl.tenpo.learning.reactive.tasks.task2.domain.model.event.ErrorEvent;
import reactor.core.publisher.Mono;

public interface EventPublisherPort {
    Mono<ErrorEvent> publish(ErrorEvent errorEvent);
}
