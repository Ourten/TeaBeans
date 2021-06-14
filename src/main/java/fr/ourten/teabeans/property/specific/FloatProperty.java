package fr.ourten.teabeans.property.specific;

import fr.ourten.teabeans.property.PropertyBase;
import fr.ourten.teabeans.value.specific.FloatValue;

import java.util.Objects;

public class FloatProperty extends PropertyBase<Number> implements FloatValue
{
    protected float value;
    protected float oldValue;

    public FloatProperty()
    {
        this(0);
    }

    public FloatProperty(float value)
    {
        this.value = value;
        oldValue = value;
    }

    @Override
    protected void afterBindProperty()
    {
        if (!Objects.equals(observable.getValue(), value))
        {
            if (isPristine())
                setPristine(false);

            fireChangeArglessListeners();
            fireChangeListeners(value, observable.getValue());
        }
        fireInvalidationListeners();
    }

    @Override
    public Float getValue()
    {
        return get();
    }

    @Override
    public void setValue(Number value)
    {
        if (isBound())
            throw new UnsupportedOperationException("Cannot set the value of a bound property");
        set((float) value);
    }

    @Override
    protected void setPropertyValue(Number value)
    {
        this.value = (float) value;
        invalidate();

        if (isPristine())
            setPristine(false);
    }

    @Override
    public void invalidate()
    {
        if (isMuted())
            return;

        if (value != oldValue)
        {
            fireChangeArglessListeners();
            fireChangeListeners(oldValue, value);
        }
        fireInvalidationListeners();

        oldValue = value;
    }

    public void set(float value)
    {
        if (isBound())
            throw new UnsupportedOperationException("Cannot set the value of a bound property");
        this.value = value;
        invalidate();
    }

    @Override
    public float get()
    {
        return observable == null ? value : observable.getValue().floatValue();
    }
}
