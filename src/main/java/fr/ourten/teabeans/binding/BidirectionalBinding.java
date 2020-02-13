package fr.ourten.teabeans.binding;

import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.value.IProperty;
import fr.ourten.teabeans.value.ObservableValue;

import java.util.Objects;

public class BidirectionalBinding<T> implements ValueChangeListener<T>
{
    private boolean            updating;
    private final IProperty<T> property1;
    private final IProperty<T> property2;

    public BidirectionalBinding(final IProperty<T> p1, final IProperty<T> p2)
    {
        Objects.requireNonNull(p1, "Cannot bind to null!");
        Objects.requireNonNull(p2, "Cannot bind to null!");
        this.property1 = p1;
        this.property2 = p2;

        this.getProperty1().setValue(p2.getValue());
        this.getProperty1().addListener(this);
        this.getProperty2().addListener(this);
    }

    public void unbind()
    {
        this.getProperty1().removeListener(this);
        this.getProperty2().removeListener(this);
    }

    @Override
    public void valueChanged(final ObservableValue<? extends T> observable, final T oldValue, final T newValue)
    {
        if (!this.updating)
            if (this.getProperty1() == null || this.getProperty2() == null)
            {
                if (this.getProperty1() != null)
                    this.getProperty1().removeListener(this);
                if (this.getProperty2() != null)
                    this.getProperty2().removeListener(this);
            }
            else
            {
                this.updating = true;
                if (this.getProperty1() == observable)
                    this.getProperty2().setValue(newValue);
                else
                    this.getProperty1().setValue(newValue);
                this.updating = false;
            }
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj)
            return true;

        final Object propertyA1 = this.getProperty1();
        final Object propertyA2 = this.getProperty2();
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

    public IProperty<T> getProperty1()
    {
        return this.property1;
    }

    public IProperty<T> getProperty2()
    {
        return this.property2;
    }
}
