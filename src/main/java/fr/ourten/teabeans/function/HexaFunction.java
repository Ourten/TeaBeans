package fr.ourten.teabeans.function;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author Ourten 21 oct. 2016
 */
@FunctionalInterface
public interface HexaFunction<A, B, C, D, E, F, R>
{
    R apply(A a, B b, C c, D d, E e, F f);

    default <V> HexaFunction<A, B, C, D, E, F, V> andThen(final Function<? super R, ? extends V> after)
    {
        Objects.requireNonNull(after);
        return (final A a, final B b, final C c, final D d, final E e, final F f) -> after
                .apply(this.apply(a, b, c, d, e, f));
    }
}