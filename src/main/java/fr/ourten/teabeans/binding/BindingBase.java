package fr.ourten.teabeans.binding;

import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.value.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class BindingBase<T> implements IBinding<T>
{
    private final   ArrayList<Observable>                     dependencies                = new ArrayList<>();
    protected final ArrayList<ValueChangeListener<? super T>> valueChangeListeners        = new ArrayList<>();
    protected final ArrayList<ValueInvalidationListener>      valueInvalidationListeners  = new ArrayList<>();
    protected final ArrayList<ValueInvalidationListener>      valueChangeArglessListeners = new ArrayList<>();
    private         ValueInvalidationListener                 bindingInvalidator;

    private boolean isValid;

    private boolean isMuted;

    @Override
    public void addChangeListener(ValueChangeListener<? super T> listener)
    {
        valueChangeListeners.add(listener);
    }

    @Override
    public void removeChangeListener(ValueChangeListener<? super T> listener)
    {
        valueChangeListeners.remove(listener);
    }

    @Override
    public void addListener(ValueInvalidationListener listener)
    {
        valueInvalidationListeners.add(listener);
    }

    @Override
    public void removeListener(ValueInvalidationListener listener)
    {
        valueInvalidationListeners.remove(listener);
    }

    @Override
    public void addChangeListener(ValueInvalidationListener listener)
    {
        valueChangeArglessListeners.add(listener);
    }

    @Override
    public void removeChangeListener(ValueInvalidationListener listener)
    {
        valueChangeArglessListeners.remove(listener);
    }

    @Override
    public T getValue()
    {
        return null;
    }

    @Override
    public void bind(Observable... observables)
    {
        for (Observable obs : observables)
            Objects.requireNonNull(obs, "Cannot bind to null!");
        if (bindingInvalidator == null)
            bindingInvalidator = new WeakObservableListener(this);
        for (Observable observable : observables)
        {
            observable.addListener(bindingInvalidator);
            dependencies.add(observable);
        }
    }

    @Override
    public void unbind(Observable... observables)
    {
        for (Observable observable : observables)
        {
            if (bindingInvalidator != null)
                observable.removeListener(bindingInvalidator);
            dependencies.remove(observable);
        }
    }

    @Override
    public void unbindAll()
    {
        dependencies.forEach(obs -> obs.removeListener(bindingInvalidator));
        dependencies.clear();
    }

    @Override
    public List<Observable> getDependencies()
    {
        return dependencies;
    }

    @Override
    public final boolean isValid()
    {
        return isValid;
    }

    protected void setValid(boolean isValid)
    {
        this.isValid = isValid;
    }

    @Override
    public void invalidate()
    {
        isValid = false;
        fireInvalidationListeners();
    }

    @Override
    public void mute()
    {
        isMuted = true;
    }

    @Override
    public void unmute()
    {
        isMuted = false;
        invalidate();
    }

    @Override
    public boolean isMuted()
    {
        return isMuted;
    }

    protected void fireChangeListeners(T oldValue, T newValue)
    {
        for (int i = 0, valueChangeListenersSize = valueChangeListeners.size(); i < valueChangeListenersSize; i++)
        {
            ValueChangeListener<? super T> listener = valueChangeListeners.get(i);
            listener.valueChanged(this, oldValue, newValue);
        }
    }

    protected void fireInvalidationListeners()
    {
        for (int i = 0, valueInvalidationListenersSize = valueInvalidationListeners.size(); i < valueInvalidationListenersSize; i++)
        {
            ValueInvalidationListener listener = valueInvalidationListeners.get(i);
            listener.invalidated(this);
        }
    }

    protected void fireChangeArglessListeners()
    {
        for (int i = 0, valueChangeArglessListenersSize = valueChangeArglessListeners.size(); i < valueChangeArglessListenersSize; i++)
        {
            ValueInvalidationListener listener = valueChangeArglessListeners.get(i);
            listener.invalidated(this);
        }
    }
}
