package cl.tenpo.learning.reactive.tasks.task2.infrastructure.controller;

import cl.tenpo.learning.reactive.tasks.task2.application.port.CalculatePercentageUseCasePort;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.internal.CalculationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calculation")
public class CalculateController {

    private final CalculatePercentageUseCasePort calculatePercentageUseCasePort;

    @PostMapping
    public Mono<Double> calculatePercentage(@RequestBody final Mono<CalculationRequest> request) {
        return request.flatMap(calculatePercentageUseCasePort::calculate);
    }

}
