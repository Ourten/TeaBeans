package fr.ourten.teabeans.value;

import fr.ourten.teabeans.binding.Expression;
import fr.ourten.teabeans.function.HexaFunction;
import fr.ourten.teabeans.function.PetaFunction;
import fr.ourten.teabeans.function.TetraFunction;
import fr.ourten.teabeans.function.TriFunction;
import fr.ourten.teabeans.listener.ValueChangeListener;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public interface ObservableValue<T> extends Observable
{
    void addListener(ValueChangeListener<? super T> listener);

    void removeListener(ValueChangeListener<? super T> listener);

    T getValue();

    default boolean isPresent()
    {
        return this.getValue() != null;
    }

    default void ifPresent(final Consumer<T> function)
    {
        T value;

        value = this.getValue();
        if (value != null)
            function.accept(value);
    }

    default <R> Optional<R> mapGet(Function<T, R> mapper)
    {
        return Optional.ofNullable(getValue()).map(mapper);
    }

    default <R> ObservableValue<R> map(Function<T, R> mapper)
    {
        return Expression.transform(this, mapper);
    }

    default <T2, R> ObservableValue<R> map(T2 constant, BiFunction<T, T2, R> combineFunction)
    {
        return Expression.constantCombine(this, constant, combineFunction);
    }

    default <T2, R> ObservableValue<R> combine(ObservableValue<T2> toCombine, BiFunction<T, T2, R> combineFunction)
    {
        return Expression.biCombine(this, toCombine, combineFunction);
    }

    default <T2, T3, R> ObservableValue<R> combine(ObservableValue<T2> toCombine, ObservableValue<T3> toCombine2, TriFunction<T, T2, T3, R> combineFunction)
    {
        return Expression.triCombine(this, toCombine, toCombine2, combineFunction);
    }

    default <T2, T3, T4, R> ObservableValue<R> combine(ObservableValue<T2> toCombine, ObservableValue<T3> toCombine2, ObservableValue<T4> toCombine3, TetraFunction<T, T2, T3, T4, R> combineFunction)
    {
        return Expression.tetraCombine(this, toCombine, toCombine2, toCombine3, combineFunction);
    }

    default <T2, T3, T4, T5, R> ObservableValue<R> combine(ObservableValue<T2> toCombine, ObservableValue<T3> toCombine2, ObservableValue<T4> toCombine3, ObservableValue<T5> toCombine4, PetaFunction<T, T2, T3, T4, T5, R> combineFunction)
    {
        return Expression.petaCombine(this, toCombine, toCombine2, toCombine3, toCombine4, combineFunction);
    }

    default <T2, T3, T4, T5, T6, R> ObservableValue<R> combine(ObservableValue<T2> toCombine, ObservableValue<T3> toCombine2, ObservableValue<T4> toCombine3, ObservableValue<T5> toCombine4, ObservableValue<T6> toCombine5, HexaFunction<T, T2, T3, T4, T5, T6, R> combineFunction)
    {
        return Expression.hexaCombine(this, toCombine, toCombine2, toCombine3, toCombine4, toCombine5, combineFunction);
    }
}