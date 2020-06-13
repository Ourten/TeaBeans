package fr.ourten.teabeans.value;

import fr.ourten.teabeans.binding.BaseBinding;

import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Stream;

public class ReduceProperty<T, V> extends BaseBinding<V>
{
    private final Function<Stream<T>, V> reducingFunction;

    public ReduceProperty(Function<Stream<T>, V> reducingFunction)
    {
        this.reducingFunction = reducingFunction;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V computeValue()
    {
        return reducingFunction.apply(this.getDependencies().stream().map(obs -> ((ObservableValue<T>) obs).getValue()));
    }

    @Override
    public void bind(Observable... observables)
    {
        for (Observable observable : observables)
        {
            if (!(observable instanceof ObservableValue))
                throw new RuntimeException("ReduceProperty must bind to an ObservableValue");
        }

        super.bind(observables);
    }

    public static <T, V> ReduceProperty<T, V> reduce(Function<Stream<T>, V> reducingFunction)
    {
        return new ReduceProperty<>(reducingFunction);
    }

    public static <T> ReduceProperty<T, T> reduce(BinaryOperator<T> accumulator)
    {
        return new ReduceProperty<>(values -> values.reduce(accumulator).orElse(null));
    }

    public static <T, V> ReduceProperty<T, V> reduce(Function<Stream<T>, V> reducingFunction, ObservableValue<T>... observables)
    {
        ReduceProperty<T, V> reduceProperty = new ReduceProperty<>(reducingFunction);
        reduceProperty.bind(observables);
        return reduceProperty;
    }

    public static <T> ReduceProperty<T, T> reduce(BinaryOperator<T> accumulator, ObservableValue<T>... observables)
    {
        ReduceProperty<T, T> reduceProperty = new ReduceProperty<>(values -> values.reduce(accumulator).orElse(null));
        reduceProperty.bind(observables);
        return reduceProperty;
    }
}
