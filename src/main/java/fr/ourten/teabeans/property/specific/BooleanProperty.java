package fr.ourten.teabeans.property.specific;

import fr.ourten.teabeans.property.PropertyBase;
import fr.ourten.teabeans.value.specific.BooleanValue;

import java.util.Objects;

public class BooleanProperty extends PropertyBase<Boolean> implements BooleanValue
{
    protected boolean value;
    protected boolean oldValue;

    public BooleanProperty()
    {
        this(false);
    }

    public BooleanProperty(boolean value)
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
    public Boolean getValue()
    {
        return get();
    }

    @Override
    public void setValue(Boolean value)
    {
        if (isBound())
            throw new UnsupportedOperationException("Cannot set the value of a bound property");
        set(value);
    }

    @Override
    protected void setPropertyValue(Boolean value)
    {
        this.value = value;
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

    public void set(boolean value)
    {
        if (isBound())
            throw new UnsupportedOperationException("Cannot set the value of a bound property");
        this.value = value;
        invalidate();
    }

    @Override
    public boolean get()
    {
        return observable == null ? value : observable.getValue();
    }
}
