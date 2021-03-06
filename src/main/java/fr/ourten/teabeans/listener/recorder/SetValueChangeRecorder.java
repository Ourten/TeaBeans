package fr.ourten.teabeans.listener.recorder;

import fr.ourten.teabeans.listener.ListValueChangeListener;
import fr.ourten.teabeans.property.ISetProperty;
import fr.ourten.teabeans.value.ObservableValue;

public class SetValueChangeRecorder<T> extends ObservableValueRecorder<T> implements ListValueChangeListener<T>
{
    public SetValueChangeRecorder()
    {

    }

    @SafeVarargs
    public SetValueChangeRecorder(ISetProperty<T>... setProperties)
    {
        for (ISetProperty<T> setProperty : setProperties)
            setProperty.addChangeListener(this);
    }

    @Override
    public void valueChanged(ObservableValue<?> observable, T oldValue, T newValue)
    {
        count++;
        oldValues.add(oldValue);
        newValues.add(newValue);
    }
}
