package fr.ourten.teabeans.value.specific;

import fr.ourten.teabeans.binding.specific.DoubleExpression;
import fr.ourten.teabeans.binding.specific.FloatExpression;
import fr.ourten.teabeans.binding.specific.LongBinding;
import fr.ourten.teabeans.binding.specific.LongExpression;
import fr.ourten.teabeans.value.ObservableValue;

public interface LongValue extends ObservableValue<Number>
{
    long get();

    default ObservableValue<Number> add(int term)
    {
        return LongExpression.getExpression(() -> get() + term);
    }

    default ObservableValue<Number> add(long term)
    {
        return LongExpression.getExpression(() -> get() + term);
    }

    default ObservableValue<Number> add(float term)
    {
        return FloatExpression.getExpression(() -> get() + term);
    }

    default ObservableValue<Number> add(double term)
    {
        return DoubleExpression.getExpression(() -> get() + term);
    }

    default ObservableValue<Number> subtract(int term)
    {
        return LongExpression.getExpression(() -> get() - term);
    }

    default ObservableValue<Number> subtract(long term)
    {
        return LongExpression.getExpression(() -> get() - term);
    }

    default ObservableValue<Number> subtract(float term)
    {
        return FloatExpression.getExpression(() -> get() - term);
    }

    default ObservableValue<Number> subtract(double term)
    {
        return DoubleExpression.getExpression(() -> get() - term);
    }

    default ObservableValue<Number> multiply(int term)
    {
        return LongExpression.getExpression(() -> get() * term);
    }

    default ObservableValue<Number> multiply(long term)
    {
        return LongExpression.getExpression(() -> get() * term);
    }

    default ObservableValue<Number> multiply(float term)
    {
        return FloatExpression.getExpression(() -> get() * term);
    }

    default ObservableValue<Number> multiply(double term)
    {
        return DoubleExpression.getExpression(() -> get() * term);
    }

    default ObservableValue<Number> divide(int term)
    {
        return LongExpression.getExpression(() -> get() / term);
    }

    default ObservableValue<Number> divide(long term)
    {
        return LongExpression.getExpression(() -> get() / term);
    }

    default ObservableValue<Number> divide(float term)
    {
        return FloatExpression.getExpression(() -> get() / term);
    }

    default ObservableValue<Number> divide(double term)
    {
        return DoubleExpression.getExpression(() -> get() / term);
    }

    default ObservableValue<Number> add(ObservableValue<? extends Number> term)
    {
        if (term instanceof LongValue)
            return LongExpression.getExpression(() -> get() + ((LongValue) term).get(), term, this);

        return combine(term, (a, b) ->
        {
            if (b instanceof Integer)
                return (long) a + (Integer) b;
            if (b instanceof Long)
                return (long) a + (Long) b;
            if (b instanceof Float)
                return (long) a + (Float) b;
            if (b instanceof Double)
                return (long) a + (Double) b;
            if (b instanceof Short)
                return (long) a + (Short) b;
            if (b instanceof Byte)
                return (long) a + (Byte) b;
            return 0;
        });
    }

    default ObservableValue<Number> subtract(ObservableValue<? extends Number> term)
    {
        if (term instanceof LongValue)
            return LongExpression.getExpression(() -> get() - ((LongValue) term).get(), term, this);

        return combine(term, (a, b) ->
        {
            if (b instanceof Integer)
                return (long) a - (Integer) b;
            if (b instanceof Long)
                return (long) a - (Long) b;
            if (b instanceof Float)
                return (long) a - (Float) b;
            if (b instanceof Double)
                return (long) a - (Double) b;
            if (b instanceof Short)
                return (long) a - (Short) b;
            if (b instanceof Byte)
                return (long) a - (Byte) b;
            return 0;
        });
    }

    default ObservableValue<Number> multiply(ObservableValue<? extends Number> factor)
    {
        if (factor instanceof LongValue)
            return LongExpression.getExpression(() -> get() * ((LongValue) factor).get(), factor, this);

        return combine(factor, (a, b) ->
        {
            if (b instanceof Integer)
                return (long) a * (Integer) b;
            if (b instanceof Long)
                return (long) a * (Long) b;
            if (b instanceof Float)
                return (long) a * (Float) b;
            if (b instanceof Double)
                return (long) a * (Double) b;
            if (b instanceof Short)
                return (long) a * (Short) b;
            if (b instanceof Byte)
                return (long) a * (Byte) b;
            return 0;
        });
    }

    default ObservableValue<Number> divide(ObservableValue<? extends Number> divisor)
    {
        if (divisor instanceof LongValue)
            return LongExpression.getExpression(() -> get() / ((LongValue) divisor).get(), divisor, this);

        return combine(divisor, (a, b) ->
        {
            if (b instanceof Integer)
                return (long) a / (Integer) b;
            if (b instanceof Long)
                return (long) a / (Long) b;
            if (b instanceof Float)
                return (long) a / (Float) b;
            if (b instanceof Double)
                return (long) a / (Double) b;
            if (b instanceof Short)
                return (long) a / (Short) b;
            if (b instanceof Byte)
                return (long) a / (Byte) b;
            return 0;
        });
    }

    default ObservableValue<Number> max(ObservableValue<? extends Number> toCompare)
    {
        if (toCompare instanceof LongValue)
            return LongExpression.getExpression(() -> Math.max(get(), ((LongValue) toCompare).get()), toCompare, this);

        return combine(toCompare, (a, b) ->
        {
            if (b instanceof Integer)
                return Math.max((long) a, (Integer) b);
            if (b instanceof Long)
                return Math.max((long) a, (Long) b);
            if (b instanceof Float)
                return Math.max((long) a, (Float) b);
            if (b instanceof Double)
                return Math.max((long) a, (Double) b);
            if (b instanceof Short)
                return Math.max((long) a, (Short) b);
            if (b instanceof Byte)
                return Math.max((long) a, (Byte) b);
            return 0;
        });
    }

    default ObservableValue<Number> min(ObservableValue<? extends Number> toCompare)
    {
        if (toCompare instanceof LongValue)
            return LongExpression.getExpression(() -> Math.min(get(), ((LongValue) toCompare).get()), toCompare, this);

        return combine(toCompare, (a, b) ->
        {
            if (b instanceof Integer)
                return Math.min((long) a, (Integer) b);
            if (b instanceof Long)
                return Math.min((long) a, (Long) b);
            if (b instanceof Float)
                return Math.min((long) a, (Float) b);
            if (b instanceof Double)
                return Math.min((long) a, (Double) b);
            if (b instanceof Short)
                return Math.min((long) a, (Short) b);
            if (b instanceof Byte)
                return Math.min((long) a, (Byte) b);
            return 0;
        });
    }

    default LongBinding negate()
    {
        return LongExpression.getExpression(() -> -get(), this);
    }

    default LongBinding abs()
    {
        return LongExpression.getExpression(() -> Math.abs(get()), this);
    }
}
