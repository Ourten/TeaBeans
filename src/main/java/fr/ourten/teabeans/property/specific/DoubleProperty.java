package fr.ourten.teabeans.property.specific;

import fr.ourten.teabeans.property.PropertyBase;
import fr.ourten.teabeans.value.specific.DoubleValue;

import java.util.Objects;

public class DoubleProperty extends PropertyBase<Double> implements DoubleValue
{
    protected double value;
    protected double oldValue;

    public DoubleProperty()
    {
        this(0);
    }

    public DoubleProperty(double value)
    {
        this.value = value;
        oldValue = value;
    }

    @Override
    protected void afterBindProperty()
    {
        if (!Objects.equals(observable.getValue(), value))
            fireChangeListeners(value, observable.getValue());
        fireInvalidationListeners();
    }

    @Override
    public Double getValue()
    {
        return get();
    }

    @Override
    public void setValue(Double value)
    {
        if (isBound())
            throw new UnsupportedOperationException("Cannot set the value of a bound property");
        set(value);
    }

    @Override
    protected void setPropertyValue(Double value)
    {
        this.value = value;
        invalidate();
    }

    @Override
    public void invalidate()
    {
        if (isMuted())
            return;

        if (value != oldValue)
            fireChangeListeners(oldValue, value);
        fireInvalidationListeners();

        oldValue = value;
    }

    public void set(double value)
    {
        if (isBound())
            throw new UnsupportedOperationException("Cannot set the value of a bound property");
        this.value = value;
        invalidate();
    }

    @Override
    public double get()
    {
        return observable == null ? value : observable.getValue();
    }
}
