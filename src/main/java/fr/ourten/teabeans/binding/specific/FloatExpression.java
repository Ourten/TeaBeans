package fr.ourten.teabeans.binding.specific;

import fr.ourten.teabeans.function.FloatSupplier;
import fr.ourten.teabeans.value.Observable;

import java.util.Objects;

public class FloatExpression extends FloatBinding
{
    private FloatSupplier closure;

    public FloatExpression(FloatSupplier closure, Observable... dependencies)
    {
        this.closure = closure;
        super.bind(dependencies);
    }

    @Override
    float computeValue()
    {
        return closure.getAsFloat();
    }

    public void setClosure(FloatSupplier closure)
    {
        Objects.requireNonNull(closure);

        this.closure = closure;
        invalidate();
    }

    public static FloatExpression getExpression(FloatSupplier closure,
                                                Observable... dependencies)
    {
        Objects.requireNonNull(closure);
        return new FloatExpression(closure, dependencies);
    }
}
