package fr.ourten.teabeans.property.named;

import fr.ourten.teabeans.property.SetProperty;

import java.util.Set;
import java.util.function.Supplier;

public class NamedSetProperty<T> extends SetProperty<T> implements INamedProperty
{
    private final String name;

    public NamedSetProperty(Supplier<Set<T>> setSupplier, Set<T> value, String name)
    {
        super(setSupplier, value);

        this.name = name;
    }

    public NamedSetProperty(Set<T> value, String name)
    {
        super(value);

        this.name = name;
    }

    @Override
    public String getName()
    {
        return name;
    }
}
