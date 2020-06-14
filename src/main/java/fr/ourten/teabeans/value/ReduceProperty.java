package fr.ourten.teabeans.value;

import fr.ourten.teabeans.binding.BidirectionalBinding;
import fr.ourten.teabeans.binding.Binding;
import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Stream;

public class ReduceProperty<T, V> extends Binding<V> implements IProperty<V>
{
    private final Function<Stream<T>, V> reducingFunction;

    private ValueInvalidationListener    propertyInvalidator;
    private ObservableValue<? extends V> observable;
    private boolean                      isObserving;

    /**
     * Dependencies previously used when this property acted as a Binding. Kept in case of reactivation.
     */
    private final ArrayList<WeakReference<Observable>> staleDependencies = new ArrayList<>();

    private boolean actAsBinding = true;

    public ReduceProperty(Function<Stream<T>, V> reducingFunction)
    {
        this.reducingFunction = reducingFunction;
    }

    public void actAsBinding()
    {
        if (actAsBinding)
            return;

        unbind();
        actAsBinding = true;
        staleDependencies.forEach(obsRef ->
        {
            Observable obs = obsRef.get();
            if (obs != null)
                bind(obs);
        });
        staleDependencies.clear();
        invalidate();
    }

    public void actAsProperty()
    {
        if (!actAsBinding)
            return;

        actAsBinding = false;
        getDependencies().forEach(obs -> staleDependencies.add(new WeakReference<>(obs)));
        unbindAll();

        setValid(true);
    }

    @Override
    public V computeValue()
    {
        return reducingFunction.apply(getDependencies().stream().map(obs -> ((ObservableValue<T>) obs).getValue()));
    }

    @Override
    public void bind(Observable... observables)
    {
        for (Observable observable : observables)
        {
            if (!(observable instanceof ObservableValue))
                throw new RuntimeException("ReduceProperty must bind to an ObservableValue");
        }

        super.bind(observables);
    }

    @Override
    public void invalidate(V oldValue)
    {
        if (isMuted())
            return;

        if (value == null || !value.equals(oldValue))
            fireChangeListeners(oldValue, value);
    }

    @Override
    public void bindProperty(ObservableValue<? extends V> observable)
    {
        Objects.requireNonNull(observable, "Cannot bind to null");
        if (!observable.equals(this.observable))
        {
            actAsProperty();
            unbind();
            this.observable = observable;
            if (propertyInvalidator == null)
                propertyInvalidator = obs -> setPropertyValue(ReduceProperty.this.observable.getValue());
            if (!valueChangeListeners.isEmpty() || !valueInvalidationListeners.isEmpty())
                startObserving();

            if (isMuted())
                return;
            if (value == null || !value.equals(observable.getValue()))
                fireChangeListeners(value, observable.getValue());
            fireInvalidationListeners();
        }
    }

    @Override
    public V getValue()
    {
        return observable == null ? super.getValue() : observable.getValue();
    }

    @Override
    public void unbind()
    {
        if (observable != null)
        {
            value = observable.getValue();
            stopObserving();
            observable = null;
        }
    }

    @Override
    public boolean isBound()
    {
        return observable != null;
    }

    @Override
    public void bindBidirectional(IProperty<V> other)
    {
        new BidirectionalBinding<>(this, other);
    }

    @Override
    public void unbindBidirectional(IProperty<V> other)
    {
        BidirectionalBinding<V> binding = new BidirectionalBinding<>(this, other);
        binding.unbind();
    }

    @Override
    public void setValue(V value)
    {
        if (isBound())
            throw new RuntimeException("Cannot set the value of a bound property");
        actAsProperty();
        setPropertyValue(value);
    }

    @Override
    public void addListener(ValueChangeListener<? super V> listener)
    {
        if (!isObserving && observable != null)
            startObserving();
        valueChangeListeners.add(listener);
    }

    @Override
    public void removeListener(ValueChangeListener<? super V> listener)
    {
        valueChangeListeners.remove(listener);
        if (valueChangeListeners.isEmpty() && observable != null)
            stopObserving();
    }

    @Override
    public void addListener(ValueInvalidationListener listener)
    {
        if (!isObserving && observable != null)
            startObserving();
        valueInvalidationListeners.add(listener);
    }

    @Override
    public void removeListener(ValueInvalidationListener listener)
    {
        valueInvalidationListeners.remove(listener);
        if (valueChangeListeners.isEmpty() && observable != null)
            stopObserving();
    }

    protected void setPropertyValue(V value)
    {
        V oldValue = this.value;
        this.value = value;
        invalidate(oldValue);
    }

    private void startObserving()
    {
        isObserving = true;
        observable.addListener(propertyInvalidator);
    }

    private void stopObserving()
    {
        isObserving = false;
        observable.removeListener(propertyInvalidator);
    }

    //////////////////
    // CONSTRUCTORS //
    //////////////////

    public static <T, V> ReduceProperty<T, V> reduce(Function<Stream<T>, V> reducingFunction)
    {
        return new ReduceProperty<>(reducingFunction);
    }

    public static <T> ReduceProperty<T, T> reduce(BinaryOperator<T> accumulator)
    {
        return new ReduceProperty<>(values -> values.reduce(accumulator).orElse(null));
    }

    public static <T, V> ReduceProperty<T, V> reduce(Function<Stream<T>, V> reducingFunction, ObservableValue<T>... observables)
    {
        ReduceProperty<T, V> reduceProperty = new ReduceProperty<>(reducingFunction);
        reduceProperty.bind(observables);
        return reduceProperty;
    }

    public static <T> ReduceProperty<T, T> reduce(BinaryOperator<T> accumulator, ObservableValue<T>... observables)
    {
        ReduceProperty<T, T> reduceProperty = new ReduceProperty<>(values -> values.reduce(accumulator).orElse(null));
        reduceProperty.bind(observables);
        return reduceProperty;
    }
}