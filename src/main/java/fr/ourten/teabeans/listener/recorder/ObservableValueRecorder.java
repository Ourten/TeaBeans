package fr.ourten.teabeans.listener.recorder;

import java.util.ArrayList;
import java.util.List;

public class ObservableValueRecorder<T> implements Recorder
{
    protected       int     count     = 0;
    protected final List<T> oldValues = new ArrayList<>();
    protected final List<T> newValues = new ArrayList<>();

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
}
