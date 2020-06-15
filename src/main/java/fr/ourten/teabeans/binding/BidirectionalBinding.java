package fr.ourten.teabeans.binding;

import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.property.IProperty;
import fr.ourten.teabeans.value.ObservableValue;

import java.util.Objects;

public class BidirectionalBinding<T> implements ValueChangeListener<T>
{
    private       boolean      updating;
    private final IProperty<T> property1;
    private final IProperty<T> property2;

    public BidirectionalBinding(IProperty<T> p1, IProperty<T> p2)
    {
        Objects.requireNonNull(p1, "Cannot bind to null!");
        Objects.requireNonNull(p2, "Cannot bind to null!");
        property1 = p1;
        property2 = p2;

        getProperty1().setValue(p2.getValue());
        getProperty1().addListener(this);
        getProperty2().addListener(this);
    }

    public void unbind()
    {
        getProperty1().removeListener(this);
        getProperty2().removeListener(this);
    }

    @Override
    public void valueChanged(ObservableValue<? extends T> observable, T oldValue, T newValue)
    {
        if (!updating)
            if (getProperty1() == null || getProperty2() == null)
            {
                if (getProperty1() != null)
                    getProperty1().removeListener(this);
                if (getProperty2() != null)
                    getProperty2().removeListener(this);
            }
            else
            {
                updating = true;
                if (getProperty1() == observable)
                    getProperty2().setValue(newValue);
                else
                    getProperty1().setValue(newValue);
                updating = false;
            }
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;

        Object propertyA1 = getProperty1();
        Object propertyA2 = getProperty2();
        if (propertyA1 == null || propertyA2 == null)
            return false;
        if (obj instanceof BidirectionalBinding)
        {
            BidirectionalBinding<T> otherBinding = (BidirectionalBinding<T>) obj;
            Object propertyB1 = otherBinding.getProperty1();
            Object propertyB2 = otherBinding.getProperty2();
            if (propertyB1 == null || propertyB2 == null)
                return false;
            if (propertyA1 == propertyB1 && propertyA2 == propertyB2
                    || propertyA1 == propertyB2 && propertyA2 == propertyB1)
                return true;
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(updating, property1, property2);
    }

    public IProperty<T> getProperty1()
    {
        return property1;
    }

    public IProperty<T> getProperty2()
    {
        return property2;
    }
}
