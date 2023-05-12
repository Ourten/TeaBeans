package fr.ourten.teabeans.listener.holder;

import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.value.Observable;

import java.util.Objects;

public class NullaryMonoListenerHolder implements NullaryListenersHolder
{
    protected ValueInvalidationListener arglessValueChangeListener;
    protected ValueInvalidationListener invalidationListener;

    public NullaryMonoListenerHolder(ValueInvalidationListener arglessValueChangeListener,
                                     ValueInvalidationListener invalidationListener)
    {
        this.arglessValueChangeListener = arglessValueChangeListener;
        this.invalidationListener = invalidationListener;
    }

    @Override
    public NullaryListenersHolder addChangeListener(ValueInvalidationListener listener)
    {
        if (arglessValueChangeListener != null && !Objects.equals(arglessValueChangeListener, listener))
            return new NullaryMultiListenersHolder(arglessValueChangeListener, invalidationListener)
                    .addChangeListener(listener);
        arglessValueChangeListener = listener;
        return this;
    }

    @Override
    public NullaryListenersHolder removeChangeListener(ValueInvalidationListener listener)
    {
        if (Objects.equals(listener, arglessValueChangeListener))
            arglessValueChangeListener = null;
        if (invalidationListener == null)
            return null;
        return this;
    }

    @Override
    public NullaryListenersHolder addListener(ValueInvalidationListener listener)
    {
        if (invalidationListener != null && !Objects.equals(invalidationListener, listener))
            return new NullaryMultiListenersHolder(arglessValueChangeListener, invalidationListener)
                    .addListener(listener);
        invalidationListener = listener;
        return this;
    }

    @Override
    public NullaryListenersHolder removeListener(ValueInvalidationListener listener)
    {
        if (Objects.equals(listener, invalidationListener))
            invalidationListener = null;
        if (arglessValueChangeListener == null)
            return null;
        return this;
    }

    @Override
    public void fireInvalidationListeners(Observable observable)
    {
        if (invalidationListener != null)
            invalidationListener.invalidated(observable);
    }

    @Override
    public void fireChangeArglessListeners(Observable observable)
    {
        if (arglessValueChangeListener != null)
            arglessValueChangeListener.invalidated(observable);
    }

    @Override
    public boolean hasInvalidationListeners()
    {
        return invalidationListener != null;
    }

    @Override
    public boolean hasChangeArglessListeners()
    {
        return arglessValueChangeListener != null;
    }
}
