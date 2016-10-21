package fr.ourten.teabeans.function;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author Ourten 21 oct. 2016
 */
@FunctionalInterface
public interface PetaFunction<A, B, C, D, E, R>
{
    R apply(A a, B b, C c, D d, E e);

    default <V> PetaFunction<A, B, C, D, E, V> andThen(final Function<? super R, ? extends V> after)
    {
        Objects.requireNonNull(after);
        return (final A a, final B b, final C c, final D d, final E e) -> after.apply(this.apply(a, b, c, d, e));
    }
}