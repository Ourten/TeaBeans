package fr.ourten.teabeans.value.specific;

import fr.ourten.teabeans.binding.specific.DoubleExpression;
import fr.ourten.teabeans.binding.specific.FloatBinding;
import fr.ourten.teabeans.binding.specific.FloatExpression;
import fr.ourten.teabeans.binding.specific.IntBinding;
import fr.ourten.teabeans.binding.specific.IntExpression;
import fr.ourten.teabeans.value.ObservableValue;

public interface FloatValue extends ObservableValue<Number>
{
    float get();

    default ObservableValue<Number> add(int term)
    {
        return FloatExpression.getExpression(() -> get() + term);
    }

    default ObservableValue<Number> add(long term)
    {
        return FloatExpression.getExpression(() -> get() + term);
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
        return FloatExpression.getExpression(() -> get() - term);
    }

    default ObservableValue<Number> subtract(long term)
    {
        return FloatExpression.getExpression(() -> get() - term);
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
        return FloatExpression.getExpression(() -> get() * term);
    }

    default ObservableValue<Number> multiply(long term)
    {
        return FloatExpression.getExpression(() -> get() * term);
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
        return FloatExpression.getExpression(() -> get() / term);
    }

    default ObservableValue<Number> divide(long term)
    {
        return FloatExpression.getExpression(() -> get() / term);
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
        if (term instanceof FloatValue)
            return FloatExpression.getExpression(() -> get() + ((FloatValue) term).get(), term, this);

        return combine(term, (a, b) ->
        {
            if (b instanceof Integer)
                return (float) a + (Integer) b;
            if (b instanceof Float)
                return (float) a + (Float) b;
            if (b instanceof Double)
                return (float) a + (Double) b;
            if (b instanceof Long)
                return (float) a + (Long) b;
            if (b instanceof Short)
                return (float) a + (Short) b;
            if (b instanceof Byte)
                return (float) a + (Byte) b;
            return 0;
        });
    }

    default ObservableValue<Number> subtract(ObservableValue<? extends Number> term)
    {
        if (term instanceof FloatValue)
            return FloatExpression.getExpression(() -> get() - ((FloatValue) term).get(), term, this);

        return combine(term, (a, b) ->
        {
            if (b instanceof Integer)
                return (float) a - (Integer) b;
            if (b instanceof Float)
                return (float) a - (Float) b;
            if (b instanceof Double)
                return (float) a - (Double) b;
            if (b instanceof Long)
                return (float) a - (Long) b;
            if (b instanceof Short)
                return (float) a - (Short) b;
            if (b instanceof Byte)
                return (float) a - (Byte) b;
            return 0;
        });
    }

    default ObservableValue<Number> multiply(ObservableValue<? extends Number> factor)
    {
        if (factor instanceof FloatValue)
            return FloatExpression.getExpression(() -> get() * ((FloatValue) factor).get(), factor, this);

        return combine(factor, (a, b) ->
        {
            if (b instanceof Integer)
                return (float) a * (Integer) b;
            if (b instanceof Float)
                return (float) a * (Float) b;
            if (b instanceof Double)
                return (float) a * (Double) b;
            if (b instanceof Long)
                return (float) a * (Long) b;
            if (b instanceof Short)
                return (float) a * (Short) b;
            if (b instanceof Byte)
                return (float) a * (Byte) b;
            return 0;
        });
    }

    default ObservableValue<Number> divide(ObservableValue<? extends Number> divisor)
    {
        if (divisor instanceof FloatValue)
            return FloatExpression.getExpression(() -> get() / ((FloatValue) divisor).get(), divisor, this);

        return combine(divisor, (a, b) ->
        {
            if (b instanceof Integer)
                return (float) a / (Integer) b;
            if (b instanceof Float)
                return (float) a / (Float) b;
            if (b instanceof Double)
                return (float) a / (Double) b;
            if (b instanceof Long)
                return (float) a / (Long) b;
            if (b instanceof Short)
                return (float) a / (Short) b;
            if (b instanceof Byte)
                return (float) a / (Byte) b;
            return 0;
        });
    }

    default ObservableValue<Number> max(ObservableValue<? extends Number> toCompare)
    {
        if (toCompare instanceof FloatValue)
            return FloatExpression.getExpression(() -> Math.max(get(), ((FloatValue) toCompare).get()), toCompare, this);

        return combine(toCompare, (a, b) ->
        {
            if (b instanceof Integer)
                return Math.max((float) a, (Integer) b);
            if (b instanceof Float)
                return Math.max((float) a, (Float) b);
            if (b instanceof Double)
                return Math.max((float) a, (Double) b);
            if (b instanceof Long)
                return Math.max((float) a, (Long) b);
            if (b instanceof Short)
                return Math.max((float) a, (Short) b);
            if (b instanceof Byte)
                return Math.max((float) a, (Byte) b);
            return 0;
        });
    }

    default ObservableValue<Number> min(ObservableValue<? extends Number> toCompare)
    {
        if (toCompare instanceof FloatValue)
            return FloatExpression.getExpression(() -> Math.min(get(), ((FloatValue) toCompare).get()), toCompare, this);

        return combine(toCompare, (a, b) ->
        {
            if (b instanceof Integer)
                return Math.min((float) a, (Integer) b);
            if (b instanceof Float)
                return Math.min((float) a, (Float) b);
            if (b instanceof Double)
                return Math.min((float) a, (Double) b);
            if (b instanceof Long)
                return Math.min((float) a, (Long) b);
            if (b instanceof Short)
                return Math.min((float) a, (Short) b);
            if (b instanceof Byte)
                return Math.min((float) a, (Byte) b);
            return 0;
        });
    }

    default FloatBinding negate()
    {
        return FloatExpression.getExpression(() -> -get(), this);
    }

    default FloatBinding abs()
    {
        return FloatExpression.getExpression(() -> Math.abs(get()), this);
    }

    default IntBinding ceil()
    {
        return IntExpression.getExpression(() -> (int) Math.ceil(get()), this);
    }

    default IntBinding floor()
    {
        return IntExpression.getExpression(() -> (int) Math.floor(get()), this);
    }

    default IntBinding round()
    {
        return IntExpression.getExpression(() -> Math.round(get()), this);
    }
}
