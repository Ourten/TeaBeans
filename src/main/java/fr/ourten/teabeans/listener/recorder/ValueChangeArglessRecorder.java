package fr.ourten.teabeans.listener.recorder;

import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.value.Observable;

public class ValueChangeArglessRecorder implements ValueInvalidationListener, Recorder
{
    private int count;

    public ValueChangeArglessRecorder()
    {

    }

    public ValueChangeArglessRecorder(Observable... observables)
    {
        for (Observable observable : observables)
            observable.addChangeListener(this);
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
