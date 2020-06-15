package fr.ourten.teabeans.listener.recorder;

import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.value.ObservableValue;

import java.util.ArrayList;
import java.util.List;

public class ValueChangeRecorder<T> implements ValueChangeListener<T>, Recorder
{
    private       int     count;
    private final List<T> oldValues = new ArrayList<>();
    private final List<T> newValues = new ArrayList<>();

    public ValueChangeRecorder()
    {
    }

    @SafeVarargs
    public ValueChangeRecorder(ObservableValue<T>... observables)
    {
        for (ObservableValue<T> observable : observables)
            observable.addListener(this);
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
    public void valueChanged(ObservableValue<? extends T> observable, T oldValue, T newValue)
    {
        count++;
        oldValues.add(oldValue);
        newValues.add(newValue);
    }
}
