package fr.ourten.teabeans.value.specific;

import fr.ourten.teabeans.binding.Binding;
import fr.ourten.teabeans.binding.Expression;
import fr.ourten.teabeans.binding.specific.DoubleExpression;
import fr.ourten.teabeans.binding.specific.FloatExpression;
import fr.ourten.teabeans.binding.specific.LongBinding;
import fr.ourten.teabeans.binding.specific.LongExpression;
import fr.ourten.teabeans.value.ObservableValue;

public interface LongValue extends ObservableValue<Number>
{
    long get();

    default LongValue add(int term)
    {
        return LongExpression.getExpression(() -> get() + term, this);
    }

    default LongValue add(long term)
    {
        return LongExpression.getExpression(() -> get() + term, this);
    }

    default FloatValue add(float term)
    {
        return FloatExpression.getExpression(() -> get() + term, this);
    }

    default DoubleValue add(double term)
    {
        return DoubleExpression.getExpression(() -> get() + term, this);
    }

    default LongValue subtract(int term)
    {
        return LongExpression.getExpression(() -> get() - term, this);
    }

    default LongValue subtract(long term)
    {
        return LongExpression.getExpression(() -> get() - term, this);
    }

    default FloatValue subtract(float term)
    {
        return FloatExpression.getExpression(() -> get() - term, this);
    }

    default DoubleValue subtract(double term)
    {
        return DoubleExpression.getExpression(() -> get() - term, this);
    }

    default LongValue multiply(int term)
    {
        return LongExpression.getExpression(() -> get() * term, this);
    }

    default LongValue multiply(long term)
    {
        return LongExpression.getExpression(() -> get() * term, this);
    }

    default FloatValue multiply(float term)
    {
        return FloatExpression.getExpression(() -> get() * term, this);
    }

    default DoubleValue multiply(double term)
    {
        return DoubleExpression.getExpression(() -> get() * term, this);
    }

    default LongValue divide(int term)
    {
        return LongExpression.getExpression(() -> get() / term, this);
    }

    default LongValue divide(long term)
    {
        return LongExpression.getExpression(() -> get() / term, this);
    }

    default FloatValue divide(float term)
    {
        return FloatExpression.getExpression(() -> get() / term, this);
    }

    default DoubleValue divide(double term)
    {
        return DoubleExpression.getExpression(() -> get() / term, this);
    }

    default FloatValue add(FloatValue term)
    {
        return FloatExpression.getExpression(() -> get() + term.get(), term, this);
    }

    default LongValue add(IntValue term)
    {
        return LongExpression.getExpression(() -> get() + term.get(), term, this);
    }

    default LongValue add(LongValue term)
    {
        return LongExpression.getExpression(() -> get() + term.get(), term, this);
    }

    default DoubleValue add(DoubleValue term)
    {
        return DoubleExpression.getExpression(() -> get() + term.get(), term, this);
    }

    default ObservableValue<Number> add(ObservableValue<Number> term)
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

    default FloatValue subtract(FloatValue term)
    {
        return FloatExpression.getExpression(() -> get() - term.get(), term, this);
    }

    default LongValue subtract(IntValue term)
    {
        return LongExpression.getExpression(() -> get() - term.get(), term, this);
    }

    default LongValue subtract(LongValue term)
    {
        return LongExpression.getExpression(() -> get() - term.get(), term, this);
    }

    default DoubleValue subtract(DoubleValue term)
    {
        return DoubleExpression.getExpression(() -> get() - term.get(), term, this);
    }

    default ObservableValue<Number> subtract(ObservableValue<Number> term)
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

    default FloatValue multiply(FloatValue term)
    {
        return FloatExpression.getExpression(() -> get() * term.get(), term, this);
    }

    default LongValue multiply(IntValue term)
    {
        return LongExpression.getExpression(() -> get() * term.get(), term, this);
    }

    default LongValue multiply(LongValue term)
    {
        return LongExpression.getExpression(() -> get() * term.get(), term, this);
    }

    default DoubleValue multiply(DoubleValue term)
    {
        return DoubleExpression.getExpression(() -> get() * term.get(), term, this);
    }

    default ObservableValue<Number> multiply(ObservableValue<Number> factor)
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

    default FloatValue divide(FloatValue term)
    {
        return FloatExpression.getExpression(() -> get() / term.get(), term, this);
    }

    default LongValue divide(IntValue term)
    {
        return LongExpression.getExpression(() -> get() / term.get(), term, this);
    }

    default LongValue divide(LongValue term)
    {
        return LongExpression.getExpression(() -> get() / term.get(), term, this);
    }

    default DoubleValue divide(DoubleValue term)
    {
        return DoubleExpression.getExpression(() -> get() / term.get(), term, this);
    }

    default ObservableValue<Number> divide(ObservableValue<Number> divisor)
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

    default FloatValue max(FloatValue term)
    {
        return FloatExpression.getExpression(() -> Math.max(get(), term.get()), term, this);
    }

    default LongValue max(IntValue term)
    {
        return LongExpression.getExpression(() -> Math.max(get(), term.get()), term, this);
    }

    default LongValue max(LongValue term)
    {
        return LongExpression.getExpression(() -> Math.max(get(), term.get()), term, this);
    }

    default DoubleValue max(DoubleValue term)
    {
        return DoubleExpression.getExpression(() -> Math.max(get(), term.get()), term, this);
    }

    default ObservableValue<Number> max(ObservableValue<Number> toCompare)
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

    default FloatValue min(FloatValue term)
    {
        return FloatExpression.getExpression(() -> Math.min(get(), term.get()), term, this);
    }

    default LongValue min(IntValue term)
    {
        return LongExpression.getExpression(() -> Math.min(get(), term.get()), term, this);
    }

    default LongValue min(LongValue term)
    {
        return LongExpression.getExpression(() -> Math.min(get(), term.get()), term, this);
    }

    default DoubleValue min(DoubleValue term)
    {
        return DoubleExpression.getExpression(() -> Math.min(get(), term.get()), term, this);
    }

    default ObservableValue<Number> min(ObservableValue<Number> toCompare)
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

    default Binding<String> asString()
    {
        return Expression.getExpression(() -> String.valueOf(get()), this);
    }
}
