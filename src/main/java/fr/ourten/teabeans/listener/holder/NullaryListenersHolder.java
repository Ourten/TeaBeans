package fr.ourten.teabeans.listener.holder;

import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.value.Observable;

public interface NullaryListenersHolder
{
    NullaryListenersHolder addChangeListener(ValueInvalidationListener listener);

    NullaryListenersHolder removeChangeListener(ValueInvalidationListener listener);


    NullaryListenersHolder addListener(ValueInvalidationListener listener);

    NullaryListenersHolder removeListener(ValueInvalidationListener listener);


    void fireInvalidationListeners(Observable observable);

    void fireChangeArglessListeners(Observable observable);


    boolean hasInvalidationListeners();

    boolean hasChangeArglessListeners();


    static NullaryListenersHolder addChangeListener(NullaryListenersHolder holder, ValueInvalidationListener listener)
    {
        if (holder == null)
            return new NullaryMonoListenerHolder(listener, null);
        return holder.addChangeListener(listener);
    }

    static NullaryListenersHolder removeChangeListener(NullaryListenersHolder holder, ValueInvalidationListener listener)
    {
        if (holder == null)
            return null;
        return holder.removeChangeListener(listener);
    }


    static NullaryListenersHolder addListener(NullaryListenersHolder holder, ValueInvalidationListener listener)
    {
        if (holder == null)
            return new NullaryMonoListenerHolder(null, listener);
        return holder.addListener(listener);
    }

    static NullaryListenersHolder removeListener(NullaryListenersHolder holder, ValueInvalidationListener listener)
    {
        if (holder == null)
            return null;
        return holder.removeListener(listener);
    }

    static void fireInvalidationListeners(NullaryListenersHolder holder, Observable observable)
    {
        if (holder != null)
            holder.fireInvalidationListeners(observable);
    }

    static void fireChangeArglessListeners(NullaryListenersHolder holder, Observable observable)
    {
        if (holder != null)
            holder.fireChangeArglessListeners(observable);
    }

    static boolean hasInvalidationListeners(NullaryListenersHolder holder)
    {
        if (holder != null)
            return holder.hasInvalidationListeners();
        return false;
    }

    static boolean hasChangeArglessListeners(NullaryListenersHolder holder)
    {
        if (holder != null)
            return holder.hasChangeArglessListeners();
        return false;
    }
}
