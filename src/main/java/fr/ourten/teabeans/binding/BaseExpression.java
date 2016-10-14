package fr.ourten.teabeans.binding;

import java.util.function.BiFunction;
import java.util.function.Supplier;

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
            if (computed == null && this.value != null || !computed.equals(this.value))
                this.fireChangeListeners(this.value, computed);

            this.value = computed;
            this.setValid(true);
        }
        return this.value;
    }

    public static <A, B, R> BaseExpression<R> combine(final ObservableValue<A> obs1, final ObservableValue<B> obs2,
            final BiFunction<? super A, ? super B, ? extends R> closure)
    {
        return BaseExpression.getExpression(() ->
        {
            if (obs1.getValue() != null && obs2.getValue() != null)
                return closure.apply(obs1.getValue(), obs2.getValue());
            else
                return null;
        }, obs1, obs2);
    }

    public static <V> BaseExpression<V> getExpression(final Supplier<? extends V> closure,
            final Observable... dependencies)
    {
        return new BaseExpression<>(closure, dependencies);
    }
}