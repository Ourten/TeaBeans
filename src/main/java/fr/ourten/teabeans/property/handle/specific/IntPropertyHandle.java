package fr.ourten.teabeans.property.handle.specific;

import fr.ourten.teabeans.property.handle.PropertyHandle;
import fr.ourten.teabeans.property.specific.IntProperty;

import java.util.function.IntSupplier;

public class IntPropertyHandle extends PropertyHandle<Number>
{
    public IntPropertyHandle(IntProperty property, IntSupplier getter)
    {
        super(property, getter::getAsInt);
    }

    public IntProperty getProperty()
    {
        return (IntProperty) super.getProperty();
    }

    public void update()
    {
        getProperty().setValue(((IntSupplier) getter).getAsInt());
    }
}
