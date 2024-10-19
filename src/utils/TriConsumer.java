package utils;

@FunctionalInterface
public interface TriConsumer<T, U, V> {
    void accept(T t, U u, V v);

    default TriConsumer<T, U, V> andThen(TriConsumer<? super T, ? super U, ? super V> after) {
        if (after == null) {
            throw new NullPointerException();
        }
        return (l, m, n) -> {
            accept(l, m, n);
            after.accept(l, m, n);
        };
    }
}