package fr.ourten.teabeans.binding;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import fr.ourten.teabeans.function.HexaFunction;
import fr.ourten.teabeans.function.PetaFunction;
import fr.ourten.teabeans.function.TetraFunction;
import fr.ourten.teabeans.function.TriFunction;
import fr.ourten.teabeans.value.Observable;
import fr.ourten.teabeans.value.ObservableValue;

/**
 * @author Ourten 15 oct. 2016
 */
public class BaseExpression<T> extends BaseBinding<T>
{
    private final Supplier<? extends T> closure;

    public BaseExpression(final Supplier<? extends T> closure, final Observable... dependencies)
    {
        this.closure = closure;
        super.bind(dependencies);
    }

    @Override
    public T computeValue()
    {
        return this.closure.get();
    }

    @Override
    public T getValue()
    {
        if (!this.isValid())
        {
            final T computed = this.computeValue();
            if ((computed == null && this.value != null) || (computed != null && !computed.equals(this.value)))
                this.fireChangeListeners(this.value, computed);

            this.value = computed;
            this.setValid(true);
        }
        return this.value;
    }

    public static <A, B, R> BaseExpression<R> constantCombine(final ObservableValue<A> obs1, final B constant,
            final BiFunction<? super A, ? super B, ? extends R> closure)
    {
        Objects.requireNonNull(obs1, "Observables cannot be null!");
        Objects.requireNonNull(closure, "Closure cannot be null!");
        return BaseExpression.getExpression(() ->
        {
            if (obs1.getValue() != null && constant != null)
                return closure.apply(obs1.getValue(), constant);
            else
                return null;
        }, obs1);
    }

    public static <A, B, R> BaseExpression<R> biCombine(final ObservableValue<A> obs1, final ObservableValue<B> obs2,
            final BiFunction<? super A, ? super B, ? extends R> closure)
    {
        Objects.requireNonNull(obs1, "Observables cannot be null!");
        Objects.requireNonNull(obs2, "Observables cannot be null!");
        Objects.requireNonNull(closure, "Closure cannot be null!");
        return BaseExpression.getExpression(() ->
        {
            if (obs1.getValue() != null && obs2.getValue() != null)
                return closure.apply(obs1.getValue(), obs2.getValue());
            else
                return null;
        }, obs1, obs2);
    }

    public static <A, B, C, R> BaseExpression<R> triCombine(final ObservableValue<A> obs1,
            final ObservableValue<B> obs2, final ObservableValue<C> obs3,
            final TriFunction<? super A, ? super B, ? super C, ? extends R> closure)
    {
        Objects.requireNonNull(obs1, "Observables cannot be null!");
        Objects.requireNonNull(obs2, "Observables cannot be null!");
        Objects.requireNonNull(obs3, "Observables cannot be null!");
        Objects.requireNonNull(closure, "Closure cannot be null!");
        return BaseExpression.getExpression(() ->
        {
            if (obs1.getValue() != null && obs2.getValue() != null && obs3.getValue() != null)
                return closure.apply(obs1.getValue(), obs2.getValue(), obs3.getValue());
            else
                return null;
        }, obs1, obs2, obs3);
    }

    public static <A, B, C, D, R> BaseExpression<R> tetraCombine(final ObservableValue<A> obs1,
            final ObservableValue<B> obs2, final ObservableValue<C> obs3, final ObservableValue<D> obs4,
            final TetraFunction<? super A, ? super B, ? super C, ? super D, ? extends R> closure)
    {
        Objects.requireNonNull(obs1, "Observables cannot be null!");
        Objects.requireNonNull(obs2, "Observables cannot be null!");
        Objects.requireNonNull(obs3, "Observables cannot be null!");
        Objects.requireNonNull(obs4, "Observables cannot be null!");
        Objects.requireNonNull(closure, "Closure cannot be null!");
        return BaseExpression.getExpression(() ->
        {
            if (obs1.getValue() != null && obs2.getValue() != null && obs3.getValue() != null
                    && obs4.getValue() != null)
                return closure.apply(obs1.getValue(), obs2.getValue(), obs3.getValue(), obs4.getValue());
            else
                return null;
        }, obs1, obs2, obs3, obs4);
    }

    public static <A, B, C, D, E, R> BaseExpression<R> petaCombine(final ObservableValue<A> obs1,
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
        return BaseExpression.getExpression(() ->
        {
            if (obs1.getValue() != null && obs2.getValue() != null && obs3.getValue() != null && obs4.getValue() != null
                    && obs5.getValue() != null)
                return closure.apply(obs1.getValue(), obs2.getValue(), obs3.getValue(), obs4.getValue(),
                        obs5.getValue());
            else
                return null;
        }, obs1, obs2, obs3, obs4, obs5);
    }

    public static <A, B, C, D, E, F, R> BaseExpression<R> hexaCombine(final ObservableValue<A> obs1,
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
        return BaseExpression.getExpression(() ->
        {
            if (obs1.getValue() != null && obs2.getValue() != null && obs3.getValue() != null && obs4.getValue() != null
                    && obs5.getValue() != null && obs6.getValue() != null)
                return closure.apply(obs1.getValue(), obs2.getValue(), obs3.getValue(), obs4.getValue(),
                        obs5.getValue(), obs6.getValue());
            else
                return null;
        }, obs1, obs2, obs3, obs4, obs5, obs6);
    }

    public static <V> BaseExpression<V> getExpression(final Supplier<? extends V> closure,
            final Observable... dependencies)
    {
        return new BaseExpression<>(closure, dependencies);
    }
}