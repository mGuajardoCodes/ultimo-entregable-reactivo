package cl.tenpo.learning.reactive.tasks.task2.application.service;

import cl.tenpo.learning.reactive.tasks.task2.domain.exception.ServiceUnavailableException;
import cl.tenpo.learning.reactive.tasks.task2.domain.port.CachePort;
import cl.tenpo.learning.reactive.tasks.task2.domain.port.CalculatePort;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.internal.CalculationRequest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static cl.tenpo.learning.reactive.tasks.task2.util.Constants.PERCENTAGE_CACHE_KEY;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculatePercentageUseCaseTest {

    @Mock
    private CalculatePort calculatePort;

    @Mock
    private CachePort cachePort;

    @InjectMocks
    private CalculatePercentageUseCase useCase;

    private final CalculationRequest request = new CalculationRequest(2.0, 3.0);

    @Nested
    class CalculateTests {

        @Test
        void shouldUseCachedValueWhenExternalFails() {
            when(calculatePort.getPercentage())
                    .thenReturn(Mono.error(new RuntimeException("external down")));

            when(cachePort.get(PERCENTAGE_CACHE_KEY, Double.class))
                    .thenReturn(Mono.just(1.1));

            StepVerifier.create(useCase.calculate(request))
                    .expectNext((2.0 + 3.0) * 1.1)
                    .verifyComplete();
        }

        @Test
        void shouldFailWhenExternalAndCacheAreUnavailable() {
            when(calculatePort.getPercentage())
                    .thenReturn(Mono.error(new RuntimeException("external down")));

            when(cachePort.get(PERCENTAGE_CACHE_KEY, Double.class))
                    .thenReturn(Mono.empty());

            StepVerifier.create(useCase.calculate(request))
                    .expectErrorMatches(error ->
                            error instanceof ServiceUnavailableException &&
                                    error.getMessage()
                                            .contains("No cached percentage available and external service failed"))
                    .verify();
        }
    }
}