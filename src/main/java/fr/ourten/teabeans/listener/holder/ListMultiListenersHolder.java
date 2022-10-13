package fr.ourten.teabeans.listener.holder;

import fr.ourten.teabeans.listener.ListValueChangeListener;
import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.value.ObservableValue;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListMultiListenersHolder<T> extends MultiListenersHolder<List<T>> implements ListListenersHolder<List<T>, T>
{
    private ListValueChangeListener<? super T>[] listValueChangeListeners;

    public ListMultiListenersHolder(
            ListValueChangeListener<? super T> listValueChangeListener,
            ValueChangeListener<? super List<? super T>> valueChangeListener,
            ValueInvalidationListener arglessValueChangeListener,
            ValueInvalidationListener invalidationListener)
    {
        super(valueChangeListener, arglessValueChangeListener, invalidationListener);

        if (listValueChangeListener != null)
            this.listValueChangeListeners = new ListValueChangeListener[] { listValueChangeListener };
    }

    @Override
    public ListListenersHolder<? super List<? super T>> addListChangeListener(ListValueChangeListener<? super T> listener)
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
    public ListListenersHolder<? super List<? super T>> removeListChangeListener(ListValueChangeListener<? super T> listener)
    {
        var length = listValueChangeListeners.length;
        var newListeners = listValueChangeListeners;

        for (int i = 0; i < length; i++)
        {
            var valueChangeListener = listValueChangeListeners[i];

            if (Objects.equals(valueChangeListener, listener))
            {
                newListeners = new ListValueChangeListener[length - 1];

                if (i != 0)
                    System.arraycopy(listValueChangeListeners, 0, newListeners, 0, i);
                if (i != length - 1)
                    System.arraycopy(listValueChangeListeners, i + 1, newListeners, i, length - i - 1);
            }
        }

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
    public ListListenersHolder<? super List<? super T>> addChangeListener(ValueChangeListener<? super List<? super T>> listener)
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
    public ListListenersHolder<? super List<? super T>> removeChangeListener(ValueChangeListener<? super List<? super T>> listener)
    {
        var length = valueChangeListeners.length;
        var newListeners = valueChangeListeners;

        for (int i = 0; i < length; i++)
        {
            var valueChangeListener = valueChangeListeners[i];

            if (Objects.equals(valueChangeListener, listener))
            {
                newListeners = new ValueChangeListener[length - 1];

                if (i != 0)
                    System.arraycopy(valueChangeListeners, 0, newListeners, 0, i);
                if (i != length - 1)
                    System.arraycopy(valueChangeListeners, i + 1, newListeners, i, length - i - 1);
            }
        }

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
    public ListListenersHolder<? super List<? super T>> addChangeListener(ValueInvalidationListener listener)
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
    public ListListenersHolder<? super List<? super T>> removeChangeListener(ValueInvalidationListener listener)
    {
        var length = arglessValueChangeListeners.length;
        var newListeners = arglessValueChangeListeners;

        for (int i = 0; i < length; i++)
        {
            var arglessValueChangeListener = arglessValueChangeListeners[i];

            if (Objects.equals(arglessValueChangeListener, listener))
            {
                newListeners = new ValueInvalidationListener[length - 1];

                if (i != 0)
                    System.arraycopy(arglessValueChangeListeners, 0, newListeners, 0, i);
                if (i != length - 1)
                    System.arraycopy(arglessValueChangeListeners, i + 1, newListeners, i, length - i - 1);
            }
        }

        if (newListeners.length == 1 &&
                (listValueChangeListeners == null || listValueChangeListeners.length == 1) &&
                (valueChangeListeners == null || valueChangeListeners.length == 1) &&
                (invalidationListeners == null || invalidationListeners.length == 1)
        )
            return new ListMonoListenerHolder<>(listValueChangeListeners == null ? null : listValueChangeListeners[0], valueChangeListeners == null ? null : valueChangeListeners[0], newListeners[0], invalidationListeners == null ? null : invalidationListeners[0]);

        arglessValueChangeListeners = newListeners;
        return this;
    }

    @Override
    public ListListenersHolder<? super List<? super T>> addListener(ValueInvalidationListener listener)
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
    public ListListenersHolder<? super List<? super T>> removeListener(ValueInvalidationListener listener)
    {
        var length = invalidationListeners.length;
        var newListeners = invalidationListeners;

        for (int i = 0; i < length; i++)
        {
            var arglessValueChangeListener = invalidationListeners[i];

            if (Objects.equals(arglessValueChangeListener, listener))
            {
                newListeners = new ValueInvalidationListener[length - 1];

                if (i != 0)
                    System.arraycopy(invalidationListeners, 0, newListeners, 0, i);
                if (i != length - 1)
                    System.arraycopy(invalidationListeners, i + 1, newListeners, i, length - i - 1);
            }
        }

        if (newListeners.length == 1 &&
                (listValueChangeListeners == null || listValueChangeListeners.length == 1) &&
                (valueChangeListeners == null || valueChangeListeners.length == 1) &&
                (arglessValueChangeListeners == null || arglessValueChangeListeners.length == 1)
        )
            return new ListMonoListenerHolder<>(listValueChangeListeners == null ? null : listValueChangeListeners[0], valueChangeListeners == null ? null : valueChangeListeners[0], arglessValueChangeListeners == null ? null : arglessValueChangeListeners[0], newListeners[0]);

        invalidationListeners = newListeners;
        return this;
    }

    @Override
    public void fireListChangeListeners(ObservableValue<? extends T> observable, T oldValue, T newValue)
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
