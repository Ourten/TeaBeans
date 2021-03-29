package fr.ourten.teabeans.binding.specific;

import fr.ourten.teabeans.binding.BindingBase;
import fr.ourten.teabeans.value.specific.IntValue;

import java.util.Objects;

public abstract class IntBinding extends BindingBase<Number> implements IntValue
{
    protected int value;

    @Override
    public Integer getValue()
    {
        return get();
    }

    @Override
    public int get()
    {
        if (!isValid())
        {
            int computed = computeValue();

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

    protected abstract int computeValue();
}
