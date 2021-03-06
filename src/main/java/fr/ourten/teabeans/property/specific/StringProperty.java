package fr.ourten.teabeans.property.specific;

import fr.ourten.teabeans.property.Property;
import fr.ourten.teabeans.value.specific.StringValue;

public class StringProperty extends Property<String> implements StringValue
{
    public StringProperty()
    {
        super("");
    }

    public StringProperty(String value)
    {
        super(value);
    }
}
