package fr.ourten.teabeans.binding;

import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.listener.WeakObservableListener;
import fr.ourten.teabeans.listener.holder.ListenersHolder;
import fr.ourten.teabeans.value.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class BindingBase<T> implements IBinding<T>
{
    private final ArrayList<Observable> dependencies = new ArrayList<>();

    protected ListenersHolder<T> listenersHolder;

    private ValueInvalidationListener bindingInvalidator;

    private boolean isValid;

    private boolean isMuted;

    @Override
    public void addChangeListener(ValueChangeListener<? super T> listener)
    {
        listenersHolder = ListenersHolder.addChangeListener(listenersHolder, listener);
    }

    @Override
    public void removeChangeListener(ValueChangeListener<? super T> listener)
    {
        listenersHolder = ListenersHolder.removeChangeListener(listenersHolder, listener);
    }

    @Override
    public void addListener(ValueInvalidationListener listener)
    {
        listenersHolder = ListenersHolder.addListener(listenersHolder, listener);
    }

    @Override
    public void removeListener(ValueInvalidationListener listener)
    {
        listenersHolder = ListenersHolder.removeListener(listenersHolder, listener);
    }

    @Override
    public void addChangeListener(ValueInvalidationListener listener)
    {
        listenersHolder = ListenersHolder.addChangeListener(listenersHolder, listener);
    }

    @Override
    public void removeChangeListener(ValueInvalidationListener listener)
    {
        listenersHolder = ListenersHolder.removeChangeListener(listenersHolder, listener);
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
        ListenersHolder.fireChangeListeners(listenersHolder, this, oldValue, newValue);
    }

    protected void fireInvalidationListeners()
    {
        ListenersHolder.fireInvalidationListeners(listenersHolder, this);
    }

    protected void fireChangeArglessListeners()
    {
        ListenersHolder.fireChangeArglessListeners(listenersHolder, this);
    }
}
