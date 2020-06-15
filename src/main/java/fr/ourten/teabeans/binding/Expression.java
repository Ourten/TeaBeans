package fr.ourten.teabeans.binding;

import fr.ourten.teabeans.value.Observable;

import java.util.function.Supplier;

/**
 * @author Ourten 15 oct. 2016
 */
public class Expression<T> extends Binding<T>
{
    private final Supplier<? extends T> closure;

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

    public static <V> Expression<V> getExpression(Supplier<? extends V> closure,
                                                  Observable... dependencies)
    {
        return new Expression<>(closure, dependencies);
    }
}