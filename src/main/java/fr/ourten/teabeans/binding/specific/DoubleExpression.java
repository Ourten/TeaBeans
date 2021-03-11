package fr.ourten.teabeans.binding.specific;

import fr.ourten.teabeans.value.Observable;

import java.util.Objects;
import java.util.function.DoubleSupplier;

public class DoubleExpression extends DoubleBinding
{
    private DoubleSupplier closure;

    public DoubleExpression(DoubleSupplier closure, Observable... dependencies)
    {
        this.closure = closure;
        super.bind(dependencies);
    }

    @Override
    protected double computeValue()
    {
        return closure.getAsDouble();
    }

    public void setClosure(DoubleSupplier closure)
    {
        Objects.requireNonNull(closure);

        this.closure = closure;
        invalidate();
    }

    public static DoubleExpression getExpression(DoubleSupplier closure,
                                                 Observable... dependencies)
    {
        Objects.requireNonNull(closure);
        return new DoubleExpression(closure, dependencies);
    }
}
