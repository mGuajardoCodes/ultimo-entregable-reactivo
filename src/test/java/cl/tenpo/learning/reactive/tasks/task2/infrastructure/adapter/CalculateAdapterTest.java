package cl.tenpo.learning.reactive.tasks.task2.infrastructure.adapter;

import cl.tenpo.learning.reactive.tasks.task2.domain.exception.RetryExhaustedException;
import cl.tenpo.learning.reactive.tasks.task2.domain.model.event.ErrorEvent;
import cl.tenpo.learning.reactive.tasks.task2.domain.port.EventPublisherPort;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.client.CalculateClient;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalculateAdapterTest {

    @Mock
    private CalculateClient calculateClient;

    @Mock
    private EventPublisherPort publisherPort;

    @InjectMocks
    private CalculateAdapter adapter;

    @Nested
    class GetPercentageTests {

        @Test
        void shouldReturnPercentageWhenClientSucceeds() {
            when(calculateClient.retrievePercentage()).thenReturn(Mono.just(0.25));

            StepVerifier.create(adapter.getPercentage())
                    .expectNext(0.25)
                    .verifyComplete();

            verify(calculateClient).retrievePercentage();
            verifyNoInteractions(publisherPort);
        }

        @Test
        void shouldPublishEventAndPropagateWhenRetryExhaustedExceptionOccurs() {
            RetryExhaustedException exception = new RetryExhaustedException(new RuntimeException("external down"));

            when(calculateClient.retrievePercentage()).thenReturn(Mono.error(exception));
            when(publisherPort.publish(any(ErrorEvent.class))).thenReturn(Mono.empty());

            StepVerifier.create(adapter.getPercentage())
                    .expectError(RetryExhaustedException.class)
                    .verify();

            verify(calculateClient).retrievePercentage();
            verify(publisherPort).publish(any(ErrorEvent.class));
        }
    }
}
