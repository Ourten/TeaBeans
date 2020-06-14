package fr.ourten.teabeans.value;

import fr.ourten.teabeans.binding.BidirectionalBinding;
import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.BiFunction;

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
    private   BiFunction<T, T, T>          checker;
    protected T                            value;

    private boolean isMuted;
    private T       valueBeforeMute;

    public Property(T value)
    {
        valueChangeListeners = new ArrayList<>();
        valueInvalidationListeners = new ArrayList<>();

        isObserving = false;
        this.value = value;
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
        valueBeforeMute = getValue();
    }

    @Override
    public void unmute()
    {
        isMuted = false;
        invalidate(valueBeforeMute);
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

    @Override
    public T getValue()
    {
        return observable == null ? value : observable.getValue();
    }

    @Override
    public void setValue(T value)
    {
        if (isBound())
            throw new RuntimeException("Cannot set the value of a bound property");
        if (checker != null)
            value = checker.apply(this.value, value);
        setPropertyValue(value);
    }

    public BiFunction<T, T, T> getChecker()
    {
        return checker;
    }

    public void setChecker(BiFunction<T, T, T> checker)
    {
        this.checker = checker;
    }

    protected void setPropertyValue(T value)
    {
        T oldValue = this.value;
        this.value = value;
        invalidate(oldValue);
    }

    @Override
    public void invalidate(T oldValue)
    {
        if (isMuted())
            return;

        if (value == null || !value.equals(oldValue))
            fireChangeListeners(oldValue, value);
        fireInvalidationListeners();
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
                propertyInvalidator = obs -> setPropertyValue(Property.this.observable.getValue());
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

    private void startObserving()
    {
        isObserving = true;
        observable.addListener(propertyInvalidator);
    }

    private void stopObserving()
    {
        isObserving = false;
        observable.removeListener(propertyInvalidator);
    }
}