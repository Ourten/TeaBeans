package fr.ourten.teabeans.listener;

import fr.ourten.teabeans.value.ObservableValue;

@FunctionalInterface
public interface SetValueChangeListener<T>
{
    void valueChanged(ObservableValue<?> observable, T oldValue, T newValue);
}
