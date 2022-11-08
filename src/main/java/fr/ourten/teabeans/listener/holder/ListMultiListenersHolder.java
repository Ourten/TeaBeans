package fr.ourten.teabeans.listener.holder;

import fr.ourten.teabeans.listener.ListValueChangeListener;
import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.value.ObservableValue;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListMultiListenersHolder<T> extends MultiListenersHolder<List<T>> implements ListListenersHolder<T>
{
    private ListValueChangeListener<? super T>[] listValueChangeListeners;

    public ListMultiListenersHolder(
            ListValueChangeListener<? super T> listValueChangeListener,
            ValueChangeListener<? super List<T>> valueChangeListener,
            ValueInvalidationListener arglessValueChangeListener,
            ValueInvalidationListener invalidationListener)
    {
        super(valueChangeListener, arglessValueChangeListener, invalidationListener);

        if (listValueChangeListener != null)
            this.listValueChangeListeners = new ListValueChangeListener[] { listValueChangeListener };
    }

    @Override
    public ListListenersHolder<T> addListChangeListener(ListValueChangeListener<? super T> listener)
    {
        if (listValueChangeListeners == null)
        {
            listValueChangeListeners = new ListValueChangeListener[] { listener };
            return this;
        }

        for (var listValueChangeListener : listValueChangeListeners)
        {
            if (Objects.equals(listValueChangeListener, listener))
                return this;
        }

        var length = listValueChangeListeners.length;
        listValueChangeListeners = Arrays.copyOf(listValueChangeListeners, length + 1);
        listValueChangeListeners[length] = listener;
        return this;
    }

    @Override
    public ListListenersHolder<T> removeListChangeListener(ListValueChangeListener<? super T> listener)
    {
        if (listValueChangeListeners == null)
            return this;

        ListValueChangeListener<? super T>[] newListeners = cloneListenerArray(listener,
                listValueChangeListeners,
                ListValueChangeListener.class);

        if (newListeners.length == 1 &&
                (valueChangeListeners == null || valueChangeListeners.length == 1) &&
                (arglessValueChangeListeners == null || arglessValueChangeListeners.length == 1) &&
                (invalidationListeners == null || invalidationListeners.length == 1)
        )
            return new ListMonoListenerHolder<>(newListeners[0], valueChangeListeners == null ? null : valueChangeListeners[0], arglessValueChangeListeners == null ? null : arglessValueChangeListeners[0], invalidationListeners == null ? null : invalidationListeners[0]);

        listValueChangeListeners = newListeners;
        return this;
    }

    @Override
    public ListListenersHolder<T> addChangeListener(ValueChangeListener<? super List<T>> listener)
    {
        return (ListListenersHolder<T>) super.addChangeListener(listener);
    }

    @Override
    public ListListenersHolder<T> removeChangeListener(ValueChangeListener<? super List<T>> listener)
    {
        if (valueChangeListeners == null)
            return this;

        ValueChangeListener<? super List<T>>[] newListeners = cloneListenerArray(listener,
                valueChangeListeners,
                ValueChangeListener.class);

        if (newListeners.length == 1 &&
                (listValueChangeListeners == null || listValueChangeListeners.length == 1) &&
                (arglessValueChangeListeners == null || arglessValueChangeListeners.length == 1) &&
                (invalidationListeners == null || invalidationListeners.length == 1)
        )
            return new ListMonoListenerHolder<>(listValueChangeListeners == null ? null : listValueChangeListeners[0], newListeners[0], arglessValueChangeListeners == null ? null : arglessValueChangeListeners[0], invalidationListeners == null ? null : invalidationListeners[0]);

        valueChangeListeners = newListeners;
        return this;
    }

    @Override
    public ListListenersHolder<T> addChangeListener(ValueInvalidationListener listener)
    {
        return (ListListenersHolder<T>) super.addChangeListener(listener);
    }

    @Override
    public ListListenersHolder<T> removeChangeListener(ValueInvalidationListener listener)
    {
        if (arglessValueChangeListeners == null)
            return this;

        var newListeners = cloneListenerArray(listener,
                arglessValueChangeListeners,
                ValueInvalidationListener.class);

        if (newListeners.length <= 1 &&
                (listValueChangeListeners == null || listValueChangeListeners.length == 1) &&
                (valueChangeListeners == null || valueChangeListeners.length == 1) &&
                (invalidationListeners == null || invalidationListeners.length == 1)
        )
            return new ListMonoListenerHolder<>(listValueChangeListeners == null ? null : listValueChangeListeners[0], valueChangeListeners == null ? null : valueChangeListeners[0], newListeners[0], invalidationListeners == null ? null : invalidationListeners[0]);

        arglessValueChangeListeners = newListeners;
        return this;
    }

    @Override
    public ListListenersHolder<T> addListener(ValueInvalidationListener listener)
    {
        return (ListListenersHolder<T>) super.addListener(listener);
    }

    @Override
    public ListListenersHolder<T> removeListener(ValueInvalidationListener listener)
    {
        if (invalidationListeners == null)
            return this;

        var newListeners = cloneListenerArray(listener,
                invalidationListeners,
                ValueInvalidationListener.class);

        if (newListeners.length <= 1 &&
                (listValueChangeListeners == null || listValueChangeListeners.length == 1) &&
                (valueChangeListeners == null || valueChangeListeners.length == 1) &&
                (arglessValueChangeListeners == null || arglessValueChangeListeners.length == 1)
        )
            return new ListMonoListenerHolder<>(listValueChangeListeners == null ? null : listValueChangeListeners[0], valueChangeListeners == null ? null : valueChangeListeners[0], arglessValueChangeListeners == null ? null : arglessValueChangeListeners[0], newListeners[0]);

        invalidationListeners = newListeners;
        return this;
    }

    @Override
    public void fireListChangeListeners(ObservableValue<? extends List<T>> observable, T oldValue, T newValue)
    {
        var listeners = listValueChangeListeners;

        if (listeners == null)
            return;

        for (var listener : listeners)
            listener.valueChanged(observable, oldValue, newValue);
    }

    @Override
    public boolean hasListChangeListeners()
    {
        return listValueChangeListeners != null;
    }
}
