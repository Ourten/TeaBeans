package fr.ourten.teabeans.binding;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.value.Observable;

public abstract class BaseBinding<T> implements Binding<T>
{
    private final ArrayList<Observable>                     dependencies               = Lists.newArrayList();
    private final ArrayList<ValueChangeListener<? super T>> valueChangeListeners       = Lists.newArrayList();
    private final ArrayList<ValueInvalidationListener>      valueInvalidationListeners = Lists.newArrayList();
    private T                                               value;
    private boolean                                         isValid;
    private ValueInvalidationListener                       listener;

    @Override
    public void addListener(final ValueChangeListener<? super T> listener)
    {
        this.valueChangeListeners.add(listener);
    }

    @Override
    public void removeListener(final ValueChangeListener<? super T> listener)
    {
        this.valueChangeListeners.remove(listener);
    }

    @Override
    public void addListener(final ValueInvalidationListener listener)
    {
        this.valueInvalidationListeners.add(listener);
    }

    @Override
    public void removeListener(final ValueInvalidationListener listener)
    {
        this.valueInvalidationListeners.remove(listener);
    }

    @Override
    public T getValue()
    {
        if (this.value == null || !this.isValid)
        {
            final T computed = this.computeValue();
            if (!computed.equals(this.value))
                this.fireChangeListeners(this.value, computed);

            this.value = computed;
            this.isValid = true;
        }
        return this.value;
    }

    @Override
    public void bind(final Observable... observables)
    {
        if (this.listener == null)
            this.listener = observable -> BaseBinding.this.invalidate();
        for (final Observable observable : observables)
        {
            observable.addListener(this.listener);
            this.dependencies.add(observable);
        }
    }

    @Override
    public void unbind(final Observable... observables)
    {
        for (final Observable observable : observables)
        {
            if (this.listener != null)
                observable.removeListener(this.listener);
            this.dependencies.remove(observable);
        }
    }

    @Override
    public List<Observable> getDependencies()
    {
        return this.dependencies;
    }

    @Override
    public final boolean isValid()
    {
        return this.isValid;
    }

    @Override
    public void invalidate()
    {
        this.isValid = false;
        this.fireInvalidationListeners();
    }

    private void fireChangeListeners(final T oldValue, final T newValue)
    {
        for (final ValueChangeListener<? super T> listener : this.valueChangeListeners)
            listener.valueChanged(this, oldValue, newValue);
    }

    private void fireInvalidationListeners()
    {
        for (final ValueInvalidationListener listener : this.valueInvalidationListeners)
            listener.invalidated(this);
    }
}