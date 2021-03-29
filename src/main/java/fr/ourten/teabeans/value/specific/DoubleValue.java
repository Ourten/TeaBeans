package fr.ourten.teabeans.value.specific;

import fr.ourten.teabeans.binding.specific.DoubleBinding;
import fr.ourten.teabeans.binding.specific.DoubleExpression;
import fr.ourten.teabeans.binding.specific.LongBinding;
import fr.ourten.teabeans.binding.specific.LongExpression;
import fr.ourten.teabeans.value.ObservableValue;

public interface DoubleValue extends ObservableValue<Number>
{
    double get();

    default ObservableValue<Number> add(int term)
    {
        return DoubleExpression.getExpression(() -> get() + term);
    }

    default ObservableValue<Number> add(long term)
    {
        return DoubleExpression.getExpression(() -> get() + term);
    }

    default ObservableValue<Number> add(float term)
    {
        return DoubleExpression.getExpression(() -> get() + term);
    }

    default ObservableValue<Number> add(double term)
    {
        return DoubleExpression.getExpression(() -> get() + term);
    }

    default ObservableValue<Number> subtract(int term)
    {
        return DoubleExpression.getExpression(() -> get() - term);
    }

    default ObservableValue<Number> subtract(long term)
    {
        return DoubleExpression.getExpression(() -> get() - term);
    }

    default ObservableValue<Number> subtract(float term)
    {
        return DoubleExpression.getExpression(() -> get() - term);
    }

    default ObservableValue<Number> subtract(double term)
    {
        return DoubleExpression.getExpression(() -> get() - term);
    }

    default ObservableValue<Number> multiply(int term)
    {
        return DoubleExpression.getExpression(() -> get() * term);
    }

    default ObservableValue<Number> multiply(long term)
    {
        return DoubleExpression.getExpression(() -> get() * term);
    }

    default ObservableValue<Number> multiply(float term)
    {
        return DoubleExpression.getExpression(() -> get() * term);
    }

    default ObservableValue<Number> multiply(double term)
    {
        return DoubleExpression.getExpression(() -> get() * term);
    }

    default ObservableValue<Number> divide(int term)
    {
        return DoubleExpression.getExpression(() -> get() / term);
    }

    default ObservableValue<Number> divide(long term)
    {
        return DoubleExpression.getExpression(() -> get() / term);
    }

    default ObservableValue<Number> divide(float term)
    {
        return DoubleExpression.getExpression(() -> get() / term);
    }

    default ObservableValue<Number> divide(double term)
    {
        return DoubleExpression.getExpression(() -> get() / term);
    }

    default ObservableValue<Number> add(ObservableValue<? extends Number> term)
    {
        if (term instanceof DoubleValue)
            return DoubleExpression.getExpression(() -> get() + ((DoubleValue) term).get(), term, this);

        return combine(term, (a, b) ->
        {
            if (b instanceof Integer)
                return (double) a + (Integer) b;
            if (b instanceof Float)
                return (double) a + (Float) b;
            if (b instanceof Double)
                return (double) a + (Double) b;
            if (b instanceof Long)
                return (double) a + (Long) b;
            if (b instanceof Short)
                return (double) a + (Short) b;
            if (b instanceof Byte)
                return (double) a + (Byte) b;
            return 0;
        });
    }

    default ObservableValue<Number> subtract(ObservableValue<? extends Number> term)
    {
        if (term instanceof DoubleValue)
            return DoubleExpression.getExpression(() -> get() - ((DoubleValue) term).get(), term, this);

        return combine(term, (a, b) ->
        {
            if (b instanceof Integer)
                return (double) a - (Integer) b;
            if (b instanceof Float)
                return (double) a - (Float) b;
            if (b instanceof Double)
                return (double) a - (Double) b;
            if (b instanceof Long)
                return (double) a - (Long) b;
            if (b instanceof Short)
                return (double) a - (Short) b;
            if (b instanceof Byte)
                return (double) a - (Byte) b;
            return 0;
        });
    }

    default ObservableValue<Number> multiply(ObservableValue<? extends Number> factor)
    {
        if (factor instanceof DoubleValue)
            return DoubleExpression.getExpression(() -> get() * ((DoubleValue) factor).get(), factor, this);

        return combine(factor, (a, b) ->
        {
            if (b instanceof Integer)
                return (double) a * (Integer) b;
            if (b instanceof Float)
                return (double) a * (Float) b;
            if (b instanceof Double)
                return (double) a * (Double) b;
            if (b instanceof Long)
                return (double) a * (Long) b;
            if (b instanceof Short)
                return (double) a * (Short) b;
            if (b instanceof Byte)
                return (double) a * (Byte) b;
            return 0;
        });
    }

    default ObservableValue<Number> divide(ObservableValue<? extends Number> divisor)
    {
        if (divisor instanceof DoubleValue)
            return DoubleExpression.getExpression(() -> get() / ((DoubleValue) divisor).get(), divisor, this);

        return combine(divisor, (a, b) ->
        {
            if (b instanceof Integer)
                return (double) a / (Integer) b;
            if (b instanceof Float)
                return (double) a / (Float) b;
            if (b instanceof Double)
                return (double) a / (Double) b;
            if (b instanceof Long)
                return (double) a / (Long) b;
            if (b instanceof Short)
                return (double) a / (Short) b;
            if (b instanceof Byte)
                return (double) a / (Byte) b;
            return 0;
        });
    }

    default ObservableValue<Number> max(ObservableValue<? extends Number> toCompare)
    {
        if (toCompare instanceof DoubleValue)
            return DoubleExpression.getExpression(() -> Math.max(get(), ((DoubleValue) toCompare).get()), toCompare, this);

        return combine(toCompare, (a, b) ->
        {
            if (b instanceof Integer)
                return Math.max((double) a, (Integer) b);
            if (b instanceof Float)
                return Math.max((double) a, (Float) b);
            if (b instanceof Double)
                return Math.max((double) a, (Double) b);
            if (b instanceof Long)
                return Math.max((double) a, (Long) b);
            if (b instanceof Short)
                return Math.max((double) a, (Short) b);
            if (b instanceof Byte)
                return Math.max((double) a, (Byte) b);
            return 0;
        });
    }

    default ObservableValue<Number> min(ObservableValue<? extends Number> toCompare)
    {
        if (toCompare instanceof DoubleValue)
            return DoubleExpression.getExpression(() -> Math.min(get(), ((DoubleValue) toCompare).get()), toCompare, this);

        return combine(toCompare, (a, b) ->
        {
            if (b instanceof Integer)
                return Math.min((double) a, (Integer) b);
            if (b instanceof Float)
                return Math.min((double) a, (Float) b);
            if (b instanceof Double)
                return Math.min((double) a, (Double) b);
            if (b instanceof Long)
                return Math.min((double) a, (Long) b);
            if (b instanceof Short)
                return Math.min((double) a, (Short) b);
            if (b instanceof Byte)
                return Math.min((double) a, (Byte) b);
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
