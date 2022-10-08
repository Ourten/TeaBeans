package fr.ourten.teabeans.property.specific;

import fr.ourten.teabeans.function.FloatConsumer;
import fr.ourten.teabeans.function.FloatFunction;
import fr.ourten.teabeans.function.FloatSupplier;
import fr.ourten.teabeans.function.ToFloatFunction;
import fr.ourten.teabeans.property.PropertyBase;
import fr.ourten.teabeans.property.handle.specific.FloatPropertyHandle;
import fr.ourten.teabeans.value.specific.FloatValue;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class FloatProperty extends PropertyBase<Number> implements FloatValue
{
    protected float value;
    protected float oldValue;

    public FloatProperty()
    {
        this(0);
    }

    public FloatProperty(float value)
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
    public Float getValue()
    {
        return get();
    }

    @Override
    public void setValue(Number value)
    {
        if (isBound())
            throw new UnsupportedOperationException("Cannot set the value of a bound property");
        set((float) value);
    }

    @Override
    protected void setPropertyValue(Number value)
    {
        this.value = (float) value;
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

    public void set(float value)
    {
        if (isBound())
            throw new UnsupportedOperationException("Cannot set the value of a bound property");
        this.value = value;
        invalidate();
    }

    @Override
    public float get()
    {
        return observable == null ? value : observable.getValue().floatValue();
    }

    /**
     * Wrap an existing source of value to this FloatProperty<br>
     * The FloatProperty will be set with the current value of the source.
     * <p></p>
     * Changes to the FloatProperty will be applied to the source of value.<br>
     * Changes to the source will not be applied to the FloatProperty.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #link(Supplier, Consumer)}
     *
     * @param getter of the value source
     * @param setter of the value source
     */
    public void wrap(FloatSupplier getter, FloatConsumer setter)
    {
        Objects.requireNonNull(getter);
        Objects.requireNonNull(setter);

        setValue(getter.getAsFloat());
        addChangeListener(obs -> setter.accept(getValue()));
    }

    /**
     * Wrap an existing source of value to this FloatProperty with a bijective transform<br>
     * The FloatProperty will be set with the current value of the source.
     * <p></p>
     * Changes to the FloatProperty will be applied to the source of value.<br>
     * Changes to the source will not be applied to the FloatProperty.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #linkMap(Supplier, Consumer, Function, Function)}
     *
     * @param getter             of the value source
     * @param setter             of the value source
     * @param transformer        mapping function from source to property
     * @param reverseTransformer inverse mapping function from property to source
     * @param <S>                type of the source
     */
    public <S> void wrapMap(Supplier<S> getter, Consumer<S> setter, ToFloatFunction<S> transformer, FloatFunction<S> reverseTransformer)
    {
        Objects.requireNonNull(getter);
        Objects.requireNonNull(setter);
        Objects.requireNonNull(transformer);
        Objects.requireNonNull(reverseTransformer);

        setValue(transformer.applyAsFloat(getter.get()));
        addChangeListener(obs -> setter.accept(reverseTransformer.apply(getValue())));
    }

    /**
     * Observe an existing source of value and propagate changes to this FloatProperty<br>
     * The FloatProperty will be set with the current value of the source.
     * <p></p>
     * Changes to the source will be applied to the FloatProperty.<br>
     * Changes to the FloatProperty will not be applied to the source of value.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #link(Supplier, Consumer)}
     *
     * @param getter of the value source
     * @return FloatPropertyHandle for this FloatProperty set with the current value of the source
     */
    public FloatPropertyHandle observe(FloatSupplier getter)
    {
        Objects.requireNonNull(getter);

        setValue(getter.getAsFloat());
        return new FloatPropertyHandle(this, getter);
    }

    /**
     * Observe an existing source of value and propagate changes to this FloatProperty with a transform<br>
     * The FloatProperty will be set with the current value of the source.
     * <p></p>
     * Changes to the source will be applied to the FloatProperty.<br>
     * Changes to the FloatProperty will not be applied to the source of value.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #linkMap(Supplier, Consumer, Function, Function)}
     *
     * @param getter      of the value source
     * @param transformer mapping function from source to property
     * @param <S>         type of the source
     * @return FloatPropertyHandle for this FloatProperty set with the current value of the source
     */
    public <S> FloatPropertyHandle observeMap(Supplier<S> getter, ToFloatFunction<S> transformer)
    {
        Objects.requireNonNull(getter);
        Objects.requireNonNull(transformer);

        setValue(transformer.applyAsFloat(getter.get()));
        return new FloatPropertyHandle(this, () -> transformer.applyAsFloat(getter.get()));
    }

    /**
     * Link an existing source of value to this FloatProperty<br>
     * The FloatProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the FloatProperty will be applied to the source of value.<br>
     * Changes to the source will be applied using the FloatPropertyHandle with {@link FloatPropertyHandle#update()}<br>
     *
     * @param getter of the value source
     * @param setter of the value source
     * @return FloatPropertyHandle for this FloatProperty set with the current value of the source
     */
    public FloatPropertyHandle link(FloatSupplier getter, FloatConsumer setter)
    {
        Objects.requireNonNull(getter);
        Objects.requireNonNull(setter);

        setValue(getter.getAsFloat());
        addChangeListener(obs -> setter.accept(getValue()));
        return new FloatPropertyHandle(this, getter);
    }

    /**
     * Link an existing source of value to this FloatProperty with a bijective transform<br>
     * The FloatProperty will be set with the current value of the source.
     * <p></p>
     * Changes to the FloatProperty will be applied to the source of value.<br>
     * Changes to the source will be applied using the FloatPropertyHandle with {@link FloatPropertyHandle#update()}<br>
     *
     * @param getter             of the value source
     * @param setter             of the value source
     * @param transformer        mapping function from source to property
     * @param reverseTransformer inverse mapping function from property to source
     * @param <S>                type of the source
     * @return FloatPropertyHandle for this FloatProperty set with the current value of the source
     */
    public <S> FloatPropertyHandle linkMap(Supplier<S> getter, Consumer<S> setter, ToFloatFunction<S> transformer, FloatFunction<S> reverseTransformer)
    {
        Objects.requireNonNull(getter);
        Objects.requireNonNull(setter);
        Objects.requireNonNull(transformer);
        Objects.requireNonNull(reverseTransformer);

        setValue(transformer.applyAsFloat(getter.get()));
        addChangeListener(obs -> setter.accept(reverseTransformer.apply(getValue())));
        return new FloatPropertyHandle(this, () -> transformer.applyAsFloat(getter.get()));
    }

    /**
     * Wrap an existing source of value to a FloatProperty<br>
     * The FloatProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the FloatProperty will be applied to the source of value.<br>
     * Changes to the source will not be applied to the FloatProperty.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #fromLink(FloatSupplier, FloatConsumer)}
     *
     * @param getter of the value source
     * @param setter of the value source
     * @return created FloatProperty with the current value of the source
     */
    public static FloatProperty fromWrap(FloatSupplier getter, FloatConsumer setter)
    {
        FloatProperty property = new FloatProperty();
        property.wrap(getter, setter);
        return property;
    }

    /**
     * Wrap an existing source of value to a FloatProperty with a bijective transform<br>
     * The FloatProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the FloatProperty will be applied to the source of value.<br>
     * Changes to the source will not be applied to the FloatProperty.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #fromLink(FloatSupplier, FloatConsumer)}
     *
     * @param getter             of the value source
     * @param setter             of the value source
     * @param transformer        mapping function from source to property
     * @param reverseTransformer inverse mapping function from property to source
     * @param <S>                type of the source
     * @return created FloatProperty with the current value of the source
     */
    public static <S> FloatProperty fromWrapMap(Supplier<S> getter, Consumer<S> setter, ToFloatFunction<S> transformer, FloatFunction<S> reverseTransformer)
    {
        FloatProperty property = new FloatProperty();
        property.wrapMap(getter, setter, transformer, reverseTransformer);
        return property;
    }

    /**
     * Observe an existing source of value and propagate changes to a FloatProperty<br>
     * The FloatProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the source will be applied to the Property.<br>
     * Changes to the FloatProperty will not be applied to the source of value.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #fromLink(FloatSupplier, FloatConsumer)}
     *
     * @param getter of the value source
     * @return FloatPropertyHandle for the newly created FloatProperty with the current value of the source
     */
    public static FloatPropertyHandle fromObserve(FloatSupplier getter)
    {
        FloatProperty property = new FloatProperty();
        return property.observe(getter);
    }

    /**
     * Observe an existing source of value and propagate changes to a FloatProperty with a transform<br>
     * The FloatProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the source will be applied to the FloatProperty.<br>
     * Changes to the FloatProperty will not be applied to the source of value.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #fromLinkMap(Supplier, Consumer, ToFloatFunction, FloatFunction)}
     *
     * @param getter      of the value source
     * @param transformer mapping function from source to property
     * @param <S>         type of the source
     * @return FloatPropertyHandle for the newly created FloatProperty with the current value of the source
     */
    public static <S> FloatPropertyHandle fromObserveMap(Supplier<S> getter, ToFloatFunction<S> transformer)
    {
        FloatProperty property = new FloatProperty();
        return property.observeMap(getter, transformer);
    }

    /**
     * Link an existing source of value to a FloatProperty<br>
     * The FloatProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the FloatProperty will be applied to the source of value.<br>
     * Changes to the source will be applied using the FloatPropertyHandle with {@link FloatPropertyHandle#update()}<br>
     *
     * @param getter of the value source
     * @param setter of the value source
     * @return created FloatProperty with the current value of the source
     */
    public static FloatPropertyHandle fromLink(FloatSupplier getter, FloatConsumer setter)
    {
        FloatProperty property = new FloatProperty();
        return property.link(getter, setter);
    }

    /**
     * Link an existing source of value to a FloatProperty with a bijective transform<br>
     * The FloatProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the FloatProperty will be applied to the source of value.<br>
     * Changes to the source will be applied using the FloatPropertyHandle with {@link FloatPropertyHandle#update()}<br>
     *
     * @param getter             of the value source
     * @param setter             of the value source
     * @param transformer        mapping function from source to property
     * @param reverseTransformer inverse mapping function from property to source
     * @param <S>                type of the source
     * @return created FloatProperty with the current value of the source
     */
    public static <S> FloatPropertyHandle fromLinkMap(Supplier<S> getter, Consumer<S> setter, ToFloatFunction<S> transformer, FloatFunction<S> reverseTransformer)
    {
        FloatProperty property = new FloatProperty();
        return property.linkMap(getter, setter, transformer, reverseTransformer);
    }
}
