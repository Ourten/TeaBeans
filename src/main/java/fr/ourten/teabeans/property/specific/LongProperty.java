package fr.ourten.teabeans.property.specific;

import fr.ourten.teabeans.property.PropertyBase;
import fr.ourten.teabeans.property.handle.specific.LongPropertyHandle;
import fr.ourten.teabeans.value.specific.LongValue;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.function.ToLongFunction;

public class LongProperty extends PropertyBase<Number> implements LongValue
{
    protected long value;
    protected long oldValue;

    public LongProperty()
    {
        this(0);
    }

    public LongProperty(long value)
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
    public Long getValue()
    {
        return get();
    }

    @Override
    public void setValue(Number value)
    {
        if (value != null)
            set(value.longValue());
        else
            set(0);
    }

    @Override
    protected void setPropertyValue(Number value)
    {
        if (value != null)
            this.value = value.longValue();
        else
            this.value = 0;
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

    public void set(long value)
    {
        if (isBound())
            throw new UnsupportedOperationException("Cannot set the value of a bound property");
        this.value = value;
        invalidate();
    }

    @Override
    public long get()
    {
        return observable == null ? value : observable.getValue().longValue();
    }

    /**
     * Wrap an existing source of value to this LongProperty<br>
     * The LongProperty will be set with the current value of the source.
     * <p></p>
     * Changes to the LongProperty will be applied to the source of value.<br>
     * Changes to the source will not be applied to the LongProperty.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #link(Supplier, Consumer)}
     *
     * @param getter of the value source
     * @param setter of the value source
     */
    public void wrap(LongSupplier getter, LongConsumer setter)
    {
        Objects.requireNonNull(getter);
        Objects.requireNonNull(setter);

        setValue(getter.getAsLong());
        addChangeListener(obs -> setter.accept(getValue()));
    }

    /**
     * Wrap an existing source of value to this LongProperty with a bijective transform<br>
     * The LongProperty will be set with the current value of the source.
     * <p></p>
     * Changes to the LongProperty will be applied to the source of value.<br>
     * Changes to the source will not be applied to the LongProperty.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #linkMap(Supplier, Consumer, Function, Function)}
     *
     * @param getter             of the value source
     * @param setter             of the value source
     * @param transformer        mapping function from source to property
     * @param reverseTransformer inverse mapping function from property to source
     * @param <S>                type of the source
     */
    public <S> void wrapMap(Supplier<S> getter, Consumer<S> setter, ToLongFunction<S> transformer, LongFunction<S> reverseTransformer)
    {
        Objects.requireNonNull(getter);
        Objects.requireNonNull(setter);
        Objects.requireNonNull(transformer);
        Objects.requireNonNull(reverseTransformer);

        setValue(transformer.applyAsLong(getter.get()));
        addChangeListener(obs -> setter.accept(reverseTransformer.apply(getValue())));
    }

    /**
     * Observe an existing source of value and propagate changes to this LongProperty<br>
     * The LongProperty will be set with the current value of the source.
     * <p></p>
     * Changes to the source will be applied to the LongProperty.<br>
     * Changes to the LongProperty will not be applied to the source of value.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #link(Supplier, Consumer)}
     *
     * @param getter of the value source
     * @return LongPropertyHandle for this LongProperty set with the current value of the source
     */
    public LongPropertyHandle observe(LongSupplier getter)
    {
        Objects.requireNonNull(getter);

        setValue(getter.getAsLong());
        return new LongPropertyHandle(this, getter);
    }

    /**
     * Observe an existing source of value and propagate changes to this LongProperty with a transform<br>
     * The LongProperty will be set with the current value of the source.
     * <p></p>
     * Changes to the source will be applied to the LongProperty.<br>
     * Changes to the LongProperty will not be applied to the source of value.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #linkMap(Supplier, Consumer, Function, Function)}
     *
     * @param getter      of the value source
     * @param transformer mapping function from source to property
     * @param <S>         type of the source
     * @return LongPropertyHandle for this LongProperty set with the current value of the source
     */
    public <S> LongPropertyHandle observeMap(Supplier<S> getter, ToLongFunction<S> transformer)
    {
        Objects.requireNonNull(getter);
        Objects.requireNonNull(transformer);

        setValue(transformer.applyAsLong(getter.get()));
        return new LongPropertyHandle(this, () -> transformer.applyAsLong(getter.get()));
    }

    /**
     * Link an existing source of value to this LongProperty<br>
     * The LongProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the LongProperty will be applied to the source of value.<br>
     * Changes to the source will be applied using the LongPropertyHandle with {@link LongPropertyHandle#update()}<br>
     *
     * @param getter of the value source
     * @param setter of the value source
     * @return LongPropertyHandle for this LongProperty set with the current value of the source
     */
    public LongPropertyHandle link(LongSupplier getter, LongConsumer setter)
    {
        Objects.requireNonNull(getter);
        Objects.requireNonNull(setter);

        setValue(getter.getAsLong());
        addChangeListener(obs -> setter.accept(getValue()));
        return new LongPropertyHandle(this, getter);
    }

    /**
     * Link an existing source of value to this LongProperty with a bijective transform<br>
     * The LongProperty will be set with the current value of the source.
     * <p></p>
     * Changes to the LongProperty will be applied to the source of value.<br>
     * Changes to the source will be applied using the LongPropertyHandle with {@link LongPropertyHandle#update()}<br>
     *
     * @param getter             of the value source
     * @param setter             of the value source
     * @param transformer        mapping function from source to property
     * @param reverseTransformer inverse mapping function from property to source
     * @param <S>                type of the source
     * @return LongPropertyHandle for this LongProperty set with the current value of the source
     */
    public <S> LongPropertyHandle linkMap(Supplier<S> getter, Consumer<S> setter, ToLongFunction<S> transformer, LongFunction<S> reverseTransformer)
    {
        Objects.requireNonNull(getter);
        Objects.requireNonNull(setter);
        Objects.requireNonNull(transformer);
        Objects.requireNonNull(reverseTransformer);

        setValue(transformer.applyAsLong(getter.get()));
        addChangeListener(obs -> setter.accept(reverseTransformer.apply(getValue())));
        return new LongPropertyHandle(this, () -> transformer.applyAsLong(getter.get()));
    }

    /**
     * Wrap an existing source of value to a LongProperty<br>
     * The LongProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the LongProperty will be applied to the source of value.<br>
     * Changes to the source will not be applied to the LongProperty.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #fromLink(LongSupplier, LongConsumer)}
     *
     * @param getter of the value source
     * @param setter of the value source
     * @return created LongProperty with the current value of the source
     */
    public static LongProperty fromWrap(LongSupplier getter, LongConsumer setter)
    {
        LongProperty property = new LongProperty();
        property.wrap(getter, setter);
        return property;
    }

    /**
     * Wrap an existing source of value to a LongProperty with a bijective transform<br>
     * The LongProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the LongProperty will be applied to the source of value.<br>
     * Changes to the source will not be applied to the LongProperty.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #fromLink(LongSupplier, LongConsumer)}
     *
     * @param getter             of the value source
     * @param setter             of the value source
     * @param transformer        mapping function from source to property
     * @param reverseTransformer inverse mapping function from property to source
     * @param <S>                type of the source
     * @return created LongProperty with the current value of the source
     */
    public static <S> LongProperty fromWrapMap(Supplier<S> getter, Consumer<S> setter, ToLongFunction<S> transformer, LongFunction<S> reverseTransformer)
    {
        LongProperty property = new LongProperty();
        property.wrapMap(getter, setter, transformer, reverseTransformer);
        return property;
    }

    /**
     * Observe an existing source of value and propagate changes to a LongProperty<br>
     * The LongProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the source will be applied to the Property.<br>
     * Changes to the LongProperty will not be applied to the source of value.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #fromLink(LongSupplier, LongConsumer)}
     *
     * @param getter of the value source
     * @return LongPropertyHandle for the newly created LongProperty with the current value of the source
     */
    public static LongPropertyHandle fromObserve(LongSupplier getter)
    {
        LongProperty property = new LongProperty();
        return property.observe(getter);
    }

    /**
     * Observe an existing source of value and propagate changes to a LongProperty with a transform<br>
     * The LongProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the source will be applied to the LongProperty.<br>
     * Changes to the LongProperty will not be applied to the source of value.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #fromLinkMap(Supplier, Consumer, ToLongFunction, LongFunction)}
     *
     * @param getter      of the value source
     * @param transformer mapping function from source to property
     * @param <S>         type of the source
     * @return LongPropertyHandle for the newly created LongProperty with the current value of the source
     */
    public static <S> LongPropertyHandle fromObserveMap(Supplier<S> getter, ToLongFunction<S> transformer)
    {
        LongProperty property = new LongProperty();
        return property.observeMap(getter, transformer);
    }

    /**
     * Link an existing source of value to a LongProperty<br>
     * The LongProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the LongProperty will be applied to the source of value.<br>
     * Changes to the source will be applied using the LongPropertyHandle with {@link LongPropertyHandle#update()}<br>
     *
     * @param getter of the value source
     * @param setter of the value source
     * @return created LongProperty with the current value of the source
     */
    public static LongPropertyHandle fromLink(LongSupplier getter, LongConsumer setter)
    {
        LongProperty property = new LongProperty();
        return property.link(getter, setter);
    }

    /**
     * Link an existing source of value to a LongProperty with a bijective transform<br>
     * The LongProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the LongProperty will be applied to the source of value.<br>
     * Changes to the source will be applied using the LongPropertyHandle with {@link LongPropertyHandle#update()}<br>
     *
     * @param getter             of the value source
     * @param setter             of the value source
     * @param transformer        mapping function from source to property
     * @param reverseTransformer inverse mapping function from property to source
     * @param <S>                type of the source
     * @return created LongProperty with the current value of the source
     */
    public static <S> LongPropertyHandle fromLinkMap(Supplier<S> getter, Consumer<S> setter, ToLongFunction<S> transformer, LongFunction<S> reverseTransformer)
    {
        LongProperty property = new LongProperty();
        return property.linkMap(getter, setter, transformer, reverseTransformer);
    }
}
