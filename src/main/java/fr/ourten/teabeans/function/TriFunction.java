package fr.ourten.teabeans.function;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author Ourten 21 oct. 2016
 */
@FunctionalInterface
public interface TriFunction<A, B, C, R>
{
    R apply(A a, B b, C c);

    default <V> TriFunction<A, B, C, V> andThen(final Function<? super R, ? extends V> after)
    {
        Objects.requireNonNull(after);
        return (final A a, final B b, final C c) -> after.apply(this.apply(a, b, c));
    }
}