package fr.ourten.teabeans.binding.specific;

import fr.ourten.teabeans.value.Observable;

import java.util.Objects;
import java.util.function.LongSupplier;

public class LongExpression extends LongBinding
{
    private LongSupplier closure;

    public LongExpression(LongSupplier closure, Observable... dependencies)
    {
        this.closure = closure;
        super.bind(dependencies);
    }

    @Override
    protected long computeValue()
    {
        return closure.getAsLong();
    }

    public void setClosure(LongSupplier closure)
    {
        Objects.requireNonNull(closure);

        this.closure = closure;
        invalidate();
    }

    public static LongExpression getExpression(LongSupplier closure,
                                               Observable... dependencies)
    {
        Objects.requireNonNull(closure);
        return new LongExpression(closure, dependencies);
    }
}
