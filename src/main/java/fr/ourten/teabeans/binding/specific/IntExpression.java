package fr.ourten.teabeans.binding.specific;

import fr.ourten.teabeans.value.Observable;

import java.util.Objects;
import java.util.function.IntSupplier;

public class IntExpression extends IntBinding
{
    private IntSupplier closure;

    public IntExpression(IntSupplier closure, Observable... dependencies)
    {
        this.closure = closure;
        super.bind(dependencies);
    }

    @Override
    protected int computeValue()
    {
        return closure.getAsInt();
    }

    public void setClosure(IntSupplier closure)
    {
        Objects.requireNonNull(closure);

        this.closure = closure;
        invalidate();
    }

    public static IntExpression getExpression(IntSupplier closure,
                                              Observable... dependencies)
    {
        Objects.requireNonNull(closure);
        return new IntExpression(closure, dependencies);
    }
}
