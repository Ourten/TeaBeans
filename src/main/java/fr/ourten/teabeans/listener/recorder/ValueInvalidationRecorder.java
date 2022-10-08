package fr.ourten.teabeans.listener.recorder;

import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.value.Observable;

public class ValueInvalidationRecorder implements ValueInvalidationListener, Recorder
{
    private int count;

    public ValueInvalidationRecorder()
    {

    }

    public ValueInvalidationRecorder(Observable... observables)
    {
        for (Observable observable : observables)
            observable.addListener(this);
    }

    @Override
    public void invalidated(Observable observable)
    {
        count++;
    }

    @Override
    public int getCount()
    {
        return count;
    }

    @Override
    public void reset()
    {
        count = 0;
    }
}
