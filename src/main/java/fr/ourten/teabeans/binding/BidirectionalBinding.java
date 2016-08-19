package fr.ourten.teabeans.binding;

import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.value.ObservableValue;
import fr.ourten.teabeans.value.Property;

public class BidirectionalBinding<T> implements ValueChangeListener<T>
{
    private boolean           updating;
    private final Property<T> property1;
    private final Property<T> property2;

    public BidirectionalBinding(final Property<T> p1, final Property<T> p2)
    {
        this.property1 = p1;
        this.property2 = p2;

        this.property1.setValue(p2.getValue());
        this.property1.addListener(this);
        this.property2.addListener(this);
    }

    public void unbind()
    {
        this.property1.removeListener(this);
        this.property2.removeListener(this);
    }

    @Override
    public void valueChanged(final ObservableValue<? extends T> observable, final T oldValue, final T newValue)
    {
        if (!this.updating)
            if (this.property1 == null || this.property2 == null)
            {
                if (this.property1 != null)
                    this.property1.removeListener(this);
                if (this.property2 != null)
                    this.property2.removeListener(this);
            }
            else
            {
                this.updating = true;
                if (this.property1 == observable)
                    this.property2.setValue(newValue);
                else
                    this.property1.setValue(newValue);
                this.updating = false;
            }
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj)
            return true;

        final Object propertyA1 = this.property1;
        final Object propertyA2 = this.property2;
        if (propertyA1 == null || propertyA2 == null)
            return false;
        if (obj instanceof BidirectionalBinding)
        {
            @SuppressWarnings("unchecked")
            final BidirectionalBinding<T> otherBinding = (BidirectionalBinding<T>) obj;
            final Object propertyB1 = otherBinding.getProperty1();
            final Object propertyB2 = otherBinding.getProperty2();
            if (propertyB1 == null || propertyB2 == null)
                return false;
            if (propertyA1 == propertyB1 && propertyA2 == propertyB2
                    || propertyA1 == propertyB2 && propertyA2 == propertyB1)
                return true;
        }
        return false;
    }

    public Property<T> getProperty1()
    {
        return this.property1;
    }

    public Property<T> getProperty2()
    {
        return this.property2;
    }
}