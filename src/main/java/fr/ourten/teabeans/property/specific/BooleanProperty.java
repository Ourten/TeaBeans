package fr.ourten.teabeans.property.specific;

import fr.ourten.teabeans.function.BooleanConsumer;
import fr.ourten.teabeans.function.BooleanFunction;
import fr.ourten.teabeans.function.ToBooleanFunction;
import fr.ourten.teabeans.property.PropertyBase;
import fr.ourten.teabeans.property.handle.specific.BooleanPropertyHandle;
import fr.ourten.teabeans.value.specific.BooleanValue;

import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class BooleanProperty extends PropertyBase<Boolean> implements BooleanValue
{
    protected boolean value;
    protected boolean oldValue;

    public BooleanProperty()
    {
        this(false);
    }

    public BooleanProperty(boolean value)
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
    public Boolean getValue()
    {
        return get();
    }

    @Override
    public void setValue(Boolean value)
    {
        if (isBound())
            throw new UnsupportedOperationException("Cannot set the value of a bound property");
        set(value);
    }

    @Override
    protected void setPropertyValue(Boolean value)
    {
        this.value = value;
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

    public void set(boolean value)
    {
        if (isBound())
            throw new UnsupportedOperationException("Cannot set the value of a bound property");
        this.value = value;
        invalidate();
    }

    @Override
    public boolean get()
    {
        return observable == null ? value : observable.getValue();
    }

    /**
     * Wrap an existing source of value to this BooleanProperty<br>
     * The BooleanProperty will be set with the current value of the source.
     * <p></p>
     * Changes to the BooleanProperty will be applied to the source of value.<br>
     * Changes to the source will not be applied to the BooleanProperty.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #link(Supplier, Consumer)}
     *
     * @param getter of the value source
     * @param setter of the value source
     */
    public void wrap(BooleanSupplier getter, BooleanConsumer setter)
    {
        Objects.requireNonNull(getter);
        Objects.requireNonNull(setter);

        setValue(getter.getAsBoolean());
        addChangeListener(obs -> setter.accept(getValue()));
    }

    /**
     * Wrap an existing source of value to this BooleanProperty with a bijective transform<br>
     * The BooleanProperty will be set with the current value of the source.
     * <p></p>
     * Changes to the BooleanProperty will be applied to the source of value.<br>
     * Changes to the source will not be applied to the BooleanProperty.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #linkMap(Supplier, Consumer, Function, Function)}
     *
     * @param getter             of the value source
     * @param setter             of the value source
     * @param transformer        mapping function from source to property
     * @param reverseTransformer inverse mapping function from property to source
     * @param <S>                type of the source
     */
    public <S> void wrapMap(Supplier<S> getter, Consumer<S> setter, ToBooleanFunction<S> transformer, BooleanFunction<S> reverseTransformer)
    {
        Objects.requireNonNull(getter);
        Objects.requireNonNull(setter);
        Objects.requireNonNull(transformer);
        Objects.requireNonNull(reverseTransformer);

        setValue(transformer.applyAsBoolean(getter.get()));
        addChangeListener(obs -> setter.accept(reverseTransformer.apply(getValue())));
    }

    /**
     * Observe an existing source of value and propagate changes to this BooleanProperty<br>
     * The BooleanProperty will be set with the current value of the source.
     * <p></p>
     * Changes to the source will be applied to the BooleanProperty.<br>
     * Changes to the BooleanProperty will not be applied to the source of value.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #link(Supplier, Consumer)}
     *
     * @param getter of the value source
     * @return BooleanPropertyHandle for this BooleanProperty set with the current value of the source
     */
    public BooleanPropertyHandle observe(BooleanSupplier getter)
    {
        Objects.requireNonNull(getter);

        setValue(getter.getAsBoolean());
        return new BooleanPropertyHandle(this, getter);
    }

    /**
     * Observe an existing source of value and propagate changes to this BooleanProperty with a transform<br>
     * The BooleanProperty will be set with the current value of the source.
     * <p></p>
     * Changes to the source will be applied to the BooleanProperty.<br>
     * Changes to the BooleanProperty will not be applied to the source of value.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #linkMap(Supplier, Consumer, Function, Function)}
     *
     * @param getter      of the value source
     * @param transformer mapping function from source to property
     * @param <S>         type of the source
     * @return BooleanPropertyHandle for this BooleanProperty set with the current value of the source
     */
    public <S> BooleanPropertyHandle observeMap(Supplier<S> getter, ToBooleanFunction<S> transformer)
    {
        Objects.requireNonNull(getter);
        Objects.requireNonNull(transformer);

        setValue(transformer.applyAsBoolean(getter.get()));
        return new BooleanPropertyHandle(this, () -> transformer.applyAsBoolean(getter.get()));
    }

    /**
     * Link an existing source of value to this BooleanProperty<br>
     * The BooleanProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the BooleanProperty will be applied to the source of value.<br>
     * Changes to the source will be applied using the BooleanPropertyHandle with {@link BooleanPropertyHandle#update()}<br>
     *
     * @param getter of the value source
     * @param setter of the value source
     * @return BooleanPropertyHandle for this BooleanProperty set with the current value of the source
     */
    public BooleanPropertyHandle link(BooleanSupplier getter, BooleanConsumer setter)
    {
        Objects.requireNonNull(getter);
        Objects.requireNonNull(setter);

        setValue(getter.getAsBoolean());
        addChangeListener(obs -> setter.accept(getValue()));
        return new BooleanPropertyHandle(this, getter);
    }

    /**
     * Link an existing source of value to this BooleanProperty with a bijective transform<br>
     * The BooleanProperty will be set with the current value of the source.
     * <p></p>
     * Changes to the BooleanProperty will be applied to the source of value.<br>
     * Changes to the source will be applied using the BooleanPropertyHandle with {@link BooleanPropertyHandle#update()}<br>
     *
     * @param getter             of the value source
     * @param setter             of the value source
     * @param transformer        mapping function from source to property
     * @param reverseTransformer inverse mapping function from property to source
     * @param <S>                type of the source
     * @return BooleanPropertyHandle for this BooleanProperty set with the current value of the source
     */
    public <S> BooleanPropertyHandle linkMap(Supplier<S> getter, Consumer<S> setter, ToBooleanFunction<S> transformer, BooleanFunction<S> reverseTransformer)
    {
        Objects.requireNonNull(getter);
        Objects.requireNonNull(setter);
        Objects.requireNonNull(transformer);
        Objects.requireNonNull(reverseTransformer);

        setValue(transformer.applyAsBoolean(getter.get()));
        addChangeListener(obs -> setter.accept(reverseTransformer.apply(getValue())));
        return new BooleanPropertyHandle(this, () -> transformer.applyAsBoolean(getter.get()));
    }

    /**
     * Wrap an existing source of value to a BooleanProperty<br>
     * The BooleanProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the BooleanProperty will be applied to the source of value.<br>
     * Changes to the source will not be applied to the BooleanProperty.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #fromLink(BooleanSupplier, BooleanConsumer)}
     *
     * @param getter of the value source
     * @param setter of the value source
     * @return created BooleanProperty with the current value of the source
     */
    public static BooleanProperty fromWrap(BooleanSupplier getter, BooleanConsumer setter)
    {
        BooleanProperty property = new BooleanProperty();
        property.wrap(getter, setter);
        return property;
    }

    /**
     * Wrap an existing source of value to a BooleanProperty with a bijective transform<br>
     * The BooleanProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the BooleanProperty will be applied to the source of value.<br>
     * Changes to the source will not be applied to the BooleanProperty.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #fromLink(BooleanSupplier, BooleanConsumer)}
     *
     * @param getter             of the value source
     * @param setter             of the value source
     * @param transformer        mapping function from source to property
     * @param reverseTransformer inverse mapping function from property to source
     * @param <S>                type of the source
     * @return created BooleanProperty with the current value of the source
     */
    public static <S> BooleanProperty fromWrapMap(Supplier<S> getter, Consumer<S> setter, ToBooleanFunction<S> transformer, BooleanFunction<S> reverseTransformer)
    {
        BooleanProperty property = new BooleanProperty();
        property.wrapMap(getter, setter, transformer, reverseTransformer);
        return property;
    }

    /**
     * Observe an existing source of value and propagate changes to a BooleanProperty<br>
     * The BooleanProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the source will be applied to the Property.<br>
     * Changes to the BooleanProperty will not be applied to the source of value.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #fromLink(BooleanSupplier, BooleanConsumer)}
     *
     * @param getter of the value source
     * @return BooleanPropertyHandle for the newly created BooleanProperty with the current value of the source
     */
    public static BooleanPropertyHandle fromObserve(BooleanSupplier getter)
    {
        BooleanProperty property = new BooleanProperty();
        return property.observe(getter);
    }

    /**
     * Observe an existing source of value and propagate changes to a BooleanProperty with a transform<br>
     * The BooleanProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the source will be applied to the BooleanProperty.<br>
     * Changes to the BooleanProperty will not be applied to the source of value.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #fromLinkMap(Supplier, Consumer, ToBooleanFunction, BooleanFunction)}
     *
     * @param getter      of the value source
     * @param transformer mapping function from source to property
     * @param <S>         type of the source
     * @return BooleanPropertyHandle for the newly created BooleanProperty with the current value of the source
     */
    public static <S> BooleanPropertyHandle fromObserveMap(Supplier<S> getter, ToBooleanFunction<S> transformer)
    {
        BooleanProperty property = new BooleanProperty();
        return property.observeMap(getter, transformer);
    }

    /**
     * Link an existing source of value to a BooleanProperty<br>
     * The BooleanProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the BooleanProperty will be applied to the source of value.<br>
     * Changes to the source will be applied using the BooleanPropertyHandle with {@link BooleanPropertyHandle#update()}<br>
     *
     * @param getter of the value source
     * @param setter of the value source
     * @return created BooleanProperty with the current value of the source
     */
    public static BooleanPropertyHandle fromLink(BooleanSupplier getter, BooleanConsumer setter)
    {
        BooleanProperty property = new BooleanProperty();
        return property.link(getter, setter);
    }

    /**
     * Link an existing source of value to a BooleanProperty with a bijective transform<br>
     * The BooleanProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the BooleanProperty will be applied to the source of value.<br>
     * Changes to the source will be applied using the BooleanPropertyHandle with {@link BooleanPropertyHandle#update()}<br>
     *
     * @param getter             of the value source
     * @param setter             of the value source
     * @param transformer        mapping function from source to property
     * @param reverseTransformer inverse mapping function from property to source
     * @param <S>                type of the source
     * @return created BooleanProperty with the current value of the source
     */
    public static <S> BooleanPropertyHandle fromLinkMap(Supplier<S> getter, Consumer<S> setter, ToBooleanFunction<S> transformer, BooleanFunction<S> reverseTransformer)
    {
        BooleanProperty property = new BooleanProperty();
        return property.linkMap(getter, setter, transformer, reverseTransformer);
    }
}
