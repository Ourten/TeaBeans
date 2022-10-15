package fr.ourten.teabeans.listener.holder;

import fr.ourten.teabeans.listener.ListValueChangeListener;
import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.value.ObservableValue;

import java.util.List;
import java.util.Objects;

public class ListMonoListenerHolder<T> extends MonoListenerHolder<List<T>> implements ListListenersHolder<T>
{
    private ListValueChangeListener<? super T> listValueChangeListener;

    public ListMonoListenerHolder(ListValueChangeListener<? super T> listValueChangeListener,
                                  ValueChangeListener<? super List<T>> valueChangeListener,
                                  ValueInvalidationListener arglessValueChangeListener,
                                  ValueInvalidationListener invalidationListener)
    {
        super(valueChangeListener, arglessValueChangeListener, invalidationListener);

        this.listValueChangeListener = listValueChangeListener;
    }

    @Override
    public ListListenersHolder<T> addListChangeListener(ListValueChangeListener<? super T> listener)
    {
        if (listValueChangeListener != null && !Objects.equals(listValueChangeListener, listener))
            return new ListMultiListenersHolder<T>(listValueChangeListener, valueChangeListener, arglessValueChangeListener, invalidationListener)
                    .addListChangeListener(listener);
        listValueChangeListener = listener;
        return this;
    }

    @Override
    public ListListenersHolder<T> removeListChangeListener(ListValueChangeListener<? super T> listener)
    {
        if (Objects.equals(listener, listValueChangeListener))
            listValueChangeListener = null;
        if (valueChangeListener == null && arglessValueChangeListener == null && invalidationListener == null)
            return null;
        return this;
    }

    @Override
    public ListListenersHolder<T> addChangeListener(ValueChangeListener<? super List<T>> listener)
    {
        if (valueChangeListener != null && !Objects.equals(valueChangeListener, listener))
            return new ListMultiListenersHolder<T>(listValueChangeListener, valueChangeListener, arglessValueChangeListener, invalidationListener)
                    .addChangeListener(listener);
        valueChangeListener = listener;
        return this;
    }

    @Override
    public ListListenersHolder<T> removeChangeListener(ValueChangeListener<? super List<T>> listener)
    {
        if (Objects.equals(listener, valueChangeListener))
            valueChangeListener = null;
        if (listValueChangeListener == null && arglessValueChangeListener == null && invalidationListener == null)
            return null;
        return this;
    }

    @Override
    public ListListenersHolder<T> addChangeListener(ValueInvalidationListener listener)
    {
        if (arglessValueChangeListener != null && !Objects.equals(arglessValueChangeListener, listener))
            return new ListMultiListenersHolder<T>(listValueChangeListener, valueChangeListener, arglessValueChangeListener, invalidationListener)
                    .addChangeListener(listener);
        arglessValueChangeListener = listener;
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ListListenersHolder<T> removeChangeListener(ValueInvalidationListener listener)
    {
        if (Objects.equals(listener, arglessValueChangeListener))
            arglessValueChangeListener = null;
        if (listValueChangeListener == null && valueChangeListener == null && invalidationListener == null)
            return null;
        return this;
    }

    @Override
    public ListListenersHolder<T> addListener(ValueInvalidationListener listener)
    {
        if (invalidationListener != null && !Objects.equals(invalidationListener, listener))
            return new ListMultiListenersHolder<T>(listValueChangeListener, valueChangeListener, arglessValueChangeListener, invalidationListener)
                    .addListener(listener);
        invalidationListener = listener;
        return this;
    }

    @Override
    public ListListenersHolder<T> removeListener(ValueInvalidationListener listener)
    {
        if (Objects.equals(listener, invalidationListener))
            invalidationListener = null;
        if (listValueChangeListener == null && valueChangeListener == null && arglessValueChangeListener == null)
            return null;
        return this;
    }

    @Override
    public void fireListChangeListeners(ObservableValue<? extends List<T>> observable, T oldValue, T newValue)
    {
        if (listValueChangeListener != null)
            listValueChangeListener.valueChanged(observable, oldValue, newValue);
    }

    @Override
    public boolean hasListChangeListeners()
    {
        return listValueChangeListener != null;
    }
}
