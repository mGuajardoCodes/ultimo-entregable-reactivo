package cl.tenpo.learning.reactive.tasks.task2.util;

@FunctionalInterface
public interface TriFunction<T, U, V, R> {
    R apply(T t, U u, V v);
}
