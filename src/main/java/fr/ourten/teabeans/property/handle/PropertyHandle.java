package fr.ourten.teabeans.property.handle;

import fr.ourten.teabeans.property.PropertyBase;

import java.util.function.Supplier;

public class PropertyHandle<T>
{
    private final   PropertyBase<T> property;
    protected final Supplier<T>     getter;

    public PropertyHandle(PropertyBase<T> property, Supplier<T> getter)
    {
        this.property = property;
        this.getter = getter;
    }

    public PropertyBase<T> getProperty()
    {
        return property;
    }

    public void update()
    {
        property.setValue(getter.get());
    }
}
