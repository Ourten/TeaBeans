package fr.ourten.teabeans.value;

public interface WritableValue<T> extends ObservableValue<T>
{
    void setValue(T value);

    void setPristine(boolean isPristine);

    boolean isPristine();
}