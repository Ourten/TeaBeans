package fr.ourten.teabeans.property.named;

import fr.ourten.teabeans.property.MapProperty;

import java.util.Map;
import java.util.function.Supplier;

public class NamedMapProperty<K, V> extends MapProperty<K, V> implements INamedProperty
{
    private final String name;

    public NamedMapProperty(Supplier<Map<K, V>> mapSupplier, Map<K, V> value, String name)
    {
        super(mapSupplier, value);

        this.name = name;
    }

    public NamedMapProperty(Map<K, V> value, String name)
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
