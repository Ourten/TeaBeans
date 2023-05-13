package fr.ourten.teabeans.listener.holder;

import fr.ourten.teabeans.listener.SetValueChangeListener;
import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.value.ObservableValue;

import java.util.Objects;
import java.util.Set;

public class SetMonoListenerHolder<T> extends MonoListenerHolder<Set<T>> implements SetListenersHolder<T>
{
    private SetValueChangeListener<? super T> setValueChangeListener;

    public SetMonoListenerHolder(SetValueChangeListener<? super T> setValueChangeListener,
                                 ValueChangeListener<? super Set<T>> valueChangeListener,
                                 ValueInvalidationListener arglessValueChangeListener,
                                 ValueInvalidationListener invalidationListener)
    {
        super(valueChangeListener, arglessValueChangeListener, invalidationListener);

        this.setValueChangeListener = setValueChangeListener;
    }

    @Override
    public SetListenersHolder<T> addSetChangeListener(SetValueChangeListener<? super T> listener)
    {
        if (setValueChangeListener != null && !Objects.equals(setValueChangeListener, listener))
            return new SetMultiListenersHolder<T>(setValueChangeListener, valueChangeListener, arglessValueChangeListener, invalidationListener)
                    .addSetChangeListener(listener);
        setValueChangeListener = listener;
        return this;
    }

    @Override
    public SetListenersHolder<T> removeSetChangeListener(SetValueChangeListener<? super T> listener)
    {
        if (Objects.equals(listener, setValueChangeListener))
            setValueChangeListener = null;
        if (valueChangeListener == null && arglessValueChangeListener == null && invalidationListener == null)
            return null;
        return this;
    }

    @Override
    public SetListenersHolder<T> addChangeListener(ValueChangeListener<? super Set<T>> listener)
    {
        if (valueChangeListener != null && !Objects.equals(valueChangeListener, listener))
            return new SetMultiListenersHolder<T>(setValueChangeListener, valueChangeListener, arglessValueChangeListener, invalidationListener)
                    .addChangeListener(listener);
        valueChangeListener = listener;
        return this;
    }

    @Override
    public SetListenersHolder<T> removeChangeListener(ValueChangeListener<? super Set<T>> listener)
    {
        if (Objects.equals(listener, valueChangeListener))
            valueChangeListener = null;
        if (setValueChangeListener == null && arglessValueChangeListener == null && invalidationListener == null)
            return null;
        return this;
    }

    @Override
    public SetListenersHolder<T> addChangeListener(ValueInvalidationListener listener)
    {
        if (arglessValueChangeListener != null && !Objects.equals(arglessValueChangeListener, listener))
            return new SetMultiListenersHolder<T>(setValueChangeListener, valueChangeListener, arglessValueChangeListener, invalidationListener)
                    .addChangeListener(listener);
        arglessValueChangeListener = listener;
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public SetListenersHolder<T> removeChangeListener(ValueInvalidationListener listener)
    {
        if (Objects.equals(listener, arglessValueChangeListener))
            arglessValueChangeListener = null;
        if (setValueChangeListener == null && valueChangeListener == null && invalidationListener == null)
            return null;
        return this;
    }

    @Override
    public SetListenersHolder<T> addListener(ValueInvalidationListener listener)
    {
        if (invalidationListener != null && !Objects.equals(invalidationListener, listener))
            return new SetMultiListenersHolder<T>(setValueChangeListener, valueChangeListener, arglessValueChangeListener, invalidationListener)
                    .addListener(listener);
        invalidationListener = listener;
        return this;
    }

    @Override
    public SetListenersHolder<T> removeListener(ValueInvalidationListener listener)
    {
        if (Objects.equals(listener, invalidationListener))
            invalidationListener = null;
        if (setValueChangeListener == null && valueChangeListener == null && arglessValueChangeListener == null)
            return null;
        return this;
    }

    @Override
    public void fireListChangeListeners(ObservableValue<? extends Set<T>> observable, T oldValue, T newValue)
    {
        if (setValueChangeListener != null)
            setValueChangeListener.valueChanged(observable, oldValue, newValue);
    }

    @Override
    public boolean hasListChangeListeners()
    {
        return setValueChangeListener != null;
    }
}
