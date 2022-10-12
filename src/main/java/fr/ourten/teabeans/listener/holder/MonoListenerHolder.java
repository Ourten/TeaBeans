package fr.ourten.teabeans.listener.holder;

import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.value.Observable;
import fr.ourten.teabeans.value.ObservableValue;

import java.util.Objects;

public class MonoListenerHolder<T> implements ListenersHolder<T>
{
    private ValueChangeListener<? super T> valueChangeListener;

    private ValueInvalidationListener arglessValueChangeListener;
    private ValueInvalidationListener invalidationListener;

    public MonoListenerHolder(ValueChangeListener<? super T> valueChangeListener,
                              ValueInvalidationListener arglessValueChangeListener,
                              ValueInvalidationListener invalidationListener)
    {
        this.valueChangeListener = valueChangeListener;
        this.arglessValueChangeListener = arglessValueChangeListener;
        this.invalidationListener = invalidationListener;
    }

    @Override
    public ListenersHolder<T> addChangeListener(ValueChangeListener<? super T> listener)
    {
        if (valueChangeListener != null && !Objects.equals(valueChangeListener, listener))
            return new MultiListenersHolder<T>(valueChangeListener, arglessValueChangeListener, invalidationListener)
                    .addChangeListener(listener);
        valueChangeListener = listener;
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ListenersHolder<T> removeChangeListener(ValueChangeListener<? super T> listener)
    {
        if (Objects.equals(listener, valueChangeListener))
            valueChangeListener = null;
        if (arglessValueChangeListener == null && invalidationListener == null)
            return null;
        return this;
    }

    @Override
    public ListenersHolder<T> addChangeListener(ValueInvalidationListener listener)
    {
        if (arglessValueChangeListener != null && !Objects.equals(arglessValueChangeListener, listener))
            return new MultiListenersHolder<T>(valueChangeListener, arglessValueChangeListener, invalidationListener)
                    .addChangeListener(listener);
        arglessValueChangeListener = listener;
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ListenersHolder<T> removeChangeListener(ValueInvalidationListener listener)
    {
        if (Objects.equals(listener, arglessValueChangeListener))
            arglessValueChangeListener = null;
        if (valueChangeListener == null && invalidationListener == null)
            return null;
        return this;
    }

    @Override
    public ListenersHolder<T> addListener(ValueInvalidationListener listener)
    {
        if (invalidationListener != null && !Objects.equals(invalidationListener, listener))
            return new MultiListenersHolder<T>(valueChangeListener, arglessValueChangeListener, invalidationListener)
                    .addListener(listener);
        invalidationListener = listener;
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ListenersHolder<T> removeListener(ValueInvalidationListener listener)
    {
        if (Objects.equals(listener, invalidationListener))
            invalidationListener = null;
        if (valueChangeListener == null && arglessValueChangeListener == null)
            return null;
        return this;
    }

    @Override
    public void fireChangeListeners(ObservableValue<? extends T> observable, T oldValue, T newValue)
    {
        if (valueChangeListener != null)
            valueChangeListener.valueChanged(observable, oldValue, newValue);
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
    public boolean hasChangeListeners()
    {
        return valueChangeListener != null;
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
