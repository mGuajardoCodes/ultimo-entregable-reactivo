package cl.tenpo.learning.reactive.tasks.task2.application.port;

import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.internal.CalculationRequest;
import reactor.core.publisher.Mono;

public interface CalculatePercentageUseCasePort {
    Mono<Double> calculate(CalculationRequest calculationRequest);
}
