package cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.internal;

import reactor.util.annotation.NonNull;

public record CalculationRequest (@NonNull Double num_1, @NonNull Double num_2) { }
