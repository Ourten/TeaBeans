package fr.ourten.teabeans.property.handle.specific;

import fr.ourten.teabeans.property.handle.PropertyHandle;
import fr.ourten.teabeans.property.specific.LongProperty;

import java.util.function.LongSupplier;

public class LongPropertyHandle extends PropertyHandle<Number>
{
    public LongPropertyHandle(LongProperty property, LongSupplier getter)
    {
        super(property, getter::getAsLong);
    }

    public LongProperty getProperty()
    {
        return (LongProperty) super.getProperty();
    }

    public void update()
    {
        getProperty().setValue(((LongSupplier) getter).getAsLong());
    }
}
