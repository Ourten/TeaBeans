package fr.ourten.teabeans.value;

import java.util.function.Consumer;

import fr.ourten.teabeans.listener.ValueChangeListener;

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
}