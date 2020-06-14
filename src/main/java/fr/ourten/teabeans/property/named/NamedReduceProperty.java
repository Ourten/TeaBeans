package fr.ourten.teabeans.property.named;

import fr.ourten.teabeans.property.ReduceProperty;
import fr.ourten.teabeans.value.ObservableValue;

import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Stream;

public class NamedReduceProperty<T, V> extends ReduceProperty<T, V> implements INamedProperty
{
    private final String name;

    public NamedReduceProperty(Function<Stream<T>, V> reducingFunction, String name)
    {
        super(reducingFunction);

        this.name = name;
    }

    @Override
    public String getName()
    {
        return name;
    }

    //////////////////
    // CONSTRUCTORS //
    //////////////////

    public static <T, V> NamedReduceProperty<T, V> reduce(String name, Function<Stream<T>, V> reducingFunction)
    {
        return new NamedReduceProperty<>(reducingFunction, name);
    }

    public static <T> NamedReduceProperty<T, T> reduce(String name, BinaryOperator<T> accumulator)
    {
        return new NamedReduceProperty<>(values -> values.reduce(accumulator).orElse(null), name);
    }

    public static <T, V> NamedReduceProperty<T, V> reduce(String name, Function<Stream<T>, V> reducingFunction, ObservableValue<T>... observables)
    {
        NamedReduceProperty<T, V> reduceProperty = new NamedReduceProperty<>(reducingFunction, name);
        reduceProperty.bind(observables);
        return reduceProperty;
    }

    public static <T> NamedReduceProperty<T, T> reduce(String name, BinaryOperator<T> accumulator, ObservableValue<T>... observables)
    {
        NamedReduceProperty<T, T> reduceProperty = new NamedReduceProperty<>(values -> values.reduce(accumulator).orElse(null), name);
        reduceProperty.bind(observables);
        return reduceProperty;
    }
}
