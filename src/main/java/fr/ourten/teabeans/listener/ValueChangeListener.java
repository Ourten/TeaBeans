package fr.ourten.teabeans.listener;

import fr.ourten.teabeans.value.ObservableValue;

public interface ValueChangeListener<T>
{
    void valueChanged(ObservableValue<? extends T> observable, T oldValue, T newValue);
}