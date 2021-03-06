package fr.ourten.teabeans.value.specific;

import fr.ourten.teabeans.binding.specific.FloatBinding;
import fr.ourten.teabeans.binding.specific.FloatExpression;
import fr.ourten.teabeans.binding.specific.IntBinding;
import fr.ourten.teabeans.binding.specific.IntExpression;
import fr.ourten.teabeans.value.ObservableValue;

public interface FloatValue extends ObservableValue<Float>
{
    float get();

    default ObservableValue<? extends Number> add(ObservableValue<? extends Number> term)
    {
        if (term instanceof FloatValue)
            return FloatExpression.getExpression(() -> get() + ((FloatValue) term).get(), term, this);

        return combine(term, (a, b) ->
        {
            if (b instanceof Integer)
                return a + (Integer) b;
            if (b instanceof Float)
                return a + (Float) b;
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
        if (term instanceof FloatValue)
            return FloatExpression.getExpression(() -> get() - ((FloatValue) term).get(), term, this);

        return combine(term, (a, b) ->
        {
            if (b instanceof Integer)
                return a - (Integer) b;
            if (b instanceof Float)
                return a - (Float) b;
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
        if (factor instanceof FloatValue)
            return FloatExpression.getExpression(() -> get() * ((FloatValue) factor).get(), factor, this);

        return combine(factor, (a, b) ->
        {
            if (b instanceof Integer)
                return a * (Integer) b;
            if (b instanceof Float)
                return a * (Float) b;
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
        if (divisor instanceof FloatValue)
            return FloatExpression.getExpression(() -> get() / ((FloatValue) divisor).get(), divisor, this);

        return combine(divisor, (a, b) ->
        {
            if (b instanceof Integer)
                return a / (Integer) b;
            if (b instanceof Float)
                return a / (Float) b;
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
        if (toCompare instanceof FloatValue)
            return FloatExpression.getExpression(() -> Math.max(get(), ((FloatValue) toCompare).get()), toCompare, this);

        return combine(toCompare, (a, b) ->
        {
            if (b instanceof Integer)
                return Math.max(a, (Integer) b);
            if (b instanceof Float)
                return Math.max(a, (Float) b);
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
        if (toCompare instanceof FloatValue)
            return FloatExpression.getExpression(() -> Math.min(get(), ((FloatValue) toCompare).get()), toCompare, this);

        return combine(toCompare, (a, b) ->
        {
            if (b instanceof Integer)
                return Math.min(a, (Integer) b);
            if (b instanceof Float)
                return Math.min(a, (Float) b);
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
