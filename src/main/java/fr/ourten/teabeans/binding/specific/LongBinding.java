package fr.ourten.teabeans.binding.specific;

import fr.ourten.teabeans.binding.BindingBase;
import fr.ourten.teabeans.value.specific.LongValue;

import java.util.Objects;

public abstract class LongBinding extends BindingBase<Long> implements LongValue
{
    protected long value;

    @Override
    public Long getValue()
    {
        return get();
    }

    @Override
    public long get()
    {
        if (!isValid())
        {
            long computed = computeValue();

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

    protected abstract long computeValue();
}
