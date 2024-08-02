package fr.ourten.teabeans.value.specific;

import fr.ourten.teabeans.binding.Binding;
import fr.ourten.teabeans.binding.Expression;
import fr.ourten.teabeans.binding.specific.DoubleBinding;
import fr.ourten.teabeans.binding.specific.DoubleExpression;
import fr.ourten.teabeans.binding.specific.LongBinding;
import fr.ourten.teabeans.binding.specific.LongExpression;
import fr.ourten.teabeans.value.ObservableValue;

public interface DoubleValue extends ObservableValue<Number>
{
    double get();

    default DoubleValue add(int term)
    {
        return DoubleExpression.getExpression(() -> get() + term, this);
    }

    default DoubleValue add(long term)
    {
        return DoubleExpression.getExpression(() -> get() + term, this);
    }

    default DoubleValue add(float term)
    {
        return DoubleExpression.getExpression(() -> get() + term, this);
    }

    default DoubleValue add(double term)
    {
        return DoubleExpression.getExpression(() -> get() + term, this);
    }

    default DoubleValue subtract(int term)
    {
        return DoubleExpression.getExpression(() -> get() - term, this);
    }

    default DoubleValue subtract(long term)
    {
        return DoubleExpression.getExpression(() -> get() - term, this);
    }

    default DoubleValue subtract(float term)
    {
        return DoubleExpression.getExpression(() -> get() - term, this);
    }

    default DoubleValue subtract(double term)
    {
        return DoubleExpression.getExpression(() -> get() - term, this);
    }

    default DoubleValue multiply(int term)
    {
        return DoubleExpression.getExpression(() -> get() * term, this);
    }

    default DoubleValue multiply(long term)
    {
        return DoubleExpression.getExpression(() -> get() * term, this);
    }

    default DoubleValue multiply(float term)
    {
        return DoubleExpression.getExpression(() -> get() * term, this);
    }

    default DoubleValue multiply(double term)
    {
        return DoubleExpression.getExpression(() -> get() * term, this);
    }

    default DoubleValue divide(int term)
    {
        return DoubleExpression.getExpression(() -> get() / term, this);
    }

    default DoubleValue divide(long term)
    {
        return DoubleExpression.getExpression(() -> get() / term, this);
    }

    default DoubleValue divide(float term)
    {
        return DoubleExpression.getExpression(() -> get() / term, this);
    }

    default DoubleValue divide(double term)
    {
        return DoubleExpression.getExpression(() -> get() / term, this);
    }

    default DoubleValue add(FloatValue term)
    {
        return DoubleExpression.getExpression(() -> get() + term.get(), term, this);
    }

    default DoubleValue add(IntValue term)
    {
        return DoubleExpression.getExpression(() -> get() + term.get(), term, this);
    }

    default DoubleValue add(LongValue term)
    {
        return DoubleExpression.getExpression(() -> get() + term.get(), term, this);
    }

    default DoubleValue add(DoubleValue term)
    {
        return DoubleExpression.getExpression(() -> get() + term.get(), term, this);
    }

    default ObservableValue<Number> add(ObservableValue<Number> term)
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

    default DoubleValue subtract(FloatValue term)
    {
        return DoubleExpression.getExpression(() -> get() - term.get(), term, this);
    }

    default DoubleValue subtract(IntValue term)
    {
        return DoubleExpression.getExpression(() -> get() - term.get(), term, this);
    }

    default DoubleValue subtract(LongValue term)
    {
        return DoubleExpression.getExpression(() -> get() - term.get(), term, this);
    }

    default DoubleValue subtract(DoubleValue term)
    {
        return DoubleExpression.getExpression(() -> get() - term.get(), term, this);
    }

    default ObservableValue<Number> subtract(ObservableValue<Number> term)
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

    default DoubleValue multiply(FloatValue term)
    {
        return DoubleExpression.getExpression(() -> get() * term.get(), term, this);
    }

    default DoubleValue multiply(IntValue term)
    {
        return DoubleExpression.getExpression(() -> get() * term.get(), term, this);
    }

    default DoubleValue multiply(LongValue term)
    {
        return DoubleExpression.getExpression(() -> get() * term.get(), term, this);
    }

    default DoubleValue multiply(DoubleValue term)
    {
        return DoubleExpression.getExpression(() -> get() * term.get(), term, this);
    }

    default ObservableValue<Number> multiply(ObservableValue<Number> factor)
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

    default DoubleValue divide(FloatValue term)
    {
        return DoubleExpression.getExpression(() -> get() / term.get(), term, this);
    }

    default DoubleValue divide(IntValue term)
    {
        return DoubleExpression.getExpression(() -> get() / term.get(), term, this);
    }

    default DoubleValue divide(LongValue term)
    {
        return DoubleExpression.getExpression(() -> get() / term.get(), term, this);
    }

    default DoubleValue divide(DoubleValue term)
    {
        return DoubleExpression.getExpression(() -> get() / term.get(), term, this);
    }

    default ObservableValue<Number> divide(ObservableValue<Number> divisor)
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

    default DoubleValue max(FloatValue term)
    {
        return DoubleExpression.getExpression(() -> Math.max(get(), term.get()), term, this);
    }

    default DoubleValue max(IntValue term)
    {
        return DoubleExpression.getExpression(() -> Math.max(get(), term.get()), term, this);
    }

    default DoubleValue max(LongValue term)
    {
        return DoubleExpression.getExpression(() -> Math.max(get(), term.get()), term, this);
    }

    default DoubleValue max(DoubleValue term)
    {
        return DoubleExpression.getExpression(() -> Math.max(get(), term.get()), term, this);
    }

    default ObservableValue<Number> max(ObservableValue<Number> toCompare)
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

    default DoubleValue min(FloatValue term)
    {
        return DoubleExpression.getExpression(() -> Math.min(get(), term.get()), term, this);
    }

    default DoubleValue min(IntValue term)
    {
        return DoubleExpression.getExpression(() -> Math.min(get(), term.get()), term, this);
    }

    default DoubleValue min(LongValue term)
    {
        return DoubleExpression.getExpression(() -> Math.min(get(), term.get()), term, this);
    }

    default DoubleValue min(DoubleValue term)
    {
        return DoubleExpression.getExpression(() -> Math.min(get(), term.get()), term, this);
    }

    default ObservableValue<Number> min(ObservableValue<Number> toCompare)
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

    default Binding<String> asString()
    {
        return Expression.getExpression(() -> String.valueOf(get()), this);
    }
}
