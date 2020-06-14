package fr.ourten.teabeans.binding;

import fr.ourten.teabeans.function.HexaFunction;
import fr.ourten.teabeans.function.PetaFunction;
import fr.ourten.teabeans.function.TetraFunction;
import fr.ourten.teabeans.function.TriFunction;
import fr.ourten.teabeans.value.Observable;
import fr.ourten.teabeans.value.ObservableValue;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Ourten 15 oct. 2016
 */
public class Expression<T> extends Binding<T>
{
    private final Supplier<? extends T> closure;

    public Expression(final Supplier<? extends T> closure, final Observable... dependencies)
    {
        this.closure = closure;
        super.bind(dependencies);
    }

    @Override
    public T computeValue()
    {
        return this.closure.get();
    }

    /**
     * This method is not intended for direct use.
     * It will eventually be removed in a future version of the library.
     * Prefer to use ObservableValue#map instead
     *
     * Marked for removal @since 1.2.0
     */
    @Deprecated
    public static <A, R> Expression<R> transform(final ObservableValue<A> obs,
                                                 final Function<? super A, ? extends R> closure)
    {
        Objects.requireNonNull(obs, "Observables cannot be null!");
        return Expression.getExpression(() ->
        {
            if (obs.getValue() != null)
                return closure.apply(obs.getValue());
            return null;
        }, obs);
    }

    /**
     * This method is not intended for direct use.
     * It will eventually be removed in a future version of the library.
     * Prefer to use ObservableValue#map instead
     *
     * Marked for removal @since 1.2.0
     */
    @Deprecated
    public static <A, B, R> Expression<R> constantCombine(final ObservableValue<A> obs1, final B constant,
                                                          final BiFunction<? super A, ? super B, ? extends R> closure)
    {
        Objects.requireNonNull(obs1, "Observables cannot be null!");
        Objects.requireNonNull(closure, "Closure cannot be null!");
        return Expression.getExpression(() ->
        {
            if (obs1.getValue() != null && constant != null)
                return closure.apply(obs1.getValue(), constant);
            return null;
        }, obs1);
    }

    /**
     * This method is not intended for direct use.
     * It will eventually be removed in a future version of the library.
     * Prefer to use ObservableValue#combine instead
     *
     * Marked for removal @since 1.2.0
     */
    @Deprecated
    public static <A, B, R> Expression<R> biCombine(final ObservableValue<A> obs1, final ObservableValue<B> obs2,
                                                    final BiFunction<? super A, ? super B, ? extends R> closure)
    {
        Objects.requireNonNull(obs1, "Observables cannot be null!");
        Objects.requireNonNull(obs2, "Observables cannot be null!");
        Objects.requireNonNull(closure, "Closure cannot be null!");
        return Expression.getExpression(() ->
        {
            if (obs1.getValue() != null && obs2.getValue() != null)
                return closure.apply(obs1.getValue(), obs2.getValue());
            return null;
        }, obs1, obs2);
    }

    /**
     * This method is not intended for direct use.
     * It will eventually be removed in a future version of the library.
     * Prefer to use ObservableValue#combine instead
     *
     * Marked for removal @since 1.2.0
     */
    @Deprecated
    public static <A, B, C, R> Expression<R> triCombine(final ObservableValue<A> obs1,
                                                        final ObservableValue<B> obs2, final ObservableValue<C> obs3,
                                                        final TriFunction<? super A, ? super B, ? super C, ? extends R> closure)
    {
        Objects.requireNonNull(obs1, "Observables cannot be null!");
        Objects.requireNonNull(obs2, "Observables cannot be null!");
        Objects.requireNonNull(obs3, "Observables cannot be null!");
        Objects.requireNonNull(closure, "Closure cannot be null!");
        return Expression.getExpression(() ->
        {
            if (obs1.getValue() != null && obs2.getValue() != null && obs3.getValue() != null)
                return closure.apply(obs1.getValue(), obs2.getValue(), obs3.getValue());
            return null;
        }, obs1, obs2, obs3);
    }

    /**
     * This method is not intended for direct use.
     * It will eventually be removed in a future version of the library.
     * Prefer to use ObservableValue#combine instead
     *
     * Marked for removal @since 1.2.0
     */
    @Deprecated
    public static <A, B, C, D, R> Expression<R> tetraCombine(final ObservableValue<A> obs1,
                                                             final ObservableValue<B> obs2, final ObservableValue<C> obs3, final ObservableValue<D> obs4,
                                                             final TetraFunction<? super A, ? super B, ? super C, ? super D, ? extends R> closure)
    {
        Objects.requireNonNull(obs1, "Observables cannot be null!");
        Objects.requireNonNull(obs2, "Observables cannot be null!");
        Objects.requireNonNull(obs3, "Observables cannot be null!");
        Objects.requireNonNull(obs4, "Observables cannot be null!");
        Objects.requireNonNull(closure, "Closure cannot be null!");
        return Expression.getExpression(() ->
        {
            if (obs1.getValue() != null && obs2.getValue() != null && obs3.getValue() != null
                    && obs4.getValue() != null)
                return closure.apply(obs1.getValue(), obs2.getValue(), obs3.getValue(), obs4.getValue());
            return null;
        }, obs1, obs2, obs3, obs4);
    }

    /**
     * This method is not intended for direct use.
     * It will eventually be removed in a future version of the library.
     * Prefer to use ObservableValue#combine instead
     *
     * Marked for removal @since 1.2.0
     */
    @Deprecated
    public static <A, B, C, D, E, R> Expression<R> petaCombine(final ObservableValue<A> obs1,
                                                               final ObservableValue<B> obs2, final ObservableValue<C> obs3, final ObservableValue<D> obs4,
                                                               final ObservableValue<E> obs5,
                                                               final PetaFunction<? super A, ? super B, ? super C, ? super D, ? super E, ? extends R> closure)
    {
        Objects.requireNonNull(obs1, "Observables cannot be null!");
        Objects.requireNonNull(obs2, "Observables cannot be null!");
        Objects.requireNonNull(obs3, "Observables cannot be null!");
        Objects.requireNonNull(obs4, "Observables cannot be null!");
        Objects.requireNonNull(obs5, "Observables cannot be null!");
        Objects.requireNonNull(closure, "Closure cannot be null!");
        return Expression.getExpression(() ->
        {
            if (obs1.getValue() != null && obs2.getValue() != null && obs3.getValue() != null && obs4.getValue() != null
                    && obs5.getValue() != null)
                return closure.apply(obs1.getValue(), obs2.getValue(), obs3.getValue(), obs4.getValue(),
                        obs5.getValue());
            return null;
        }, obs1, obs2, obs3, obs4, obs5);
    }

    /**
     * This method is not intended for direct use.
     * It will eventually be removed in a future version of the library.
     * Prefer to use ObservableValue#combine instead
     *
     * Marked for removal @since 1.2.0
     */
    @Deprecated
    public static <A, B, C, D, E, F, R> Expression<R> hexaCombine(final ObservableValue<A> obs1,
                                                                  final ObservableValue<B> obs2, final ObservableValue<C> obs3, final ObservableValue<D> obs4,
                                                                  final ObservableValue<E> obs5, final ObservableValue<F> obs6,
                                                                  final HexaFunction<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? extends R> closure)
    {
        Objects.requireNonNull(obs1, "Observables cannot be null!");
        Objects.requireNonNull(obs2, "Observables cannot be null!");
        Objects.requireNonNull(obs3, "Observables cannot be null!");
        Objects.requireNonNull(obs4, "Observables cannot be null!");
        Objects.requireNonNull(obs5, "Observables cannot be null!");
        Objects.requireNonNull(obs6, "Observables cannot be null!");
        Objects.requireNonNull(closure, "Closure cannot be null!");
        return Expression.getExpression(() ->
        {
            if (obs1.getValue() != null && obs2.getValue() != null && obs3.getValue() != null && obs4.getValue() != null
                    && obs5.getValue() != null && obs6.getValue() != null)
                return closure.apply(obs1.getValue(), obs2.getValue(), obs3.getValue(), obs4.getValue(),
                        obs5.getValue(), obs6.getValue());
            return null;
        }, obs1, obs2, obs3, obs4, obs5, obs6);
    }

    public static <V> Expression<V> getExpression(final Supplier<? extends V> closure,
                                                  final Observable... dependencies)
    {
        return new Expression<>(closure, dependencies);
    }
}