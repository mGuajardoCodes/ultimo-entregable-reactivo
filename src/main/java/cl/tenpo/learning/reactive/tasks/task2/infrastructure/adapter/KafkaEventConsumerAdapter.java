package cl.tenpo.learning.reactive.tasks.task2.infrastructure.adapter;

import cl.tenpo.learning.reactive.tasks.task2.domain.model.event.ErrorEvent;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static cl.tenpo.learning.reactive.tasks.task2.util.Constants.CR_RETRY_EXHAUSTED;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaEventConsumerAdapter {

    private final ReactiveKafkaConsumerTemplate<String, ErrorEvent> template;

    @PostConstruct
    public void start() {
        template.receive()
                .doOnNext(record ->
                        log.warn("[{}] Event received: [{}]", CR_RETRY_EXHAUSTED, record))
                .flatMap(record -> Mono.fromRunnable(record.receiverOffset()::acknowledge))
                .doOnError(err -> log.error("Error consuming {} topic", CR_RETRY_EXHAUSTED, err))
                .subscribe();
    }
}
