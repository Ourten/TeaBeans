package fr.ourten.teabeans.property.named;

import fr.ourten.teabeans.property.ListProperty;

import java.util.List;
import java.util.function.Supplier;

public class NamedListProperty<T> extends ListProperty<T> implements INamedProperty
{
    private final String name;

    public NamedListProperty(Supplier<List<T>> listSupplier, List<T> value, String name)
    {
        super(listSupplier, value);

        this.name = name;
    }

    public NamedListProperty(List<T> value, String name)
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
