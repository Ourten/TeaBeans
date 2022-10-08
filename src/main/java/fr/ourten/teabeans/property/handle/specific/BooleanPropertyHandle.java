package fr.ourten.teabeans.property.handle.specific;

import fr.ourten.teabeans.property.handle.PropertyHandle;
import fr.ourten.teabeans.property.specific.BooleanProperty;

import java.util.function.BooleanSupplier;

public class BooleanPropertyHandle extends PropertyHandle<Boolean>
{
    public BooleanPropertyHandle(BooleanProperty property, BooleanSupplier getter)
    {
        super(property, getter::getAsBoolean);
    }

    public BooleanProperty getProperty()
    {
        return (BooleanProperty) super.getProperty();
    }

    public void update()
    {
        getProperty().setValue(((BooleanSupplier) getter).getAsBoolean());
    }
}
