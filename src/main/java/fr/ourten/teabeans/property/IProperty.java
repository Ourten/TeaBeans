package fr.ourten.teabeans.property;

import fr.ourten.teabeans.value.ObservableValue;
import fr.ourten.teabeans.value.WritableValue;

import java.util.NoSuchElementException;

public interface IProperty<T> extends WritableValue<T>
{
    void bindProperty(ObservableValue<? extends T> observable);

    void unbind();

    boolean isBound();

    void bindBidirectional(IProperty<T> other);

    void unbindBidirectional(IProperty<T> other);

    default T getOrThrow()
    {
        T value;

        value = getValue();
        if (value == null)
            throw new NoSuchElementException();
        return value;
    }

    default T getOrDefault(T defaultValue)
    {
        T value;

        value = getValue();
        if (value == null)
            return defaultValue;
        return value;
    }
}