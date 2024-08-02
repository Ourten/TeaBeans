package fr.ourten.teabeans.value.specific;

import fr.ourten.teabeans.binding.Binding;
import fr.ourten.teabeans.binding.Expression;
import fr.ourten.teabeans.binding.specific.DoubleExpression;
import fr.ourten.teabeans.binding.specific.FloatExpression;
import fr.ourten.teabeans.binding.specific.IntBinding;
import fr.ourten.teabeans.binding.specific.IntExpression;
import fr.ourten.teabeans.binding.specific.LongExpression;
import fr.ourten.teabeans.value.ObservableValue;

public interface IntValue extends ObservableValue<Number>
{
    int get();

    default IntValue add(int term)
    {
        return IntExpression.getExpression(() -> get() + term, this);
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

    default IntValue subtract(int term)
    {
        return IntExpression.getExpression(() -> get() - term, this);
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

    default IntValue multiply(int term)
    {
        return IntExpression.getExpression(() -> get() * term, this);
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

    default IntValue divide(int term)
    {
        return IntExpression.getExpression(() -> get() / term, this);
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

    default IntValue add(IntValue term)
    {
        return IntExpression.getExpression(() -> get() + term.get(), term, this);
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

    default FloatValue subtract(FloatValue term)
    {
        return FloatExpression.getExpression(() -> get() - term.get(), term, this);
    }

    default IntValue subtract(IntValue term)
    {
        return IntExpression.getExpression(() -> get() - term.get(), term, this);
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

    default FloatValue multiply(FloatValue term)
    {
        return FloatExpression.getExpression(() -> get() * term.get(), term, this);
    }

    default IntValue multiply(IntValue term)
    {
        return IntExpression.getExpression(() -> get() * term.get(), term, this);
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

    default FloatValue divide(FloatValue term)
    {
        return FloatExpression.getExpression(() -> get() / term.get(), term, this);
    }

    default IntValue divide(IntValue term)
    {
        return IntExpression.getExpression(() -> get() / term.get(), term, this);
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

    default FloatValue max(FloatValue term)
    {
        return FloatExpression.getExpression(() -> Math.max(get(), term.get()), term, this);
    }

    default IntValue max(IntValue term)
    {
        return IntExpression.getExpression(() -> Math.max(get(), term.get()), term, this);
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

    default FloatValue min(FloatValue term)
    {
        return FloatExpression.getExpression(() -> Math.min(get(), term.get()), term, this);
    }

    default IntValue min(IntValue term)
    {
        return IntExpression.getExpression(() -> Math.min(get(), term.get()), term, this);
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

    default Binding<String> asString()
    {
        return Expression.getExpression(() -> String.valueOf(get()), this);
    }
}
