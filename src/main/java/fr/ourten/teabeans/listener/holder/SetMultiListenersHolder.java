package fr.ourten.teabeans.listener.holder;

import fr.ourten.teabeans.listener.ListValueChangeListener;
import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.value.ObservableValue;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

public class SetMultiListenersHolder<T> extends MultiListenersHolder<Set<T>> implements SetListenersHolder<T>
{
    private ListValueChangeListener<? super T>[] listValueChangeListeners;

    public SetMultiListenersHolder(
            ListValueChangeListener<? super T> listValueChangeListener,
            ValueChangeListener<? super Set<T>> valueChangeListener,
            ValueInvalidationListener arglessValueChangeListener,
            ValueInvalidationListener invalidationListener)
    {
        super(valueChangeListener, arglessValueChangeListener, invalidationListener);

        if (listValueChangeListener != null)
            this.listValueChangeListeners = new ListValueChangeListener[] { listValueChangeListener };
    }

    @Override
    public SetListenersHolder<T> addListChangeListener(ListValueChangeListener<? super T> listener)
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
    public SetListenersHolder<T> removeListChangeListener(ListValueChangeListener<? super T> listener)
    {
        ListValueChangeListener<? super T>[] newListeners = cloneListenerArray(listener,
                listValueChangeListeners,
                ListValueChangeListener.class);

        if (newListeners.length == 1 &&
                (valueChangeListeners == null || valueChangeListeners.length == 1) &&
                (arglessValueChangeListeners == null || arglessValueChangeListeners.length == 1) &&
                (invalidationListeners == null || invalidationListeners.length == 1)
        )
            return new SetMonoListenerHolder<>(newListeners[0], valueChangeListeners == null ? null : valueChangeListeners[0], arglessValueChangeListeners == null ? null : arglessValueChangeListeners[0], invalidationListeners == null ? null : invalidationListeners[0]);

        listValueChangeListeners = newListeners;
        return this;
    }

    @Override
    public SetListenersHolder<T> addChangeListener(ValueChangeListener<? super Set<T>> listener)
    {
        return (SetListenersHolder<T>) super.addChangeListener(listener);
    }

    @Override
    public SetListenersHolder<T> removeChangeListener(ValueChangeListener<? super Set<T>> listener)
    {
        ValueChangeListener<? super Set<T>>[] newListeners = cloneListenerArray(listener,
                valueChangeListeners,
                ValueChangeListener.class);

        if (newListeners.length == 1 &&
                (listValueChangeListeners == null || listValueChangeListeners.length == 1) &&
                (arglessValueChangeListeners == null || arglessValueChangeListeners.length == 1) &&
                (invalidationListeners == null || invalidationListeners.length == 1)
        )
            return new SetMonoListenerHolder<>(listValueChangeListeners == null ? null : listValueChangeListeners[0], newListeners[0], arglessValueChangeListeners == null ? null : arglessValueChangeListeners[0], invalidationListeners == null ? null : invalidationListeners[0]);

        valueChangeListeners = newListeners;
        return this;
    }

    @Override
    public SetListenersHolder<T> addChangeListener(ValueInvalidationListener listener)
    {
        return (SetListenersHolder<T>) super.addChangeListener(listener);
    }

    @Override
    public SetListenersHolder<T> removeChangeListener(ValueInvalidationListener listener)
    {
        var newListeners = cloneListenerArray(listener,
                arglessValueChangeListeners,
                ValueInvalidationListener.class);

        if (newListeners.length <= 1 &&
                (listValueChangeListeners == null || listValueChangeListeners.length == 1) &&
                (valueChangeListeners == null || valueChangeListeners.length == 1) &&
                (invalidationListeners == null || invalidationListeners.length == 1)
        )
            return new SetMonoListenerHolder<>(listValueChangeListeners == null ? null : listValueChangeListeners[0], valueChangeListeners == null ? null : valueChangeListeners[0], newListeners[0], invalidationListeners == null ? null : invalidationListeners[0]);

        arglessValueChangeListeners = newListeners;
        return this;
    }

    @Override
    public SetListenersHolder<T> addListener(ValueInvalidationListener listener)
    {
        return (SetListenersHolder<T>) super.addListener(listener);
    }

    @Override
    public SetListenersHolder<T> removeListener(ValueInvalidationListener listener)
    {
        var newListeners = cloneListenerArray(listener,
                invalidationListeners,
                ValueInvalidationListener.class);

        if (newListeners.length <= 1 &&
                (listValueChangeListeners == null || listValueChangeListeners.length == 1) &&
                (valueChangeListeners == null || valueChangeListeners.length == 1) &&
                (arglessValueChangeListeners == null || arglessValueChangeListeners.length == 1)
        )
            return new SetMonoListenerHolder<>(listValueChangeListeners == null ? null : listValueChangeListeners[0], valueChangeListeners == null ? null : valueChangeListeners[0], arglessValueChangeListeners == null ? null : arglessValueChangeListeners[0], newListeners[0]);

        invalidationListeners = newListeners;
        return this;
    }

    @Override
    public void fireListChangeListeners(ObservableValue<? extends Set<T>> observable, T oldValue, T newValue)
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
