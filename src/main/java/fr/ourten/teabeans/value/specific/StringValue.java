package fr.ourten.teabeans.value.specific;

import fr.ourten.teabeans.binding.specific.BooleanBinding;
import fr.ourten.teabeans.binding.specific.BooleanExpression;
import fr.ourten.teabeans.binding.specific.IntBinding;
import fr.ourten.teabeans.binding.specific.IntExpression;
import fr.ourten.teabeans.value.ObservableValue;

public interface StringValue extends ObservableValue<String>
{
    default IntBinding length()
    {
        return IntExpression.getExpression(() -> isPresent() ? getValue().length() : 0, this);
    }

    default BooleanBinding startsWith(String prefix)
    {
        return BooleanExpression.getExpression(() -> isPresent() && getValue().startsWith(prefix), this);
    }

    default BooleanBinding endsWith(String prefix)
    {
        return BooleanExpression.getExpression(() -> isPresent() && getValue().endsWith(prefix), this);
    }

    default BooleanBinding equals(String target)
    {
        return BooleanExpression.getExpression(() -> isPresent() && getValue().equals(target), this);
    }

    default BooleanBinding equalsIgnoreCase(String target)
    {
        return BooleanExpression.getExpression(() -> isPresent() && getValue().equalsIgnoreCase(target), this);
    }

    default BooleanBinding contains(String pattern)
    {
        return BooleanExpression.getExpression(() -> isPresent() && getValue().contains(pattern), this);
    }

    default ObservableValue<String> add(String suffix)
    {
        return map(suffix, (value, closureSuffix) -> value + closureSuffix);
    }

    default BooleanBinding startsWith(ObservableValue<String> prefix)
    {
        return BooleanExpression.getExpression(() -> isPresent() && getValue().startsWith(prefix.getValue()), this);
    }

    default BooleanBinding endsWith(ObservableValue<String> prefix)
    {
        return BooleanExpression.getExpression(() -> isPresent() && getValue().endsWith(prefix.getValue()), this);
    }

    default BooleanBinding equals(ObservableValue<String> target)
    {
        return BooleanExpression.getExpression(() -> isPresent() && getValue().equals(target.getValue()), this);
    }

    default BooleanBinding equalsIgnoreCase(ObservableValue<String> target)
    {
        return BooleanExpression.getExpression(() -> isPresent() && getValue().equalsIgnoreCase(target.getValue()), this);
    }

    default BooleanBinding contains(ObservableValue<String> pattern)
    {
        return BooleanExpression.getExpression(() -> isPresent() && getValue().contains(pattern.getValue()), this);
    }

    default ObservableValue<String> add(ObservableValue<String> suffix)
    {
        return combine(suffix, (value, closureSuffix) -> value + closureSuffix);
    }
}
