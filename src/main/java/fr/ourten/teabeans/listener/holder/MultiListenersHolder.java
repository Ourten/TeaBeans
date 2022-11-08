package fr.ourten.teabeans.listener.holder;

import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.value.Observable;
import fr.ourten.teabeans.value.ObservableValue;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

public class MultiListenersHolder<T> implements ListenersHolder<T>
{
    protected ValueChangeListener<? super T>[] valueChangeListeners;

    protected ValueInvalidationListener[] arglessValueChangeListeners;
    protected ValueInvalidationListener[] invalidationListeners;

    public MultiListenersHolder(ValueChangeListener<? super T> valueChangeListener,
                                ValueInvalidationListener arglessValueChangeListener,
                                ValueInvalidationListener invalidationListener)
    {
        if (valueChangeListener != null)
            this.valueChangeListeners = new ValueChangeListener[] { valueChangeListener };
        if (arglessValueChangeListener != null)
            this.arglessValueChangeListeners = new ValueInvalidationListener[] { arglessValueChangeListener };
        if (invalidationListener != null)
            this.invalidationListeners = new ValueInvalidationListener[] { invalidationListener };
    }

    @Override
    public ListenersHolder<T> addChangeListener(ValueChangeListener<? super T> listener)
    {
        if (valueChangeListeners == null)
        {
            valueChangeListeners = new ValueChangeListener[] { listener };
            return this;
        }

        for (var valueChangeListener : valueChangeListeners)
        {
            if (Objects.equals(valueChangeListener, listener))
                return this;
        }

        var length = valueChangeListeners.length;
        valueChangeListeners = Arrays.copyOf(valueChangeListeners, length + 1);
        valueChangeListeners[length] = listener;
        return this;
    }

    @Override
    public ListenersHolder<T> removeChangeListener(ValueChangeListener<? super T> listener)
    {
        if (valueChangeListeners == null)
            return this;

        ValueChangeListener<? super T>[] newListeners = cloneListenerArray(listener,
                valueChangeListeners,
                ValueChangeListener.class);

        if (newListeners.length <= 1 &&
                (arglessValueChangeListeners == null || arglessValueChangeListeners.length == 1) &&
                (invalidationListeners == null || invalidationListeners.length == 1)
        )
            return new MonoListenerHolder<>(newListeners[0], arglessValueChangeListeners == null ? null : arglessValueChangeListeners[0], invalidationListeners == null ? null : invalidationListeners[0]);

        valueChangeListeners = newListeners;
        return this;
    }

    @Override
    public ListenersHolder<T> addChangeListener(ValueInvalidationListener listener)
    {
        if (arglessValueChangeListeners == null)
        {
            arglessValueChangeListeners = new ValueInvalidationListener[] { listener };
            return this;
        }

        for (var arglessValueChangeListener : arglessValueChangeListeners)
        {
            if (Objects.equals(arglessValueChangeListener, listener))
                return this;
        }

        var length = arglessValueChangeListeners.length;
        arglessValueChangeListeners = Arrays.copyOf(arglessValueChangeListeners, length + 1);
        arglessValueChangeListeners[length] = listener;
        return this;
    }

    @Override
    public ListenersHolder<T> removeChangeListener(ValueInvalidationListener listener)
    {
        if (arglessValueChangeListeners == null)
            return this;

        var newListeners = cloneListenerArray(listener,
                arglessValueChangeListeners,
                ValueInvalidationListener.class);

        if (newListeners.length <= 1 &&
                (valueChangeListeners == null || valueChangeListeners.length == 1) &&
                (invalidationListeners == null || invalidationListeners.length == 1)
        )
            return new MonoListenerHolder<>(valueChangeListeners == null ? null : valueChangeListeners[0], newListeners[0], invalidationListeners == null ? null : invalidationListeners[0]);

        arglessValueChangeListeners = newListeners;
        return this;
    }

    @Override
    public ListenersHolder<T> addListener(ValueInvalidationListener listener)
    {
        if (invalidationListeners == null)
        {
            invalidationListeners = new ValueInvalidationListener[] { listener };
            return this;
        }

        for (var invalidationListener : invalidationListeners)
        {
            if (Objects.equals(invalidationListener, listener))
                return this;
        }

        var length = invalidationListeners.length;
        invalidationListeners = Arrays.copyOf(invalidationListeners, length + 1);
        invalidationListeners[length] = listener;
        return this;
    }

    @Override
    public ListenersHolder<T> removeListener(ValueInvalidationListener listener)
    {
        if (invalidationListeners == null)
            return this;

        var newListeners = cloneListenerArray(listener,
                invalidationListeners,
                ValueInvalidationListener.class);

        if (newListeners.length <= 1 &&
                (valueChangeListeners == null || valueChangeListeners.length == 1) &&
                (arglessValueChangeListeners == null || arglessValueChangeListeners.length == 1)
        )
            return new MonoListenerHolder<>(valueChangeListeners == null ? null : valueChangeListeners[0], arglessValueChangeListeners == null ? null : arglessValueChangeListeners[0], newListeners[0]);

        invalidationListeners = newListeners;
        return this;
    }

    @Override
    public void fireChangeListeners(ObservableValue<? extends T> observable, T oldValue, T newValue)
    {
        var listeners = valueChangeListeners;

        if (listeners == null)
            return;

        for (var listener : listeners)
            listener.valueChanged(observable, oldValue, newValue);
    }

    @Override
    public void fireInvalidationListeners(Observable observable)
    {
        var listeners = invalidationListeners;

        if (listeners == null)
            return;

        for (var listener : listeners)
            listener.invalidated(observable);
    }

    @Override
    public void fireChangeArglessListeners(Observable observable)
    {
        var listeners = arglessValueChangeListeners;

        if (listeners == null)
            return;

        for (var listener : listeners)
            listener.invalidated(observable);
    }

    @Override
    public boolean hasChangeListeners()
    {
        return valueChangeListeners != null;
    }

    @Override
    public boolean hasInvalidationListeners()
    {
        return invalidationListeners != null;
    }

    @Override
    public boolean hasChangeArglessListeners()
    {
        return arglessValueChangeListeners != null;
    }

    @SuppressWarnings("unchecked")
    protected <E> E[] cloneListenerArray(E listener, E[] listeners, Class<E> typeClass)
    {
        var length = listeners.length;
        var newListeners = listeners;

        for (int i = 0; i < length; i++)
        {
            var valueChangeListener = listeners[i];

            if (Objects.equals(valueChangeListener, listener))
            {
                newListeners = (E[]) Array.newInstance(typeClass, length - 1);

                if (i != 0)
                    System.arraycopy(listeners, 0, newListeners, 0, i);
                if (i != length - 1)
                    System.arraycopy(listeners, i + 1, newListeners, i, length - i - 1);
            }
        }
        return newListeners;
    }
}
