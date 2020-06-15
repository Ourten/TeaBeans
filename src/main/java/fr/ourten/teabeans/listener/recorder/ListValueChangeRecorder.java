package fr.ourten.teabeans.listener.recorder;

import fr.ourten.teabeans.listener.ListValueChangeListener;
import fr.ourten.teabeans.property.IListProperty;
import fr.ourten.teabeans.value.ObservableValue;

import java.util.ArrayList;
import java.util.List;

public class ListValueChangeRecorder<T> implements ListValueChangeListener<T>, Recorder
{
    private       int     count;
    private final List<T> oldValues = new ArrayList<>();
    private final List<T> newValues = new ArrayList<>();

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
    public int getCount()
    {
        return count;
    }

    public List<T> getOldValues()
    {
        return oldValues;
    }

    public List<T> getNewValues()
    {
        return newValues;
    }

    @Override
    public void reset()
    {
        count = 0;
        oldValues.clear();
        newValues.clear();
    }

    @Override
    public void valueChanged(ObservableValue<?> observable, T oldValue, T newValue)
    {
        count++;
        oldValues.add(oldValue);
        newValues.add(newValue);
    }
}
