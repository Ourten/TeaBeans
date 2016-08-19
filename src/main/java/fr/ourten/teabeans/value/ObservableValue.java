package fr.ourten.teabeans.value;

import fr.ourten.teabeans.listener.ValueChangeListener;

public interface ObservableValue<T> extends Observable
{
    void addListener(ValueChangeListener<? super T> listener);

    void removeListener(ValueChangeListener<? super T> listener);

    T getValue();
}