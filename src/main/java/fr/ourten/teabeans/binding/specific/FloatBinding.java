package fr.ourten.teabeans.binding.specific;

import fr.ourten.teabeans.binding.BindingBase;
import fr.ourten.teabeans.value.specific.FloatValue;

import java.util.Objects;

public abstract class FloatBinding extends BindingBase<Float> implements FloatValue
{
    protected float value;

    @Override
    public Float getValue()
    {
        return get();
    }

    @Override
    public float get()
    {
        if (!isValid())
        {
            float computed = computeValue();

            if (!isMuted())
            {
                if (!Objects.equals(computed, value))
                    fireChangeListeners(value, computed);
            }
            value = computed;
            setValid(true);
        }
        return value;
    }

    abstract float computeValue();
}
