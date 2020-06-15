package fr.ourten.teabeans.value;

import fr.ourten.teabeans.binding.Expression;
import fr.ourten.teabeans.function.HexaFunction;
import fr.ourten.teabeans.function.PetaFunction;
import fr.ourten.teabeans.function.TetraFunction;
import fr.ourten.teabeans.function.TriFunction;
import fr.ourten.teabeans.listener.ValueChangeListener;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public interface ObservableValue<T> extends Observable
{
    String OBSERVABLES_NONNULL      = "Observables cannot be null";
    String COMBINE_FUNCTION_NONNULL = "Combine function cannot be null";

    void addListener(ValueChangeListener<? super T> listener);

    void removeListener(ValueChangeListener<? super T> listener);

    T getValue();

    void invalidate();

    default boolean isPresent()
    {
        return getValue() != null;
    }

    default void ifPresent(Consumer<T> function)
    {
        T value;

        value = getValue();
        if (value != null)
            function.accept(value);
    }

    default <R> Optional<R> mapGet(Function<T, R> mapper)
    {
        return Optional.ofNullable(getValue()).map(mapper);
    }

    default <R> ObservableValue<R> map(Function<T, R> mapper)
    {
        return Expression.getExpression(() ->
        {
            if (getValue() != null)
                return mapper.apply(getValue());
            return null;
        }, this);
    }

    default <T2, R> ObservableValue<R> map(T2 constant, BiFunction<T, T2, R> combineFunction)
    {
        Objects.requireNonNull(combineFunction, COMBINE_FUNCTION_NONNULL);
        return Expression.getExpression(() ->
        {
            if (getValue() != null && constant != null)
                return combineFunction.apply(getValue(), constant);
            return null;
        }, this);
    }

    default <T2, R> ObservableValue<R> combine(ObservableValue<T2> toCombine, BiFunction<T, T2, R> combineFunction)
    {
        NullabilityHelper.requireNonNull(OBSERVABLES_NONNULL, toCombine);
        Objects.requireNonNull(combineFunction, COMBINE_FUNCTION_NONNULL);

        return Expression.getExpression(() ->
        {
            if (getValue() != null && toCombine.getValue() != null)
                return combineFunction.apply(getValue(), toCombine.getValue());
            return null;
        }, this, toCombine);
    }

    default <T2, T3, R> ObservableValue<R> combine(ObservableValue<T2> toCombine, ObservableValue<T3> toCombine2, TriFunction<T, T2, T3, R> combineFunction)
    {
        NullabilityHelper.requireNonNull(OBSERVABLES_NONNULL, toCombine, toCombine2);
        Objects.requireNonNull(combineFunction, COMBINE_FUNCTION_NONNULL);

        return Expression.getExpression(() ->
        {
            if (getValue() != null && toCombine.getValue() != null && toCombine2.getValue() != null)
                return combineFunction.apply(getValue(), toCombine.getValue(), toCombine2.getValue());
            return null;
        }, this, toCombine, toCombine2);
    }

    default <T2, T3, T4, R> ObservableValue<R> combine(ObservableValue<T2> toCombine, ObservableValue<T3> toCombine2, ObservableValue<T4> toCombine3, TetraFunction<T, T2, T3, T4, R> combineFunction)
    {
        NullabilityHelper.requireNonNull(OBSERVABLES_NONNULL, toCombine, toCombine2, toCombine3);
        Objects.requireNonNull(combineFunction, COMBINE_FUNCTION_NONNULL);

        return Expression.getExpression(() ->
        {
            if (getValue() != null && toCombine.getValue() != null && toCombine2.getValue() != null && toCombine3.getValue() != null)
                return combineFunction.apply(getValue(), toCombine.getValue(), toCombine2.getValue(), toCombine3.getValue());
            return null;
        }, this, toCombine, toCombine2, toCombine3);
    }

    default <T2, T3, T4, T5, R> ObservableValue<R> combine(ObservableValue<T2> toCombine, ObservableValue<T3> toCombine2, ObservableValue<T4> toCombine3, ObservableValue<T5> toCombine4, PetaFunction<T, T2, T3, T4, T5, R> combineFunction)
    {
        NullabilityHelper.requireNonNull(OBSERVABLES_NONNULL, toCombine, toCombine2, toCombine3, toCombine4);
        Objects.requireNonNull(combineFunction, COMBINE_FUNCTION_NONNULL);

        return Expression.getExpression(() ->
        {
            if (getValue() != null && toCombine.getValue() != null && toCombine2.getValue() != null && toCombine3.getValue() != null
                    && toCombine4.getValue() != null)
                return combineFunction.apply(getValue(), toCombine.getValue(), toCombine2.getValue(), toCombine3.getValue(),
                        toCombine4.getValue());
            return null;
        }, this, toCombine, toCombine2, toCombine3, toCombine4);
    }

    default <T2, T3, T4, T5, T6, R> ObservableValue<R> combine(ObservableValue<T2> toCombine, ObservableValue<T3> toCombine2, ObservableValue<T4> toCombine3, ObservableValue<T5> toCombine4, ObservableValue<T6> toCombine5, HexaFunction<T, T2, T3, T4, T5, T6, R> combineFunction)
    {
        NullabilityHelper.requireNonNull(OBSERVABLES_NONNULL, toCombine, toCombine2, toCombine3, toCombine4, toCombine5);
        Objects.requireNonNull(combineFunction, COMBINE_FUNCTION_NONNULL);

        return Expression.getExpression(() ->
        {
            if (getValue() != null && toCombine.getValue() != null && toCombine2.getValue() != null && toCombine3.getValue() != null
                    && toCombine4.getValue() != null && toCombine5.getValue() != null)
                return combineFunction.apply(getValue(), toCombine.getValue(), toCombine2.getValue(), toCombine3.getValue(),
                        toCombine4.getValue(), toCombine5.getValue());
            return null;
        }, this, toCombine, toCombine2, toCombine3, toCombine4, toCombine5);
    }
}