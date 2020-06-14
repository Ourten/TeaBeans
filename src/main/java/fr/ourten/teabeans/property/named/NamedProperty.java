package fr.ourten.teabeans.property.named;

import fr.ourten.teabeans.property.Property;

public class NamedProperty<T> extends Property<T> implements INamedProperty
{
    private final String name;

    public NamedProperty(T value, String name)
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
