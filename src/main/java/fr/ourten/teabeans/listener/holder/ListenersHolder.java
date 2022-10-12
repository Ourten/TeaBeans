package fr.ourten.teabeans.listener.holder;

import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.value.Observable;
import fr.ourten.teabeans.value.ObservableValue;

public interface ListenersHolder<T>
{
    ListenersHolder<T> addChangeListener(ValueChangeListener<? super T> listener);

    ListenersHolder<T> removeChangeListener(ValueChangeListener<? super T> listener);


    ListenersHolder<T> addChangeListener(ValueInvalidationListener listener);

    ListenersHolder<T> removeChangeListener(ValueInvalidationListener listener);


    ListenersHolder<T> addListener(ValueInvalidationListener listener);

    ListenersHolder<T> removeListener(ValueInvalidationListener listener);


    void fireChangeListeners(ObservableValue<? extends T> observable, T oldValue, T newValue);

    void fireInvalidationListeners(Observable observable);

    void fireChangeArglessListeners(Observable observable);

    boolean hasChangeListeners();

    boolean hasInvalidationListeners();

    boolean hasChangeArglessListeners();

    static <T> ListenersHolder<T> addChangeListener(ListenersHolder<T> holder, ValueChangeListener<? super T> listener)
    {
        if (holder == null)
            return new MonoListenerHolder<>(listener, null, null);
        return holder.addChangeListener(listener);
    }

    static <T> ListenersHolder<T> removeChangeListener(ListenersHolder<T> holder, ValueChangeListener<? super T> listener)
    {
        if (holder == null)
            return null;
        return holder.removeChangeListener(listener);
    }


    static <T> ListenersHolder<T> addChangeListener(ListenersHolder<T> holder, ValueInvalidationListener listener)
    {
        if (holder == null)
            return new MonoListenerHolder<>(null, listener, null);
        return holder.addChangeListener(listener);
    }

    static <T> ListenersHolder<T> removeChangeListener(ListenersHolder<T> holder, ValueInvalidationListener listener)
    {
        if (holder == null)
            return null;
        return holder.removeChangeListener(listener);
    }


    static <T> ListenersHolder<T> addListener(ListenersHolder<T> holder, ValueInvalidationListener listener)
    {
        if (holder == null)
            return new MonoListenerHolder<>(null, null, listener);
        return holder.addListener(listener);
    }

    static <T> ListenersHolder<T> removeListener(ListenersHolder<T> holder, ValueInvalidationListener listener)
    {
        if (holder == null)
            return null;
        return holder.removeListener(listener);
    }


    static <T> void fireChangeListeners(ListenersHolder<T> holder, ObservableValue<? extends T> observable, T oldValue, T newValue)
    {
        if (holder != null)
            holder.fireChangeListeners(observable, oldValue, newValue);
    }

    static <T> void fireInvalidationListeners(ListenersHolder<T> holder, Observable observable)
    {
        if (holder != null)
            holder.fireInvalidationListeners(observable);
    }

    static <T> void fireChangeArglessListeners(ListenersHolder<T> holder, Observable observable)
    {
        if (holder != null)
            holder.fireChangeArglessListeners(observable);
    }

    static <T> boolean hasChangeListeners(ListenersHolder<T> holder)
    {
        if (holder != null)
            return holder.hasChangeListeners();
        return false;
    }

    static <T> boolean hasInvalidationListeners(ListenersHolder<T> holder)
    {
        if (holder != null)
            return holder.hasInvalidationListeners();
        return false;
    }

    static <T> boolean hasChangeArglessListeners(ListenersHolder<T> holder)
    {
        if (holder != null)
            return holder.hasChangeArglessListeners();
        return false;
    }
}
