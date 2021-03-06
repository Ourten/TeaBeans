package fr.ourten.teabeans.property;

import java.util.Objects;

public class Property<T> extends PropertyBase<T>
{
    protected T value;
    protected T oldValue;

    public Property()
    {
        this(null);
    }

    public Property(T value)
    {
        this.value = value;
        oldValue = value;
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

    @Override
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
    protected void afterBindProperty()
    {
        if (value == null || !value.equals(observable.getValue()))
            fireChangeListeners(value, observable.getValue());
        fireInvalidationListeners();
    }
}