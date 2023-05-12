package fr.ourten.teabeans.value;

import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.listener.holder.NullaryListenersHolder;

public class SimpleObservable implements Observable
{
    private NullaryListenersHolder listenersHolder;

    private boolean isMuted;
    private boolean hasChanged;

    @Override
    public void addListener(ValueInvalidationListener listener)
    {
        listenersHolder = NullaryListenersHolder.addListener(listenersHolder, listener);
    }

    @Override
    public void removeListener(ValueInvalidationListener listener)
    {
        listenersHolder = NullaryListenersHolder.removeListener(listenersHolder, listener);
    }

    @Override
    public void addChangeListener(ValueInvalidationListener listener)
    {
        listenersHolder = NullaryListenersHolder.addChangeListener(listenersHolder, listener);
    }

    @Override
    public void removeChangeListener(ValueInvalidationListener listener)
    {
        listenersHolder = NullaryListenersHolder.removeChangeListener(listenersHolder, listener);
    }

    @Override
    public void mute()
    {
        this.isMuted = true;
    }

    @Override
    public void unmute()
    {
        this.isMuted = false;

        if (hasChanged)
            this.invalidateWithChange();
        else
            this.invalidate();
    }

    @Override
    public boolean isMuted()
    {
        return isMuted;
    }

    private void fireChangeArglessListeners()
    {
        NullaryListenersHolder.fireChangeArglessListeners(listenersHolder, this);
    }

    private void fireInvalidationListeners()
    {
        NullaryListenersHolder.fireInvalidationListeners(listenersHolder, this);
    }

    public void invalidate()
    {
        if (isMuted())
            return;

        fireInvalidationListeners();
    }

    public void invalidateWithChange()
    {
        if (isMuted())
        {
            hasChanged = true;
            return;
        }

        hasChanged = false;

        fireChangeArglessListeners();
        fireInvalidationListeners();
    }
}
