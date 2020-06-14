package fr.ourten.teabeans.value;

import java.util.NoSuchElementException;

public interface IProperty<T> extends WritableValue<T>
{
    void invalidate(T oldValue);

    void bind(ObservableValue<? extends T> observable);

    void unbind();

    boolean isBound();

    void bindBidirectional(IProperty<T> other);

    void unbindBidirectional(IProperty<T> other);

    default T getOrThrow()
    {
        T value;

        value = this.getValue();
        if (value == null)
            throw new NoSuchElementException();
        return value;
    }

    default T getOrDefault(final T defaultValue)
    {
        T value;

        value = this.getValue();
        if (value == null)
            return defaultValue;
        return value;
    }
}