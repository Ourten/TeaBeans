package fr.ourten.teabeans.listener.recorder;

import fr.ourten.teabeans.listener.ListValueChangeListener;
import fr.ourten.teabeans.property.IListProperty;
import fr.ourten.teabeans.value.ObservableValue;

public class ListValueChangeRecorder<T> extends ObservableValueRecorder<T> implements ListValueChangeListener<T>
{
    public ListValueChangeRecorder()
    {

    }

    @SafeVarargs
    public ListValueChangeRecorder(IListProperty<T>... listProperties)
    {
        for (IListProperty<T> listProperty : listProperties)
            listProperty.addListener(this);
    }

    @Override
    public void valueChanged(ObservableValue<?> observable, T oldValue, T newValue)
    {
        count++;
        oldValues.add(oldValue);
        newValues.add(newValue);
    }
}
