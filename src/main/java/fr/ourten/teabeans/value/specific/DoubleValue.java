package fr.ourten.teabeans.value.specific;

import fr.ourten.teabeans.binding.specific.DoubleBinding;
import fr.ourten.teabeans.binding.specific.DoubleExpression;
import fr.ourten.teabeans.binding.specific.LongBinding;
import fr.ourten.teabeans.binding.specific.LongExpression;
import fr.ourten.teabeans.value.ObservableValue;

public interface DoubleValue extends ObservableValue<Double>
{
    double get();

    default ObservableValue<? extends Number> add(ObservableValue<? extends Number> term)
    {
        if (term instanceof DoubleValue)
            return DoubleExpression.getExpression(() -> get() + ((DoubleValue) term).get(), term, this);

        return combine(term, (a, b) ->
        {
            if (b instanceof Integer)
                return a + (Integer) b;
            if (b instanceof Double)
                return a + (Double) b;
            if (b instanceof Double)
                return a + (Double) b;
            if (b instanceof Long)
                return a + (Long) b;
            if (b instanceof Short)
                return a + (Short) b;
            if (b instanceof Byte)
                return a + (Byte) b;
            return 0;
        });
    }

    default ObservableValue<? extends Number> subtract(ObservableValue<? extends Number> term)
    {
        if (term instanceof DoubleValue)
            return DoubleExpression.getExpression(() -> get() - ((DoubleValue) term).get(), term, this);

        return combine(term, (a, b) ->
        {
            if (b instanceof Integer)
                return a - (Integer) b;
            if (b instanceof Double)
                return a - (Double) b;
            if (b instanceof Double)
                return a - (Double) b;
            if (b instanceof Long)
                return a - (Long) b;
            if (b instanceof Short)
                return a - (Short) b;
            if (b instanceof Byte)
                return a - (Byte) b;
            return 0;
        });
    }

    default ObservableValue<? extends Number> multiply(ObservableValue<? extends Number> factor)
    {
        if (factor instanceof DoubleValue)
            return DoubleExpression.getExpression(() -> get() * ((DoubleValue) factor).get(), factor, this);

        return combine(factor, (a, b) ->
        {
            if (b instanceof Integer)
                return a * (Integer) b;
            if (b instanceof Double)
                return a * (Double) b;
            if (b instanceof Double)
                return a * (Double) b;
            if (b instanceof Long)
                return a * (Long) b;
            if (b instanceof Short)
                return a * (Short) b;
            if (b instanceof Byte)
                return a * (Byte) b;
            return 0;
        });
    }

    default ObservableValue<? extends Number> divide(ObservableValue<? extends Number> divisor)
    {
        if (divisor instanceof DoubleValue)
            return DoubleExpression.getExpression(() -> get() / ((DoubleValue) divisor).get(), divisor, this);

        return combine(divisor, (a, b) ->
        {
            if (b instanceof Integer)
                return a / (Integer) b;
            if (b instanceof Double)
                return a / (Double) b;
            if (b instanceof Double)
                return a / (Double) b;
            if (b instanceof Long)
                return a / (Long) b;
            if (b instanceof Short)
                return a / (Short) b;
            if (b instanceof Byte)
                return a / (Byte) b;
            return 0;
        });
    }

    default ObservableValue<? extends Number> max(ObservableValue<? extends Number> toCompare)
    {
        if (toCompare instanceof DoubleValue)
            return DoubleExpression.getExpression(() -> Math.max(get(), ((DoubleValue) toCompare).get()), toCompare, this);

        return combine(toCompare, (a, b) ->
        {
            if (b instanceof Integer)
                return Math.max(a, (Integer) b);
            if (b instanceof Double)
                return Math.max(a, (Double) b);
            if (b instanceof Double)
                return Math.max(a, (Double) b);
            if (b instanceof Long)
                return Math.max(a, (Long) b);
            if (b instanceof Short)
                return Math.max(a, (Short) b);
            if (b instanceof Byte)
                return Math.max(a, (Byte) b);
            return 0;
        });
    }

    default ObservableValue<? extends Number> min(ObservableValue<? extends Number> toCompare)
    {
        if (toCompare instanceof DoubleValue)
            return DoubleExpression.getExpression(() -> Math.min(get(), ((DoubleValue) toCompare).get()), toCompare, this);

        return combine(toCompare, (a, b) ->
        {
            if (b instanceof Integer)
                return Math.min(a, (Integer) b);
            if (b instanceof Double)
                return Math.min(a, (Double) b);
            if (b instanceof Double)
                return Math.min(a, (Double) b);
            if (b instanceof Long)
                return Math.min(a, (Long) b);
            if (b instanceof Short)
                return Math.min(a, (Short) b);
            if (b instanceof Byte)
                return Math.min(a, (Byte) b);
            return 0;
        });
    }

    default DoubleBinding negate()
    {
        return DoubleExpression.getExpression(() -> -get(), this);
    }

    default DoubleBinding abs()
    {
        return DoubleExpression.getExpression(() -> Math.abs(get()), this);
    }

    default LongBinding ceil()
    {
        return LongExpression.getExpression(() -> (long) Math.ceil(get()), this);
    }

    default LongBinding floor()
    {
        return LongExpression.getExpression(() -> (long) Math.floor(get()), this);
    }

    default LongBinding round()
    {
        return LongExpression.getExpression(() -> Math.round(get()), this);
    }
}
