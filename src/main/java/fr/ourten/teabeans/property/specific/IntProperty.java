package fr.ourten.teabeans.property.specific;

import fr.ourten.teabeans.property.PropertyBase;
import fr.ourten.teabeans.value.specific.IntValue;

import java.util.Objects;

public class IntProperty extends PropertyBase<Number> implements IntValue
{
    protected int value;
    protected int oldValue;

    public IntProperty()
    {
        this(0);
    }

    public IntProperty(int value)
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
    public Integer getValue()
    {
        return get();
    }

    @Override
    public void setValue(Number value)
    {
        if (isBound())
            throw new UnsupportedOperationException("Cannot set the value of a bound property");
        set((int) value);
    }

    @Override
    protected void setPropertyValue(Number value)
    {
        this.value = (int) value;
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

    public void set(int value)
    {
        if (isBound())
            throw new UnsupportedOperationException("Cannot set the value of a bound property");
        this.value = value;
        invalidate();
    }

    @Override
    public int get()
    {
        return observable == null ? value : observable.getValue().intValue();
    }
}
