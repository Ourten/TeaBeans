package fr.ourten.teabeans.value.specific;

import fr.ourten.teabeans.binding.specific.BooleanBinding;
import fr.ourten.teabeans.binding.specific.BooleanExpression;
import fr.ourten.teabeans.value.ObservableValue;

public interface BooleanValue extends ObservableValue<Boolean>
{
    boolean get();

    default BooleanBinding not()
    {
        return BooleanExpression.getExpression(() -> !get(), this);
    }

    default BooleanBinding and(ObservableValue<Boolean> term)
    {
        if (term instanceof BooleanValue)
            return BooleanExpression.getExpression(() -> get() && ((BooleanValue) term).get(), this, term);

        return BooleanExpression.getExpression(() -> get() && term.getValue(), this, term);
    }

    default BooleanBinding or(ObservableValue<Boolean> term)
    {
        if (term instanceof BooleanValue)
            return BooleanExpression.getExpression(() -> get() || ((BooleanValue) term).get(), this, term);

        return BooleanExpression.getExpression(() -> get() || term.getValue(), this, term);
    }

    default BooleanBinding xor(ObservableValue<Boolean> term)
    {
        if (term instanceof BooleanValue)
            return BooleanExpression.getExpression(() -> get() ^ ((BooleanValue) term).get(), this, term);

        return BooleanExpression.getExpression(() -> get() ^ term.getValue(), this, term);
    }
}
