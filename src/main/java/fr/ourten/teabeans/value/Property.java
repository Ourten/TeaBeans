package fr.ourten.teabeans.value;

public interface Property<T> extends ObservableValue<T>, WritableValue<T>
{
    String getName();

    void invalidate(T oldValue);

    void bind(ObservableValue<? extends T> observable);

    void unbind();

    boolean isBound();

    void bindBidirectional(Property<T> other);

    void unbindBidirectional(Property<T> other);
}
