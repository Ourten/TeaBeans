package fr.ourten.teabeans.property;

import fr.ourten.teabeans.binding.BidirectionalBinding;
import fr.ourten.teabeans.binding.WeakObservableListener;
import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.property.handle.PropertyHandle;
import fr.ourten.teabeans.value.ObservableValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class PropertyBase<T> implements IProperty<T>
{
    /**
     * The list of attached listeners that need to be notified when the value
     * change.
     */
    protected final List<ValueChangeListener<? super T>> valueChangeListeners        = new ArrayList<>();
    protected final List<ValueInvalidationListener>      valueInvalidationListeners  = new ArrayList<>();
    protected final ArrayList<ValueInvalidationListener> valueChangeArglessListeners = new ArrayList<>();

    private final List<ValueChangeListener<? super T>> valueChangeListenersToRemove        = new ArrayList<>(1);
    private final List<ValueInvalidationListener>      valueInvalidationListenersToRemove  = new ArrayList<>(1);
    private final List<ValueInvalidationListener>      valueChangeArglessListenersToRemove = new ArrayList<>(1);

    private boolean isPropagatingEvents;

    /**
     * The listener used to bind this property to another.
     */
    private   ValueInvalidationListener    propertyInvalidator;
    protected ObservableValue<? extends T> observable;

    private boolean isObserving = false;
    private boolean isMuted;

    private boolean pristine = true;

    public PropertyBase()
    {

    }

    @Override
    public void addChangeListener(ValueChangeListener<? super T> listener)
    {
        if (!isObserving && observable != null)
            startObserving();
        if (!valueChangeListeners.contains(listener))
            valueChangeListeners.add(listener);
    }

    @Override
    public void removeChangeListener(ValueChangeListener<? super T> listener)
    {
        if (isPropagatingEvents)
        {
            valueChangeListenersToRemove.add(listener);
            return;
        }

        valueChangeListeners.remove(listener);
        if (valueInvalidationListeners.isEmpty() &&
                valueChangeListeners.isEmpty() &&
                valueChangeArglessListeners.isEmpty() &&
                observable != null)
            stopObserving();
    }

    @Override
    public void addListener(ValueInvalidationListener listener)
    {
        if (!isObserving && observable != null)
            startObserving();
        if (!valueInvalidationListeners.contains(listener))
            valueInvalidationListeners.add(listener);
    }

    @Override
    public void removeListener(ValueInvalidationListener listener)
    {
        if (isPropagatingEvents)
        {
            valueInvalidationListenersToRemove.add(listener);
            return;
        }

        valueInvalidationListeners.remove(listener);
        if (valueInvalidationListeners.isEmpty() &&
                valueChangeListeners.isEmpty() &&
                valueChangeArglessListeners.isEmpty() &&
                observable != null)
            stopObserving();
    }

    @Override
    public void addChangeListener(ValueInvalidationListener listener)
    {
        if (!isObserving && observable != null)
            startObserving();
        if (!valueChangeArglessListeners.contains(listener))
            valueChangeArglessListeners.add(listener);
    }

    @Override
    public void removeChangeListener(ValueInvalidationListener listener)
    {
        if (isPropagatingEvents)
        {
            valueChangeArglessListenersToRemove.add(listener);
            return;
        }

        valueChangeArglessListeners.remove(listener);
        if (valueChangeArglessListeners.isEmpty() &&
                valueChangeListeners.isEmpty() &&
                valueChangeArglessListeners.isEmpty() &&
                observable != null)
            stopObserving();
    }

    @Override
    public void mute()
    {
        isMuted = true;
    }

    @Override
    public void unmute()
    {
        isMuted = false;
        invalidate();
    }

    @Override
    public boolean isMuted()
    {
        return isMuted;
    }

    @Override
    public void bindProperty(ObservableValue<? extends T> observable)
    {
        Objects.requireNonNull(observable, "Cannot bind to null");
        if (!observable.equals(this.observable))
        {
            unbind();
            this.observable = observable;
            if (propertyInvalidator == null)
                propertyInvalidator = new WeakObservableListener(this);
            if (hasListeners())
                startObserving();

            if (isMuted())
                return;

            afterBindProperty();
        }
    }

    @Override
    public void unbind()
    {
        if (observable != null)
        {
            setPropertyValue(observable.getValue());

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
    public boolean isBoundTo(ObservableValue<? extends T> observable)
    {
        return this.observable == observable;
    }

    @Override
    public void setPristine(boolean isPristine)
    {
        pristine = isPristine;
    }

    @Override
    public boolean isPristine()
    {
        return pristine;
    }

    @Override
    public void bindBidirectional(IProperty<T> other)
    {
        new BidirectionalBinding<>(this, other);
    }

    @Override
    public void unbindBidirectional(IProperty<T> other)
    {
        BidirectionalBinding<T> binding = new BidirectionalBinding<>(this, other);
        binding.unbind();
    }

    protected void fireChangeListeners(T oldValue, T newValue)
    {
        isPropagatingEvents = true;
        for (ValueChangeListener<? super T> listener : valueChangeListeners)
            listener.valueChanged(this, oldValue, newValue);

        isPropagatingEvents = false;
        for (ValueChangeListener<? super T> listener : valueChangeListenersToRemove)
            valueChangeListeners.remove(listener);
        valueChangeListenersToRemove.clear();
    }

    protected void fireInvalidationListeners()
    {
        isPropagatingEvents = true;
        for (ValueInvalidationListener listener : valueInvalidationListeners)
            listener.invalidated(this);

        isPropagatingEvents = false;
        for (ValueInvalidationListener listener : valueInvalidationListenersToRemove)
            valueInvalidationListeners.remove(listener);
        valueInvalidationListenersToRemove.clear();
    }

    protected void fireChangeArglessListeners()
    {
        isPropagatingEvents = true;
        for (ValueInvalidationListener listener : valueChangeArglessListeners)
            listener.invalidated(this);

        isPropagatingEvents = false;
        for (ValueInvalidationListener listener : valueChangeArglessListenersToRemove)
            valueChangeArglessListeners.remove(listener);
        valueChangeArglessListenersToRemove.clear();
    }

    protected void startObserving()
    {
        isObserving = true;
        observable.addListener(propertyInvalidator);
    }

    protected void stopObserving()
    {
        isObserving = false;
        observable.removeListener(propertyInvalidator);
    }

    protected boolean isObserving()
    {
        return isObserving;
    }

    protected boolean hasObservable()
    {
        return observable != null;
    }

    protected boolean hasListeners()
    {
        return !valueInvalidationListeners.isEmpty() || !valueChangeListeners.isEmpty();
    }

    protected abstract void setPropertyValue(T value);

    protected abstract void afterBindProperty();

    /**
     * Wrap an existing source of value to this Property<br>
     * The Property will be set with the current value of the source.
     * <p></p>
     * Changes to the Property will be applied to the source of value.<br>
     * Changes to the source will not be applied to the Property.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #link(Supplier, Consumer)}
     *
     * @param currentValue of the source
     * @param setter       of the value source
     */
    public void wrap(T currentValue, Consumer<T> setter)
    {
        Objects.requireNonNull(setter);

        setValue(currentValue);
        addChangeListener(obs -> setter.accept(getValue()));
    }

    /**
     * Wrap an existing source of value to this Property with a bijective transform<br>
     * The Property will be set with the current value of the source.
     * <p></p>
     * Changes to the Property will be applied to the source of value.<br>
     * Changes to the source will not be applied to the Property.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #linkMap(Supplier, Consumer, Function, Function)}
     *
     * @param currentValue       of the value source
     * @param setter             of the value source
     * @param transformer        mapping function from source to property
     * @param reverseTransformer inverse mapping function from property to source
     * @param <S>                type of the source
     */
    public <S> void wrapMap(S currentValue, Consumer<S> setter, Function<S, T> transformer, Function<T, S> reverseTransformer)
    {
        Objects.requireNonNull(setter);
        Objects.requireNonNull(transformer);
        Objects.requireNonNull(reverseTransformer);

        setValue(transformer.apply(currentValue));
        addChangeListener(obs -> setter.accept(reverseTransformer.apply(getValue())));
    }

    /**
     * Observe an existing source of value and propagate changes to this Property<br>
     * The Property will be set with the current value of the source.
     * <p></p>
     * Changes to the source will be applied to the Property.<br>
     * Changes to the Property will not be applied to the source of value.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #link(Supplier, Consumer)}
     *
     * @param getter of the value source
     * @return PropertyHandle for this Property set with the current value of the source
     */
    public PropertyHandle<T> observe(Supplier<T> getter)
    {
        Objects.requireNonNull(getter);

        setValue(getter.get());
        return new PropertyHandle<>(this, getter);
    }

    /**
     * Observe an existing source of value and propagate changes to this Property with a transform<br>
     * The Property will be set with the current value of the source.
     * <p></p>
     * Changes to the source will be applied to the Property.<br>
     * Changes to the Property will not be applied to the source of value.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #linkMap(Supplier, Consumer, Function, Function)}
     *
     * @param getter      of the value source
     * @param transformer mapping function from source to property
     * @param <S>         type of the source
     * @return PropertyHandle for this Property set with the current value of the source
     */
    public <S> PropertyHandle<T> observeMap(Supplier<S> getter, Function<S, T> transformer)
    {
        Objects.requireNonNull(getter);
        Objects.requireNonNull(transformer);

        setValue(transformer.apply(getter.get()));
        return new PropertyHandle<>(this, () -> transformer.apply(getter.get()));
    }

    /**
     * Link an existing source of value to this Property<br>
     * The Property will be initialized with the current value of the source.
     * <p></p>
     * Changes to the Property will be applied to the source of value.<br>
     * Changes to the source will be applied using the PropertyHandle with {@link PropertyHandle#update()}<br>
     *
     * @param getter of the value source
     * @param setter of the value source
     * @return PropertyHandle for this Property set with the current value of the source
     */
    public PropertyHandle<T> link(Supplier<T> getter, Consumer<T> setter)
    {
        Objects.requireNonNull(getter);
        Objects.requireNonNull(setter);

        setValue(getter.get());
        addChangeListener(obs -> setter.accept(getValue()));
        return new PropertyHandle<>(this, getter);
    }

    /**
     * Link an existing source of value to this Property with a bijective transform<br>
     * The Property will be set with the current value of the source.
     * <p></p>
     * Changes to the Property will be applied to the source of value.<br>
     * Changes to the source will be applied using the PropertyHandle with {@link PropertyHandle#update()}<br>
     *
     * @param getter             of the value source
     * @param setter             of the value source
     * @param transformer        mapping function from source to property
     * @param reverseTransformer inverse mapping function from property to source
     * @param <S>                type of the source
     * @return PropertyHandle for this Property set with the current value of the source
     */
    public <S> PropertyHandle<T> linkMap(Supplier<S> getter, Consumer<S> setter, Function<S, T> transformer, Function<T, S> reverseTransformer)
    {
        Objects.requireNonNull(getter);
        Objects.requireNonNull(setter);
        Objects.requireNonNull(transformer);
        Objects.requireNonNull(reverseTransformer);

        setValue(transformer.apply(getter.get()));
        addChangeListener(obs -> setter.accept(reverseTransformer.apply(getValue())));
        return new PropertyHandle<>(this, () -> transformer.apply(getter.get()));
    }
}
