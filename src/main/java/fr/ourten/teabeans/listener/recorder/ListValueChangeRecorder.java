package fr.ourten.teabeans.listener.recorder;

import fr.ourten.teabeans.listener.IListChange;
import fr.ourten.teabeans.listener.ListValueChangeListener;
import fr.ourten.teabeans.property.IListProperty;

public class ListValueChangeRecorder<T> extends ObservableValueRecorder<T> implements ListValueChangeListener<T>
{
    public ListValueChangeRecorder()
    {

    }

    @SafeVarargs
    public ListValueChangeRecorder(IListProperty<T>... listProperties)
    {
        for (IListProperty<T> listProperty : listProperties)
            listProperty.addListChangeListener(this);
    }

    @Override
    public void valueChanged(IListChange<? extends T> change)
    {
        count++;
        oldValues.add(change.oldValue());
        newValues.add(change.newValue());
    }
}
