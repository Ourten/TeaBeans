package fr.ourten.teabeans.value.specific;

import fr.ourten.teabeans.binding.specific.LongBinding;
import fr.ourten.teabeans.binding.specific.LongExpression;
import fr.ourten.teabeans.value.ObservableValue;

public interface LongValue extends ObservableValue<Long>
{
    long get();

    default ObservableValue<? extends Number> add(ObservableValue<? extends Number> term)
    {
        if (term instanceof LongValue)
            return LongExpression.getExpression(() -> get() + ((LongValue) term).get(), term, this);

        return combine(term, (a, b) ->
        {
            if (b instanceof Integer)
                return a + (Integer) b;
            if (b instanceof Long)
                return a + (Long) b;
            if (b instanceof Long)
                return a + (Long) b;
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
        if (term instanceof LongValue)
            return LongExpression.getExpression(() -> get() - ((LongValue) term).get(), term, this);

        return combine(term, (a, b) ->
        {
            if (b instanceof Integer)
                return a - (Integer) b;
            if (b instanceof Long)
                return a - (Long) b;
            if (b instanceof Long)
                return a - (Long) b;
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
        if (factor instanceof LongValue)
            return LongExpression.getExpression(() -> get() * ((LongValue) factor).get(), factor, this);

        return combine(factor, (a, b) ->
        {
            if (b instanceof Integer)
                return a * (Integer) b;
            if (b instanceof Long)
                return a * (Long) b;
            if (b instanceof Long)
                return a * (Long) b;
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
        if (divisor instanceof LongValue)
            return LongExpression.getExpression(() -> get() / ((LongValue) divisor).get(), divisor, this);

        return combine(divisor, (a, b) ->
        {
            if (b instanceof Integer)
                return a / (Integer) b;
            if (b instanceof Long)
                return a / (Long) b;
            if (b instanceof Long)
                return a / (Long) b;
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
        if (toCompare instanceof LongValue)
            return LongExpression.getExpression(() -> Math.max(get(), ((LongValue) toCompare).get()), toCompare, this);

        return combine(toCompare, (a, b) ->
        {
            if (b instanceof Integer)
                return Math.max(a, (Integer) b);
            if (b instanceof Long)
                return Math.max(a, (Long) b);
            if (b instanceof Long)
                return Math.max(a, (Long) b);
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
        if (toCompare instanceof LongValue)
            return LongExpression.getExpression(() -> Math.min(get(), ((LongValue) toCompare).get()), toCompare, this);

        return combine(toCompare, (a, b) ->
        {
            if (b instanceof Integer)
                return Math.min(a, (Integer) b);
            if (b instanceof Long)
                return Math.min(a, (Long) b);
            if (b instanceof Long)
                return Math.min(a, (Long) b);
            if (b instanceof Long)
                return Math.min(a, (Long) b);
            if (b instanceof Short)
                return Math.min(a, (Short) b);
            if (b instanceof Byte)
                return Math.min(a, (Byte) b);
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
