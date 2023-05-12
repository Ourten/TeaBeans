package fr.ourten.teabeans.value;

import fr.ourten.teabeans.listener.ValueInvalidationListener;

public class NoopObservable implements Observable
{
    public static final NoopObservable INSTANCE = new NoopObservable();

    private NoopObservable()
    {

    }

    @Override
    public void addListener(ValueInvalidationListener listener)
    {

    }

    @Override
    public void removeListener(ValueInvalidationListener listener)
    {

    }

    @Override
    public void addChangeListener(ValueInvalidationListener listener)
    {

    }

    @Override
    public void removeChangeListener(ValueInvalidationListener listener)
    {

    }

    @Override
    public void mute()
    {

    }

    @Override
    public void unmute()
    {

    }

    @Override
    public boolean isMuted()
    {
        return false;
    }
}
