package fr.ourten.teabeans.value.specific;

import fr.ourten.teabeans.binding.specific.DoubleExpression;
import fr.ourten.teabeans.binding.specific.FloatExpression;
import fr.ourten.teabeans.binding.specific.IntBinding;
import fr.ourten.teabeans.binding.specific.IntExpression;
import fr.ourten.teabeans.binding.specific.LongExpression;
import fr.ourten.teabeans.value.ObservableValue;

public interface IntValue extends ObservableValue<Number>
{
    int get();

    default ObservableValue<Number> add(int term)
    {
        return IntExpression.getExpression(() -> get() + term);
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
        return IntExpression.getExpression(() -> get() - term);
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
        return IntExpression.getExpression(() -> get() * term);
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
        return IntExpression.getExpression(() -> get() / term);
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
        if (term instanceof IntValue)
            return IntExpression.getExpression(() -> get() + ((IntValue) term).get(), term, this);

        return combine(term, (a, b) ->
        {
            if (b instanceof Integer)
                return (int) a + (Integer) b;
            if (b instanceof Float)
                return (int) a + (Float) b;
            if (b instanceof Double)
                return (int) a + (Double) b;
            if (b instanceof Long)
                return (int) a + (Long) b;
            if (b instanceof Short)
                return (int) a + (Short) b;
            if (b instanceof Byte)
                return (int) a + (Byte) b;
            return 0;
        });
    }

    default ObservableValue<Number> subtract(ObservableValue<? extends Number> term)
    {
        if (term instanceof IntValue)
            return IntExpression.getExpression(() -> get() - ((IntValue) term).get(), term, this);

        return combine(term, (a, b) ->
        {
            if (b instanceof Integer)
                return (int) a - (Integer) b;
            if (b instanceof Float)
                return (int) a - (Float) b;
            if (b instanceof Double)
                return (int) a - (Double) b;
            if (b instanceof Long)
                return (int) a - (Long) b;
            if (b instanceof Short)
                return (int) a - (Short) b;
            if (b instanceof Byte)
                return (int) a - (Byte) b;
            return 0;
        });
    }

    default ObservableValue<Number> multiply(ObservableValue<? extends Number> factor)
    {
        if (factor instanceof IntValue)
            return IntExpression.getExpression(() -> get() * ((IntValue) factor).get(), factor, this);

        return combine(factor, (a, b) ->
        {
            if (b instanceof Integer)
                return (int) a * (Integer) b;
            if (b instanceof Float)
                return (int) a * (Float) b;
            if (b instanceof Double)
                return (int) a * (Double) b;
            if (b instanceof Long)
                return (int) a * (Long) b;
            if (b instanceof Short)
                return (int) a * (Short) b;
            if (b instanceof Byte)
                return (int) a * (Byte) b;
            return 0;
        });
    }

    default ObservableValue<Number> divide(ObservableValue<? extends Number> divisor)
    {
        if (divisor instanceof IntValue)
            return IntExpression.getExpression(() -> get() / ((IntValue) divisor).get(), divisor, this);

        return combine(divisor, (a, b) ->
        {
            if (b instanceof Integer)
                return (int) a / (Integer) b;
            if (b instanceof Float)
                return (int) a / (Float) b;
            if (b instanceof Double)
                return (int) a / (Double) b;
            if (b instanceof Long)
                return (int) a / (Long) b;
            if (b instanceof Short)
                return (int) a / (Short) b;
            if (b instanceof Byte)
                return (int) a / (Byte) b;
            return 0;
        });
    }

    default ObservableValue<Number> max(ObservableValue<? extends Number> toCompare)
    {
        if (toCompare instanceof IntValue)
            return IntExpression.getExpression(() -> Math.max(get(), ((IntValue) toCompare).get()), toCompare, this);

        return combine(toCompare, (a, b) ->
        {
            if (b instanceof Integer)
                return Math.max((int) a, (Integer) b);
            if (b instanceof Float)
                return Math.max((int) a, (Float) b);
            if (b instanceof Double)
                return Math.max((int) a, (Double) b);
            if (b instanceof Long)
                return Math.max((int) a, (Long) b);
            if (b instanceof Short)
                return Math.max((int) a, (Short) b);
            if (b instanceof Byte)
                return Math.max((int) a, (Byte) b);
            return 0;
        });
    }

    default ObservableValue<Number> min(ObservableValue<? extends Number> toCompare)
    {
        if (toCompare instanceof IntValue)
            return IntExpression.getExpression(() -> Math.min(get(), ((IntValue) toCompare).get()), toCompare, this);

        return combine(toCompare, (a, b) ->
        {
            if (b instanceof Integer)
                return Math.min((int) a, (Integer) b);
            if (b instanceof Float)
                return Math.min((int) a, (Float) b);
            if (b instanceof Double)
                return Math.min((int) a, (Double) b);
            if (b instanceof Long)
                return Math.min((int) a, (Long) b);
            if (b instanceof Short)
                return Math.min((int) a, (Short) b);
            if (b instanceof Byte)
                return Math.min((int) a, (Byte) b);
            return 0;
        });
    }

    default IntBinding negate()
    {
        return IntExpression.getExpression(() -> -get(), this);
    }

    default IntBinding abs()
    {
        return IntExpression.getExpression(() -> Math.abs(get()), this);
    }
}
