package fr.ourten.teabeans.binding;

import fr.ourten.teabeans.value.Observable;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Ourten 15 oct. 2016
 */
public class Expression<T> extends Binding<T>
{
    private Supplier<? extends T> closure;

    public Expression(Supplier<? extends T> closure, Observable... dependencies)
    {
        this.closure = closure;
        super.bind(dependencies);
    }

    @Override
    public T computeValue()
    {
        return closure.get();
    }

    public void setClosure(Supplier<? extends T> closure)
    {
        Objects.requireNonNull(closure);

        this.closure = closure;
        invalidate();
    }

    public static <V> Expression<V> getExpression(Supplier<? extends V> closure,
                                                  Observable... dependencies)
    {
        Objects.requireNonNull(closure);
        return new Expression<>(closure, dependencies);
    }
}