package fr.ourten.teabeans.property.specific;

import fr.ourten.teabeans.property.PropertyBase;
import fr.ourten.teabeans.property.handle.specific.DoublePropertyHandle;
import fr.ourten.teabeans.value.specific.DoubleValue;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;

public class DoubleProperty extends PropertyBase<Number> implements DoubleValue
{
    protected double value;
    protected double oldValue;

    public DoubleProperty()
    {
        this(0);
    }

    public DoubleProperty(double value)
    {
        this.value = value;
        oldValue = value;
    }

    @Override
    protected void afterBindProperty()
    {
        if (!Objects.equals(observable.getValue(), value))
        {
            if (isPristine())
                setPristine(false);

            fireChangeArglessListeners();
            fireChangeListeners(value, observable.getValue());
        }
        fireInvalidationListeners();
    }

    @Override
    public Double getValue()
    {
        return get();
    }

    @Override
    public void setValue(Number value)
    {
        if (isBound())
            throw new UnsupportedOperationException("Cannot set the value of a bound property");
        set((double) value);
    }

    @Override
    protected void setPropertyValue(Number value)
    {
        this.value = (double) value;
        invalidate();

        if (isPristine())
            setPristine(false);
    }

    @Override
    public void invalidate()
    {
        if (isMuted())
            return;

        if (value != oldValue)
        {
            fireChangeArglessListeners();
            fireChangeListeners(oldValue, value);
        }
        fireInvalidationListeners();

        oldValue = value;
    }

    public void set(double value)
    {
        if (isBound())
            throw new UnsupportedOperationException("Cannot set the value of a bound property");
        this.value = value;
        invalidate();
    }

    @Override
    public double get()
    {
        return observable == null ? value : observable.getValue().doubleValue();
    }

    /**
     * Wrap an existing source of value to this DoubleProperty<br>
     * The DoubleProperty will be set with the current value of the source.
     * <p></p>
     * Changes to the DoubleProperty will be applied to the source of value.<br>
     * Changes to the source will not be applied to the DoubleProperty.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #link(Supplier, Consumer)}
     *
     * @param getter of the value source
     * @param setter of the value source
     */
    public void wrap(DoubleSupplier getter, DoubleConsumer setter)
    {
        Objects.requireNonNull(getter);
        Objects.requireNonNull(setter);

        setValue(getter.getAsDouble());
        addChangeListener(obs -> setter.accept(getValue()));
    }

    /**
     * Wrap an existing source of value to this DoubleProperty with a bijective transform<br>
     * The DoubleProperty will be set with the current value of the source.
     * <p></p>
     * Changes to the DoubleProperty will be applied to the source of value.<br>
     * Changes to the source will not be applied to the DoubleProperty.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #linkMap(Supplier, Consumer, Function, Function)}
     *
     * @param getter             of the value source
     * @param setter             of the value source
     * @param transformer        mapping function from source to property
     * @param reverseTransformer inverse mapping function from property to source
     * @param <S>                type of the source
     */
    public <S> void wrapMap(Supplier<S> getter, Consumer<S> setter, ToDoubleFunction<S> transformer, DoubleFunction<S> reverseTransformer)
    {
        Objects.requireNonNull(getter);
        Objects.requireNonNull(setter);
        Objects.requireNonNull(transformer);
        Objects.requireNonNull(reverseTransformer);

        setValue(transformer.applyAsDouble(getter.get()));
        addChangeListener(obs -> setter.accept(reverseTransformer.apply(getValue())));
    }

    /**
     * Observe an existing source of value and propagate changes to this DoubleProperty<br>
     * The DoubleProperty will be set with the current value of the source.
     * <p></p>
     * Changes to the source will be applied to the DoubleProperty.<br>
     * Changes to the DoubleProperty will not be applied to the source of value.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #link(Supplier, Consumer)}
     *
     * @param getter of the value source
     * @return DoublePropertyHandle for this DoubleProperty set with the current value of the source
     */
    public DoublePropertyHandle observe(DoubleSupplier getter)
    {
        Objects.requireNonNull(getter);

        setValue(getter.getAsDouble());
        return new DoublePropertyHandle(this, getter);
    }

    /**
     * Observe an existing source of value and propagate changes to this DoubleProperty with a transform<br>
     * The DoubleProperty will be set with the current value of the source.
     * <p></p>
     * Changes to the source will be applied to the DoubleProperty.<br>
     * Changes to the DoubleProperty will not be applied to the source of value.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #linkMap(Supplier, Consumer, Function, Function)}
     *
     * @param getter      of the value source
     * @param transformer mapping function from source to property
     * @param <S>         type of the source
     * @return DoublePropertyHandle for this DoubleProperty set with the current value of the source
     */
    public <S> DoublePropertyHandle observeMap(Supplier<S> getter, ToDoubleFunction<S> transformer)
    {
        Objects.requireNonNull(getter);
        Objects.requireNonNull(transformer);

        setValue(transformer.applyAsDouble(getter.get()));
        return new DoublePropertyHandle(this, () -> transformer.applyAsDouble(getter.get()));
    }

    /**
     * Link an existing source of value to this DoubleProperty<br>
     * The DoubleProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the DoubleProperty will be applied to the source of value.<br>
     * Changes to the source will be applied using the DoublePropertyHandle with {@link DoublePropertyHandle#update()}<br>
     *
     * @param getter of the value source
     * @param setter of the value source
     * @return DoublePropertyHandle for this DoubleProperty set with the current value of the source
     */
    public DoublePropertyHandle link(DoubleSupplier getter, DoubleConsumer setter)
    {
        Objects.requireNonNull(getter);
        Objects.requireNonNull(setter);

        setValue(getter.getAsDouble());
        addChangeListener(obs -> setter.accept(getValue()));
        return new DoublePropertyHandle(this, getter);
    }

    /**
     * Link an existing source of value to this DoubleProperty with a bijective transform<br>
     * The DoubleProperty will be set with the current value of the source.
     * <p></p>
     * Changes to the DoubleProperty will be applied to the source of value.<br>
     * Changes to the source will be applied using the DoublePropertyHandle with {@link DoublePropertyHandle#update()}<br>
     *
     * @param getter             of the value source
     * @param setter             of the value source
     * @param transformer        mapping function from source to property
     * @param reverseTransformer inverse mapping function from property to source
     * @param <S>                type of the source
     * @return DoublePropertyHandle for this DoubleProperty set with the current value of the source
     */
    public <S> DoublePropertyHandle linkMap(Supplier<S> getter, Consumer<S> setter, ToDoubleFunction<S> transformer, DoubleFunction<S> reverseTransformer)
    {
        Objects.requireNonNull(getter);
        Objects.requireNonNull(setter);
        Objects.requireNonNull(transformer);
        Objects.requireNonNull(reverseTransformer);

        setValue(transformer.applyAsDouble(getter.get()));
        addChangeListener(obs -> setter.accept(reverseTransformer.apply(getValue())));
        return new DoublePropertyHandle(this, () -> transformer.applyAsDouble(getter.get()));
    }

    /**
     * Wrap an existing source of value to a DoubleProperty<br>
     * The DoubleProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the DoubleProperty will be applied to the source of value.<br>
     * Changes to the source will not be applied to the DoubleProperty.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #fromLink(DoubleSupplier, DoubleConsumer)}
     *
     * @param getter of the value source
     * @param setter of the value source
     * @return created DoubleProperty with the current value of the source
     */
    public static DoubleProperty fromWrap(DoubleSupplier getter, DoubleConsumer setter)
    {
        DoubleProperty property = new DoubleProperty();
        property.wrap(getter, setter);
        return property;
    }

    /**
     * Wrap an existing source of value to a DoubleProperty with a bijective transform<br>
     * The DoubleProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the DoubleProperty will be applied to the source of value.<br>
     * Changes to the source will not be applied to the DoubleProperty.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #fromLink(DoubleSupplier, DoubleConsumer)}
     *
     * @param getter             of the value source
     * @param setter             of the value source
     * @param transformer        mapping function from source to property
     * @param reverseTransformer inverse mapping function from property to source
     * @param <S>                type of the source
     * @return created DoubleProperty with the current value of the source
     */
    public static <S> DoubleProperty fromWrapMap(Supplier<S> getter, Consumer<S> setter, ToDoubleFunction<S> transformer, DoubleFunction<S> reverseTransformer)
    {
        DoubleProperty property = new DoubleProperty();
        property.wrapMap(getter, setter, transformer, reverseTransformer);
        return property;
    }

    /**
     * Observe an existing source of value and propagate changes to a DoubleProperty<br>
     * The DoubleProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the source will be applied to the Property.<br>
     * Changes to the DoubleProperty will not be applied to the source of value.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #fromLink(DoubleSupplier, DoubleConsumer)}
     *
     * @param getter of the value source
     * @return DoublePropertyHandle for the newly created DoubleProperty with the current value of the source
     */
    public static DoublePropertyHandle fromObserve(DoubleSupplier getter)
    {
        DoubleProperty property = new DoubleProperty();
        return property.observe(getter);
    }

    /**
     * Observe an existing source of value and propagate changes to a DoubleProperty with a transform<br>
     * The DoubleProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the source will be applied to the DoubleProperty.<br>
     * Changes to the DoubleProperty will not be applied to the source of value.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #fromLinkMap(Supplier, Consumer, ToDoubleFunction, DoubleFunction)}
     *
     * @param getter      of the value source
     * @param transformer mapping function from source to property
     * @param <S>         type of the source
     * @return DoublePropertyHandle for the newly created DoubleProperty with the current value of the source
     */
    public static <S> DoublePropertyHandle fromObserveMap(Supplier<S> getter, ToDoubleFunction<S> transformer)
    {
        DoubleProperty property = new DoubleProperty();
        return property.observeMap(getter, transformer);
    }

    /**
     * Link an existing source of value to a DoubleProperty<br>
     * The DoubleProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the DoubleProperty will be applied to the source of value.<br>
     * Changes to the source will be applied using the DoublePropertyHandle with {@link DoublePropertyHandle#update()}<br>
     *
     * @param getter of the value source
     * @param setter of the value source
     * @return created DoubleProperty with the current value of the source
     */
    public static DoublePropertyHandle fromLink(DoubleSupplier getter, DoubleConsumer setter)
    {
        DoubleProperty property = new DoubleProperty();
        return property.link(getter, setter);
    }

    /**
     * Link an existing source of value to a DoubleProperty with a bijective transform<br>
     * The DoubleProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the DoubleProperty will be applied to the source of value.<br>
     * Changes to the source will be applied using the DoublePropertyHandle with {@link DoublePropertyHandle#update()}<br>
     *
     * @param getter             of the value source
     * @param setter             of the value source
     * @param transformer        mapping function from source to property
     * @param reverseTransformer inverse mapping function from property to source
     * @param <S>                type of the source
     * @return created DoubleProperty with the current value of the source
     */
    public static <S> DoublePropertyHandle fromLinkMap(Supplier<S> getter, Consumer<S> setter, ToDoubleFunction<S> transformer, DoubleFunction<S> reverseTransformer)
    {
        DoubleProperty property = new DoubleProperty();
        return property.linkMap(getter, setter, transformer, reverseTransformer);
    }
}
