package fr.ourten.teabeans.value.specific;

import fr.ourten.teabeans.binding.Binding;
import fr.ourten.teabeans.binding.Expression;
import fr.ourten.teabeans.binding.specific.DoubleExpression;
import fr.ourten.teabeans.binding.specific.FloatBinding;
import fr.ourten.teabeans.binding.specific.FloatExpression;
import fr.ourten.teabeans.binding.specific.IntBinding;
import fr.ourten.teabeans.binding.specific.IntExpression;
import fr.ourten.teabeans.value.ObservableValue;

public interface FloatValue extends ObservableValue<Number>
{
    float get();

    default FloatValue add(int term)
    {
        return FloatExpression.getExpression(() -> get() + term, this);
    }

    default FloatValue add(long term)
    {
        return FloatExpression.getExpression(() -> get() + term, this);
    }

    default FloatValue add(float term)
    {
        return FloatExpression.getExpression(() -> get() + term, this);
    }

    default DoubleValue add(double term)
    {
        return DoubleExpression.getExpression(() -> get() + term, this);
    }

    default FloatValue subtract(int term)
    {
        return FloatExpression.getExpression(() -> get() - term, this);
    }

    default FloatValue subtract(long term)
    {
        return FloatExpression.getExpression(() -> get() - term, this);
    }

    default FloatValue subtract(float term)
    {
        return FloatExpression.getExpression(() -> get() - term, this);
    }

    default DoubleValue subtract(double term)
    {
        return DoubleExpression.getExpression(() -> get() - term, this);
    }

    default FloatValue multiply(int term)
    {
        return FloatExpression.getExpression(() -> get() * term, this);
    }

    default FloatValue multiply(long term)
    {
        return FloatExpression.getExpression(() -> get() * term, this);
    }

    default FloatValue multiply(float term)
    {
        return FloatExpression.getExpression(() -> get() * term, this);
    }

    default DoubleValue multiply(double term)
    {
        return DoubleExpression.getExpression(() -> get() * term, this);
    }

    default FloatValue divide(int term)
    {
        return FloatExpression.getExpression(() -> get() / term, this);
    }

    default FloatValue divide(long term)
    {
        return FloatExpression.getExpression(() -> get() / term, this);
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

    default FloatValue add(IntValue term)
    {
        return FloatExpression.getExpression(() -> get() + term.get(), term, this);
    }

    default FloatValue add(LongValue term)
    {
        return FloatExpression.getExpression(() -> get() + term.get(), term, this);
    }

    default DoubleValue add(DoubleValue term)
    {
        return DoubleExpression.getExpression(() -> get() + term.get(), term, this);
    }

    default ObservableValue<Number> add(ObservableValue<Number> term)
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

    default FloatValue subtract(FloatValue term)
    {
        return FloatExpression.getExpression(() -> get() - term.get(), term, this);
    }

    default FloatValue subtract(IntValue term)
    {
        return FloatExpression.getExpression(() -> get() - term.get(), term, this);
    }

    default FloatValue subtract(LongValue term)
    {
        return FloatExpression.getExpression(() -> get() - term.get(), term, this);
    }

    default DoubleValue subtract(DoubleValue term)
    {
        return DoubleExpression.getExpression(() -> get() - term.get(), term, this);
    }

    default ObservableValue<Number> subtract(ObservableValue<Number> term)
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

    default FloatValue multiply(FloatValue term)
    {
        return FloatExpression.getExpression(() -> get() * term.get(), term, this);
    }

    default FloatValue multiply(IntValue term)
    {
        return FloatExpression.getExpression(() -> get() * term.get(), term, this);
    }

    default FloatValue multiply(LongValue term)
    {
        return FloatExpression.getExpression(() -> get() * term.get(), term, this);
    }

    default DoubleValue multiply(DoubleValue term)
    {
        return DoubleExpression.getExpression(() -> get() * term.get(), term, this);
    }

    default ObservableValue<Number> multiply(ObservableValue<Number> factor)
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

    default FloatValue divide(FloatValue term)
    {
        return FloatExpression.getExpression(() -> get() / term.get(), term, this);
    }

    default FloatValue divide(IntValue term)
    {
        return FloatExpression.getExpression(() -> get() / term.get(), term, this);
    }

    default FloatValue divide(LongValue term)
    {
        return FloatExpression.getExpression(() -> get() / term.get(), term, this);
    }

    default DoubleValue divide(DoubleValue term)
    {
        return DoubleExpression.getExpression(() -> get() / term.get(), term, this);
    }

    default ObservableValue<Number> divide(ObservableValue<Number> divisor)
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

    default FloatValue max(FloatValue term)
    {
        return FloatExpression.getExpression(() -> Math.max(get(), term.get()), term, this);
    }

    default FloatValue max(IntValue term)
    {
        return FloatExpression.getExpression(() -> Math.max(get(), term.get()), term, this);
    }

    default FloatValue max(LongValue term)
    {
        return FloatExpression.getExpression(() -> Math.max(get(), term.get()), term, this);
    }

    default DoubleValue max(DoubleValue term)
    {
        return DoubleExpression.getExpression(() -> Math.max(get(), term.get()), term, this);
    }

    default ObservableValue<Number> max(ObservableValue<Number> toCompare)
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

    default FloatValue min(FloatValue term)
    {
        return FloatExpression.getExpression(() -> Math.min(get(), term.get()), term, this);
    }

    default FloatValue min(IntValue term)
    {
        return FloatExpression.getExpression(() -> Math.min(get(), term.get()), term, this);
    }

    default FloatValue min(LongValue term)
    {
        return FloatExpression.getExpression(() -> Math.min(get(), term.get()), term, this);
    }

    default DoubleValue min(DoubleValue term)
    {
        return DoubleExpression.getExpression(() -> Math.min(get(), term.get()), term, this);
    }

    default ObservableValue<Number> min(ObservableValue<Number> toCompare)
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
    
    default Binding<String> asString()
    {
        return Expression.getExpression(() -> String.valueOf(get()), this);
    }
}
