package fr.ourten.teabeans.listener.holder;

import fr.ourten.teabeans.listener.SetValueChangeListener;
import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.value.Observable;
import fr.ourten.teabeans.value.ObservableValue;

import java.util.Set;

public interface SetListenersHolder<T> extends ListenersHolder<Set<T>>
{
    SetListenersHolder<T> addSetChangeListener(SetValueChangeListener<? super T> listener);

    SetListenersHolder<T> removeSetChangeListener(SetValueChangeListener<? super T> listener);

    SetListenersHolder<T> addChangeListener(ValueChangeListener<? super Set<T>> listener);

    SetListenersHolder<T> removeChangeListener(ValueChangeListener<? super Set<T>> listener);


    SetListenersHolder<T> addChangeListener(ValueInvalidationListener listener);

    SetListenersHolder<T> removeChangeListener(ValueInvalidationListener listener);


    SetListenersHolder<T> addListener(ValueInvalidationListener listener);

    SetListenersHolder<T> removeListener(ValueInvalidationListener listener);

    void fireListChangeListeners(ObservableValue<? extends Set<T>> observable, T oldValue, T newValue);

    boolean hasListChangeListeners();

    static <T> SetListenersHolder<T> addSetChangeListener(SetListenersHolder<T> holder, SetValueChangeListener<? super T> listener)
    {
        if (holder == null)
            return new SetMonoListenerHolder<>(listener, null, null, null);
        return holder.addSetChangeListener(listener);
    }

    static <T> SetListenersHolder<T> removeSetChangeListener(SetListenersHolder<T> holder, SetValueChangeListener<? super T> listener)
    {
        if (holder == null)
            return null;
        return holder.removeSetChangeListener(listener);
    }


    static <T> SetListenersHolder<T> addChangeListener(SetListenersHolder<T> holder, ValueChangeListener<? super Set<T>> listener)
    {
        if (holder == null)
            return new SetMonoListenerHolder<>(null, listener, null, null);
        return holder.addChangeListener(listener);
    }

    static <T> SetListenersHolder<T> removeChangeListener(SetListenersHolder<T> holder, ValueChangeListener<? super Set<T>> listener)
    {
        if (holder == null)
            return null;
        return holder.removeChangeListener(listener);
    }


    static <T> SetListenersHolder<T> addChangeListener(SetListenersHolder<T> holder, ValueInvalidationListener listener)
    {
        if (holder == null)
            return new SetMonoListenerHolder<>(null, null, listener, null);
        return holder.addChangeListener(listener);
    }

    static <T> SetListenersHolder<T> removeChangeListener(SetListenersHolder<T> holder, ValueInvalidationListener listener)
    {
        if (holder == null)
            return null;
        return holder.removeChangeListener(listener);
    }


    static <T> SetListenersHolder<T> addListener(SetListenersHolder<T> holder, ValueInvalidationListener listener)
    {
        if (holder == null)
            return new SetMonoListenerHolder<>(null, null, null, listener);
        return holder.addListener(listener);
    }

    static <T> SetListenersHolder<T> removeListener(SetListenersHolder<T> holder, ValueInvalidationListener listener)
    {
        if (holder == null)
            return null;
        return holder.removeListener(listener);
    }


    static <T> void fireChangeListeners(SetListenersHolder<T> holder, ObservableValue<? extends Set<T>> observable, Set<T> oldValue, Set<T> newValue)
    {
        if (holder != null)
            holder.fireChangeListeners(observable, oldValue, newValue);
    }

    static <T> void fireListChangeListeners(SetListenersHolder<T> holder, ObservableValue<? extends Set<T>> observable, T oldValue, T newValue)
    {
        if (holder != null)
            holder.fireListChangeListeners(observable, oldValue, newValue);
    }

    static <T> void fireInvalidationListeners(SetListenersHolder<T> holder, Observable observable)
    {
        if (holder != null)
            holder.fireInvalidationListeners(observable);
    }

    static <T> void fireChangeArglessListeners(SetListenersHolder<T> holder, Observable observable)
    {
        if (holder != null)
            holder.fireChangeArglessListeners(observable);
    }

    static <T> boolean hasChangeListeners(SetListenersHolder<T> holder)
    {
        if (holder != null)
            return holder.hasChangeListeners();
        return false;
    }

    static <T> boolean hasListChangeListeners(SetListenersHolder<T> holder)
    {
        if (holder != null)
            return holder.hasListChangeListeners();
        return false;
    }

    static <T> boolean hasInvalidationListeners(SetListenersHolder<T> holder)
    {
        if (holder != null)
            return holder.hasInvalidationListeners();
        return false;
    }

    static <T> boolean hasChangeArglessListeners(SetListenersHolder<T> holder)
    {
        if (holder != null)
            return holder.hasChangeArglessListeners();
        return false;
    }
}
