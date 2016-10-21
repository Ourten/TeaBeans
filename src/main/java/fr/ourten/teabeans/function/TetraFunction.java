package fr.ourten.teabeans.function;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author Ourten 21 oct. 2016
 */
@FunctionalInterface
public interface TetraFunction<A, B, C, D, R>
{
    R apply(A a, B b, C c, D d);

    default <V> TetraFunction<A, B, C, D, V> andThen(final Function<? super R, ? extends V> after)
    {
        Objects.requireNonNull(after);
        return (final A a, final B b, final C c, final D d) -> after.apply(this.apply(a, b, c, d));
    }
}