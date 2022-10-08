package fr.ourten.teabeans.property.handle.specific;

import fr.ourten.teabeans.function.FloatSupplier;
import fr.ourten.teabeans.property.handle.PropertyHandle;
import fr.ourten.teabeans.property.specific.FloatProperty;

public class FloatPropertyHandle extends PropertyHandle<Number>
{
    public FloatPropertyHandle(FloatProperty property, FloatSupplier getter)
    {
        super(property, getter::getAsFloat);
    }

    public FloatProperty getProperty()
    {
        return (FloatProperty) super.getProperty();
    }

    public void update()
    {
        getProperty().setValue(((FloatSupplier) getter).getAsFloat());
    }
}
