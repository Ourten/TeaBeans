package fr.ourten.teabeans.listener.holder;

import fr.ourten.teabeans.listener.ListValueChangeListener;
import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.value.Observable;
import fr.ourten.teabeans.value.ObservableValue;

import java.util.List;

public interface ListListenersHolder<T, E> extends ListenersHolder<T>
{
    ListListenersHolder<T, E> addListChangeListener(ListValueChangeListener<? super E> listener);

    ListListenersHolder<T, E> removeListChangeListener(ListValueChangeListener<? super E> listener);

    ListListenersHolder<T> addChangeListener(ValueChangeListener<? super T> listener);

    ListListenersHolder<T> removeChangeListener(ValueChangeListener<? super T> listener);


    ListListenersHolder<T> addChangeListener(ValueInvalidationListener listener);

    ListListenersHolder<T> removeChangeListener(ValueInvalidationListener listener);


    ListListenersHolder<T> addListener(ValueInvalidationListener listener);

    ListListenersHolder<T> removeListener(ValueInvalidationListener listener);

    void fireListChangeListeners(ObservableValue<? extends T> observable, T oldValue, T newValue);

    boolean hasListChangeListeners();

    static <T> ListListenersHolder<T> addListChangeListener(ListListenersHolder<T> holder, ListValueChangeListener<? super List<? super T>> listener)
    {
        if (holder == null)
            return new ListMonoListenerHolder<>(listener, null, null, null);
        return holder.addListChangeListener(listener);
    }

    static <T> ListListenersHolder<T> removeListChangeListener(ListListenersHolder<T> holder, ListValueChangeListener<? super List<? super T>> listener)
    {
        if (holder == null)
            return null;
        return holder.removeListChangeListener(listener);
    }


    static <T> ListListenersHolder<T> addChangeListener(ListListenersHolder<T> holder, ValueChangeListener<? super T> listener)
    {
        if (holder == null)
            return new ListMonoListenerHolder<>(null, listener, null, null);
        return holder.addChangeListener(listener);
    }

    static <T> ListListenersHolder<T> removeChangeListener(ListListenersHolder<T> holder, ValueChangeListener<? super T> listener)
    {
        if (holder == null)
            return null;
        return holder.removeChangeListener(listener);
    }


    static <T> ListListenersHolder<T> addChangeListener(ListListenersHolder<T> holder, ValueInvalidationListener listener)
    {
        if (holder == null)
            return new ListMonoListenerHolder<>(null, null, listener, null);
        return holder.addChangeListener(listener);
    }

    static <T> ListListenersHolder<T> removeChangeListener(ListListenersHolder<T> holder, ValueInvalidationListener listener)
    {
        if (holder == null)
            return null;
        return holder.removeChangeListener(listener);
    }


    static <T> ListListenersHolder<T> addListener(ListListenersHolder<T> holder, ValueInvalidationListener listener)
    {
        if (holder == null)
            return new ListMonoListenerHolder<>(null, null, null, listener);
        return holder.addListener(listener);
    }

    static <T> ListListenersHolder<T> removeListener(ListListenersHolder<T> holder, ValueInvalidationListener listener)
    {
        if (holder == null)
            return null;
        return holder.removeListener(listener);
    }


    static <T> void fireChangeListeners(ListListenersHolder<T> holder, ObservableValue<? extends T> observable, T oldValue, T newValue)
    {
        if (holder != null)
            holder.fireChangeListeners(observable, oldValue, newValue);
    }

    static <T> void fireListChangeListeners(ListListenersHolder<T> holder, ObservableValue<? extends T> observable, T oldValue, T newValue)
    {
        if (holder != null)
            holder.fireListChangeListeners(observable, oldValue, newValue);
    }

    static <T> void fireInvalidationListeners(ListListenersHolder<T> holder, Observable observable)
    {
        if (holder != null)
            holder.fireInvalidationListeners(observable);
    }

    static <T> void fireChangeArglessListeners(ListListenersHolder<T> holder, Observable observable)
    {
        if (holder != null)
            holder.fireChangeArglessListeners(observable);
    }

    static <T> boolean hasChangeListeners(ListListenersHolder<T> holder)
    {
        if (holder != null)
            return holder.hasChangeListeners();
        return false;
    }

    static <T> boolean hasListChangeListeners(ListListenersHolder<T> holder)
    {
        if (holder != null)
            return holder.hasListChangeListeners();
        return false;
    }

    static <T> boolean hasInvalidationListeners(ListListenersHolder<T> holder)
    {
        if (holder != null)
            return holder.hasInvalidationListeners();
        return false;
    }

    static <T> boolean hasChangeArglessListeners(ListListenersHolder<T> holder)
    {
        if (holder != null)
            return holder.hasChangeArglessListeners();
        return false;
    }
}
