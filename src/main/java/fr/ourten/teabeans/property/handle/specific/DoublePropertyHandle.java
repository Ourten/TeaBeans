package fr.ourten.teabeans.property.handle.specific;

import fr.ourten.teabeans.property.handle.PropertyHandle;
import fr.ourten.teabeans.property.specific.DoubleProperty;

import java.util.function.DoubleSupplier;

public class DoublePropertyHandle extends PropertyHandle<Number>
{
    public DoublePropertyHandle(DoubleProperty property, DoubleSupplier getter)
    {
        super(property, getter::getAsDouble);
    }

    public DoubleProperty getProperty()
    {
        return (DoubleProperty) super.getProperty();
    }

    public void update()
    {
        getProperty().setValue(((DoubleSupplier) getter).getAsDouble());
    }
}
