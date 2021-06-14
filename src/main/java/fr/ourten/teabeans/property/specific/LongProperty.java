package fr.ourten.teabeans.property.specific;

import fr.ourten.teabeans.property.PropertyBase;
import fr.ourten.teabeans.value.specific.LongValue;

import java.util.Objects;

public class LongProperty extends PropertyBase<Number> implements LongValue
{
    protected long value;
    protected long oldValue;

    public LongProperty()
    {
        this(0);
    }

    public LongProperty(long value)
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
    public Long getValue()
    {
        return get();
    }

    @Override
    public void setValue(Number value)
    {
        if (isBound())
            throw new UnsupportedOperationException("Cannot set the value of a bound property");
        set((long) value);
    }

    @Override
    protected void setPropertyValue(Number value)
    {
        this.value = (long) value;
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

    public void set(long value)
    {
        if (isBound())
            throw new UnsupportedOperationException("Cannot set the value of a bound property");
        this.value = value;
        invalidate();
    }

    @Override
    public long get()
    {
        return observable == null ? value : observable.getValue().longValue();
    }
}
