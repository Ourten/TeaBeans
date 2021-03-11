package fr.ourten.teabeans.binding.specific;

import fr.ourten.teabeans.binding.BindingBase;
import fr.ourten.teabeans.value.specific.DoubleValue;

import java.util.Objects;

public abstract class DoubleBinding extends BindingBase<Double> implements DoubleValue
{
    protected double value;

    @Override
    public Double getValue()
    {
        return get();
    }

    @Override
    public double get()
    {
        if (!isValid())
        {
            double computed = computeValue();

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

    protected abstract double computeValue();
}
