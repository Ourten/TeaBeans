package fr.ourten.teabeans.binding;

import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.value.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Binding<T> implements IBinding<T>
{
    private final   ArrayList<Observable>                     dependencies               = new ArrayList<>();
    protected final ArrayList<ValueChangeListener<? super T>> valueChangeListeners       = new ArrayList<>();
    protected final ArrayList<ValueInvalidationListener>      valueInvalidationListeners = new ArrayList<>();
    private         ValueInvalidationListener                 listener;

    protected T       value;
    private   boolean isValid;

    private boolean isMuted;

    @Override
    public void addListener(ValueChangeListener<? super T> listener)
    {
        valueChangeListeners.add(listener);
    }

    @Override
    public void removeListener(ValueChangeListener<? super T> listener)
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
    public T getValue()
    {
        if (!isValid)
        {
            T computed = computeValue();

            if (!isMuted())
            {
                if (computed == null && value != null)
                    fireChangeListeners(value, computed);
                else if (computed != null && !computed.equals(value))
                    fireChangeListeners(value, computed);
            }
            value = computed;
            isValid = true;
        }
        return value;
    }

    @Override
    public void bind(Observable... observables)
    {
        for (Observable obs : observables)
            Objects.requireNonNull(obs, "Cannot bind to null!");
        if (listener == null)
            listener = observable -> invalidate();
        for (Observable observable : observables)
        {
            observable.addListener(listener);
            dependencies.add(observable);
        }
    }

    @Override
    public void unbind(Observable... observables)
    {
        for (Observable observable : observables)
        {
            if (listener != null)
                observable.removeListener(listener);
            dependencies.remove(observable);
        }
    }

    @Override
    public void unbindAll()
    {
        dependencies.forEach(obs -> obs.removeListener(listener));
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
    public void muteWhile(Runnable runnable)
    {
        mute();
        runnable.run();
        unmute();
    }

    @Override
    public boolean isMuted()
    {
        return isMuted;
    }

    protected void fireChangeListeners(T oldValue, T newValue)
    {
        for (ValueChangeListener<? super T> listener : valueChangeListeners)
            listener.valueChanged(this, oldValue, newValue);
    }

    protected void fireInvalidationListeners()
    {
        for (ValueInvalidationListener listener : valueInvalidationListeners)
            listener.invalidated(this);
    }
}