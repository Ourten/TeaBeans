package fr.ourten.teabeans.listener.holder;

import fr.ourten.teabeans.listener.SetValueChangeListener;
import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.value.ObservableValue;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

public class SetMultiListenersHolder<T> extends MultiListenersHolder<Set<T>> implements SetListenersHolder<T>
{
    private SetValueChangeListener<? super T>[] setValueChangeListeners;

    public SetMultiListenersHolder(
            SetValueChangeListener<? super T> setValueChangeListener,
            ValueChangeListener<? super Set<T>> valueChangeListener,
            ValueInvalidationListener arglessValueChangeListener,
            ValueInvalidationListener invalidationListener)
    {
        super(valueChangeListener, arglessValueChangeListener, invalidationListener);

        if (setValueChangeListener != null)
            this.setValueChangeListeners = new SetValueChangeListener[] { setValueChangeListener };
    }

    @Override
    public SetListenersHolder<T> addSetChangeListener(SetValueChangeListener<? super T> listener)
    {
        if (setValueChangeListeners == null)
        {
            setValueChangeListeners = new SetValueChangeListener[] { listener };
            return this;
        }

        for (var setValueChangeListener : setValueChangeListeners)
        {
            if (Objects.equals(setValueChangeListener, listener))
                return this;
        }

        var length = setValueChangeListeners.length;
        setValueChangeListeners = Arrays.copyOf(setValueChangeListeners, length + 1);
        setValueChangeListeners[length] = listener;
        return this;
    }

    @Override
    public SetListenersHolder<T> removeSetChangeListener(SetValueChangeListener<? super T> listener)
    {
        if (setValueChangeListeners == null)
            return this;

        SetValueChangeListener<? super T>[] newListeners = cloneListenerArray(listener,
                setValueChangeListeners,
                SetValueChangeListener.class);

        if (newListeners.length == 1 &&
                (valueChangeListeners == null || valueChangeListeners.length == 1) &&
                (arglessValueChangeListeners == null || arglessValueChangeListeners.length == 1) &&
                (invalidationListeners == null || invalidationListeners.length == 1)
        )
            return new SetMonoListenerHolder<>(newListeners[0], valueChangeListeners == null ? null : valueChangeListeners[0], arglessValueChangeListeners == null ? null : arglessValueChangeListeners[0], invalidationListeners == null ? null : invalidationListeners[0]);

        setValueChangeListeners = newListeners;
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
        if (valueChangeListeners == null)
            return this;

        ValueChangeListener<? super Set<T>>[] newListeners = cloneListenerArray(listener,
                valueChangeListeners,
                ValueChangeListener.class);

        if (newListeners.length == 1 &&
                (setValueChangeListeners == null || setValueChangeListeners.length == 1) &&
                (arglessValueChangeListeners == null || arglessValueChangeListeners.length == 1) &&
                (invalidationListeners == null || invalidationListeners.length == 1)
        )
            return new SetMonoListenerHolder<>(setValueChangeListeners == null ? null : setValueChangeListeners[0], newListeners[0], arglessValueChangeListeners == null ? null : arglessValueChangeListeners[0], invalidationListeners == null ? null : invalidationListeners[0]);

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
        if (arglessValueChangeListeners == null)
            return this;

        var newListeners = cloneListenerArray(listener,
                arglessValueChangeListeners,
                ValueInvalidationListener.class);

        if (newListeners.length <= 1 &&
                (setValueChangeListeners == null || setValueChangeListeners.length == 1) &&
                (valueChangeListeners == null || valueChangeListeners.length == 1) &&
                (invalidationListeners == null || invalidationListeners.length == 1)
        )
            return new SetMonoListenerHolder<>(setValueChangeListeners == null ? null : setValueChangeListeners[0], valueChangeListeners == null ? null : valueChangeListeners[0], newListeners[0], invalidationListeners == null ? null : invalidationListeners[0]);

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
        if (invalidationListeners == null)
            return this;

        var newListeners = cloneListenerArray(listener,
                invalidationListeners,
                ValueInvalidationListener.class);

        if (newListeners.length <= 1 &&
                (setValueChangeListeners == null || setValueChangeListeners.length == 1) &&
                (valueChangeListeners == null || valueChangeListeners.length == 1) &&
                (arglessValueChangeListeners == null || arglessValueChangeListeners.length == 1)
        )
            return new SetMonoListenerHolder<>(setValueChangeListeners == null ? null : setValueChangeListeners[0], valueChangeListeners == null ? null : valueChangeListeners[0], arglessValueChangeListeners == null ? null : arglessValueChangeListeners[0], newListeners[0]);

        invalidationListeners = newListeners;
        return this;
    }

    @Override
    public void fireListChangeListeners(ObservableValue<? extends Set<T>> observable, T oldValue, T newValue)
    {
        var listeners = setValueChangeListeners;

        if (listeners == null)
            return;

        for (var listener : listeners)
            listener.valueChanged(observable, oldValue, newValue);
    }

    @Override
    public boolean hasListChangeListeners()
    {
        return setValueChangeListeners != null;
    }
}
