package fr.ourten.teabeans.listener.recorder;

import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.value.ObservableValue;

public class ValueChangeRecorder<T> extends ObservableValueRecorder<T> implements ValueChangeListener<T>
{
    public ValueChangeRecorder()
    {
    }

    @SafeVarargs
    public ValueChangeRecorder(ObservableValue<T>... observables)
    {
        for (ObservableValue<T> observable : observables)
            observable.addChangeListener(this);
    }

    @Override
    public void valueChanged(ObservableValue<? extends T> observable, T oldValue, T newValue)
    {
        count++;
        oldValues.add(oldValue);
        newValues.add(newValue);
    }
}
