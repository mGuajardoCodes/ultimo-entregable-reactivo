package cl.tenpo.learning.reactive.tasks.task2.application.service;

import cl.tenpo.learning.reactive.tasks.task2.application.port.CalculatePercentageUseCasePort;
import cl.tenpo.learning.reactive.tasks.task2.domain.port.CachePort;
import cl.tenpo.learning.reactive.tasks.task2.domain.port.CalculatePort;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.internal.CalculationRequest;
import cl.tenpo.learning.reactive.tasks.task2.util.ErrorHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static cl.tenpo.learning.reactive.tasks.task2.util.Constants.PERCENTAGE_CACHE_KEY;
import static cl.tenpo.learning.reactive.tasks.task2.util.Constants.PERCENTAGE_CACHE_TTL;

@Service
@Slf4j
@RequiredArgsConstructor
public class CalculatePercentageUseCase implements CalculatePercentageUseCasePort {

    private final String className = this.getClass().getSimpleName();
    private final CalculatePort calculatePort;
    private final CachePort cachePort;

    @Override
    public Mono<Double> calculate(final CalculationRequest calculationRequest) {
        return calculatePort
                .getPercentage()
                .delayUntil(this::putPercentageInCache)
                .onErrorResume(this::fallbackToCache)
                .map(percentage -> (calculationRequest.num_1() + calculationRequest.num_2()) * percentage)
                .doOnError(ex ->
                        log.error("[{}] Error processing calculation, error -> [{}]", className, ex.getMessage())
                );
    }

    private Mono<Double> fallbackToCache(final Throwable ex) {
        return Mono.fromRunnable(() ->
                        log.warn("[{}] External service failed [{}] – falling back to cache",
                                className, ex.getMessage()))
                .then(cachePort.get(PERCENTAGE_CACHE_KEY, Double.class))
                .doOnNext(resp ->
                        log.info("[{}] Redis cache responds satisfactorily -> [{}]" , className, resp.toString()))
                .switchIfEmpty(Mono.error(ErrorHandler.serviceUnavailableException(ex.getMessage())));
    }

    private Mono<Void> putPercentageInCache(final Double percentage) {
        return cachePort.put(PERCENTAGE_CACHE_KEY, percentage, PERCENTAGE_CACHE_TTL);
    }

}
