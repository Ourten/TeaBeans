package fr.ourten.teabeans.listener.recorder;

import fr.ourten.teabeans.listener.SetValueChangeListener;
import fr.ourten.teabeans.property.ISetProperty;
import fr.ourten.teabeans.value.ObservableValue;

public class SetValueChangeRecorder<T> extends ObservableValueRecorder<T> implements SetValueChangeListener<T>
{
    public SetValueChangeRecorder()
    {

    }

    @SafeVarargs
    public SetValueChangeRecorder(ISetProperty<T>... setProperties)
    {
        for (ISetProperty<T> setProperty : setProperties)
            setProperty.addSetChangeListener(this);
    }

    @Override
    public void valueChanged(ObservableValue<?> observable, T oldValue, T newValue)
    {
        count++;
        oldValues.add(oldValue);
        newValues.add(newValue);
    }
}
