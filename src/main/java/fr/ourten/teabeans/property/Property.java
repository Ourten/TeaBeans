package fr.ourten.teabeans.property;

import fr.ourten.teabeans.binding.BidirectionalBinding;
import fr.ourten.teabeans.binding.WeakObservableListener;
import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.value.ObservableValue;

import java.util.ArrayList;
import java.util.Objects;

public class Property<T> implements IProperty<T>
{
    /**
     * The list of attached listeners that need to be notified when the value
     * change.
     */
    protected final ArrayList<ValueChangeListener<? super T>> valueChangeListeners;
    protected final ArrayList<ValueInvalidationListener>      valueInvalidationListeners;

    /**
     * The listener used to bind this property to another.
     */
    private   ValueInvalidationListener    propertyInvalidator;
    private   ObservableValue<? extends T> observable;
    private   boolean                      isObserving;
    protected T                            value;

    protected T oldValue;

    private boolean isMuted;

    public Property(T value)
    {
        valueChangeListeners = new ArrayList<>();
        valueInvalidationListeners = new ArrayList<>();

        isObserving = false;
        this.value = value;
        oldValue = value;
    }

    @Override
    public void addListener(ValueChangeListener<? super T> listener)
    {
        if (!isObserving && observable != null)
            startObserving();
        if (!valueChangeListeners.contains(listener))
            valueChangeListeners.add(listener);
    }

    @Override
    public void removeListener(ValueChangeListener<? super T> listener)
    {
        valueChangeListeners.remove(listener);
        if (valueChangeListeners.isEmpty() && observable != null)
            stopObserving();
    }

    @Override
    public void addListener(ValueInvalidationListener listener)
    {
        if (!isObserving && observable != null)
            startObserving();
        if (!valueInvalidationListeners.contains(listener))
            valueInvalidationListeners.add(listener);
    }

    @Override
    public void removeListener(ValueInvalidationListener listener)
    {
        valueInvalidationListeners.remove(listener);
        if (valueInvalidationListeners.isEmpty() && observable != null)
            stopObserving();
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

    @Override
    public T getValue()
    {
        return observable == null ? value : observable.getValue();
    }

    @Override
    public void setValue(T value)
    {
        if (isBound())
            throw new UnsupportedOperationException("Cannot set the value of a bound property");
        setPropertyValue(value);
    }

    protected void setPropertyValue(T value)
    {
        this.value = value;
        invalidate();
    }

    @Override
    public void invalidate()
    {
        if (isMuted())
            return;

        if (!Objects.equals(value, oldValue))
            fireChangeListeners(oldValue, value);
        fireInvalidationListeners();

        oldValue = value;
    }

    @Override
    public void bindProperty(ObservableValue<? extends T> observable)
    {
        Objects.requireNonNull(observable, "Cannot bind to null");
        if (!observable.equals(this.observable))
        {
            unbind();
            this.observable = observable;
            if (propertyInvalidator == null)
                propertyInvalidator = new WeakObservableListener(this);
            if (!valueChangeListeners.isEmpty() || !valueInvalidationListeners.isEmpty())
                startObserving();

            if (isMuted())
                return;
            if (value == null || !value.equals(observable.getValue()))
                fireChangeListeners(value, observable.getValue());
            fireInvalidationListeners();
        }
    }

    @Override
    public void unbind()
    {
        if (observable != null)
        {
            value = observable.getValue();
            stopObserving();
            observable = null;
        }
    }

    @Override
    public boolean isBound()
    {
        return observable != null;
    }

    @Override
    public void bindBidirectional(IProperty<T> other)
    {
        new BidirectionalBinding<>(this, other);
    }

    @Override
    public void unbindBidirectional(IProperty<T> other)
    {
        BidirectionalBinding<T> binding = new BidirectionalBinding<>(this, other);
        binding.unbind();
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

    protected void startObserving()
    {
        isObserving = true;
        observable.addListener(propertyInvalidator);
    }

    protected void stopObserving()
    {
        isObserving = false;
        observable.removeListener(propertyInvalidator);
    }

    protected boolean isObserving()
    {
        return isObserving;
    }

    protected boolean hasObservable()
    {
        return observable != null;
    }
}