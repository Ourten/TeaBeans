package fr.ourten.teabeans.value;

import java.util.ArrayList;

import com.google.common.collect.Lists;

import fr.ourten.teabeans.binding.BidirectionalBinding;
import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;

public class BaseProperty<T> implements Property<T>
{
    /**
     * The list of attached listeners that need to be notified when the value
     * change.
     */
    protected final ArrayList<ValueChangeListener<? super T>> valueChangeListeners;
    protected final ArrayList<ValueInvalidationListener>      valueInvalidationListeners;

    /**
     * The listener used to bind this property to another.
     */
    private ValueInvalidationListener                         listener;
    private ObservableValue<? extends T>                      observable;
    private final String                                      NAME;
    protected T                                               value;

    public BaseProperty(final T value, final String name)
    {
        this.valueChangeListeners = Lists.newArrayList();
        this.valueInvalidationListeners = Lists.newArrayList();

        this.value = value;
        this.NAME = name;
    }

    public BaseProperty(final T value)
    {
        this(value, "");
    }

    @Override
    public void addListener(final ValueChangeListener<? super T> listener)
    {
        if (!this.valueChangeListeners.contains(listener))
            this.valueChangeListeners.add(listener);
    }

    @Override
    public void removeListener(final ValueChangeListener<? super T> listener)
    {
        this.valueChangeListeners.remove(listener);
    }

    @Override
    public void addListener(final ValueInvalidationListener listener)
    {
        if (!this.valueInvalidationListeners.contains(listener))
            this.valueInvalidationListeners.add(listener);
    }

    @Override
    public void removeListener(final ValueInvalidationListener listener)
    {
        this.valueInvalidationListeners.remove(listener);
    }

    @Override
    public T getValue()
    {
        return this.observable == null ? this.value : this.observable.getValue();
    }

    @Override
    public void setValue(final T value)
    {
        if (this.isBound())
            throw new RuntimeException("Cannot set the value of a bound property");
        this.setPropertyValue(value);
    }

    private void setPropertyValue(final T value)
    {
        final T oldValue = this.value;
        this.value = value;
        this.invalidate(oldValue);
    }

    @Override
    public String getName()
    {
        return this.NAME;
    }

    @Override
    public void invalidate(final T oldValue)
    {
        if (this.value == null || !this.value.equals(oldValue))
            this.fireChangeListeners(oldValue, this.value);
        this.fireInvalidationListeners();
    }

    @Override
    public void bind(final ObservableValue<? extends T> observable)
    {
        if (observable == null)
            throw new NullPointerException("Cannot bind to null");
        if (!observable.equals(this.observable))
        {
            this.unbind();
            this.observable = observable;
            if (this.listener == null)
                this.listener = obs -> BaseProperty.this.setPropertyValue(BaseProperty.this.observable.getValue());
            observable.addListener(this.listener);
            if (this.value == null || !this.value.equals(observable.getValue()))
                this.fireChangeListeners(this.value, observable.getValue());
            this.fireInvalidationListeners();
        }
    }

    @Override
    public void unbind()
    {
        if (this.observable != null)
        {
            this.value = this.observable.getValue();
            this.observable.removeListener(this.listener);
            this.observable = null;
        }
    }

    @Override
    public boolean isBound()
    {
        return this.observable != null;
    }

    @Override
    public void bindBidirectional(final Property<T> other)
    {
        new BidirectionalBinding<>(this, other);
    }

    @Override
    public void unbindBidirectional(final Property<T> other)
    {
        final BidirectionalBinding<T> binding = new BidirectionalBinding<>(this, other);
        binding.unbind();
    }

    protected void fireChangeListeners(final T oldValue, final T newValue)
    {
        for (final ValueChangeListener<? super T> listener : this.valueChangeListeners)
            listener.valueChanged(this, oldValue, newValue);
    }

    protected void fireInvalidationListeners()
    {
        for (final ValueInvalidationListener listener : this.valueInvalidationListeners)
            listener.invalidated(this);
    }
}