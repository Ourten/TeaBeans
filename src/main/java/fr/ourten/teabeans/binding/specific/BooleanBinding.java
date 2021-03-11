package fr.ourten.teabeans.binding.specific;

import fr.ourten.teabeans.binding.BindingBase;
import fr.ourten.teabeans.value.specific.BooleanValue;

import java.util.Objects;

public abstract class BooleanBinding extends BindingBase<Boolean> implements BooleanValue
{
    protected boolean value;

    @Override
    public Boolean getValue()
    {
        return get();
    }

    @Override
    public boolean get()
    {
        if (!isValid())
        {
            boolean computed = computeValue();

            if (!isMuted())
            {
                if (!Objects.equals(computed, value))
                {
                    fireChangeArglessListeners();
                    fireChangeListeners(value, computed);
                }
            }
            value = computed;
            setValid(true);
        }
        return value;
    }

    protected abstract boolean computeValue();
}
