package fr.ourten.teabeans.binding.specific;

import fr.ourten.teabeans.value.Observable;

import java.util.Objects;
import java.util.function.BooleanSupplier;

public class BooleanExpression extends BooleanBinding
{
    private BooleanSupplier closure;

    public BooleanExpression(BooleanSupplier closure, Observable... dependencies)
    {
        this.closure = closure;
        super.bind(dependencies);
    }

    @Override
    boolean computeValue()
    {
        return closure.getAsBoolean();
    }

    public void setClosure(BooleanSupplier closure)
    {
        Objects.requireNonNull(closure);

        this.closure = closure;
        invalidate();
    }

    public static BooleanExpression getExpression(BooleanSupplier closure,
                                                  Observable... dependencies)
    {
        Objects.requireNonNull(closure);
        return new BooleanExpression(closure, dependencies);
    }
}
