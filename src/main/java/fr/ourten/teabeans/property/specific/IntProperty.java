package fr.ourten.teabeans.property.specific;

import fr.ourten.teabeans.property.PropertyBase;
import fr.ourten.teabeans.property.handle.specific.IntPropertyHandle;
import fr.ourten.teabeans.value.specific.IntValue;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class IntProperty extends PropertyBase<Number> implements IntValue
{
    protected int value;
    protected int oldValue;

    public IntProperty()
    {
        this(0);
    }

    public IntProperty(int value)
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
    public Integer getValue()
    {
        return get();
    }

    @Override
    public void setValue(Number value)
    {
        if (value != null)
            set(value.intValue());
        else
            set(0);
    }

    @Override
    protected void setPropertyValue(Number value)
    {
        if (value != null)
            this.value = value.intValue();
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

    public void set(int value)
    {
        if (isBound())
            throw new UnsupportedOperationException("Cannot set the value of a bound property");
        this.value = value;
        invalidate();
    }

    @Override
    public int get()
    {
        return observable == null ? value : observable.getValue().intValue();
    }

    /**
     * Wrap an existing source of value to this IntProperty<br>
     * The IntProperty will be set with the current value of the source.
     * <p></p>
     * Changes to the IntProperty will be applied to the source of value.<br>
     * Changes to the source will not be applied to the IntProperty.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #link(Supplier, Consumer)}
     *
     * @param getter of the value source
     * @param setter of the value source
     */
    public void wrap(IntSupplier getter, IntConsumer setter)
    {
        Objects.requireNonNull(getter);
        Objects.requireNonNull(setter);

        setValue(getter.getAsInt());
        addChangeListener(obs -> setter.accept(getValue()));
    }

    /**
     * Wrap an existing source of value to this IntProperty with a bijective transform<br>
     * The IntProperty will be set with the current value of the source.
     * <p></p>
     * Changes to the IntProperty will be applied to the source of value.<br>
     * Changes to the source will not be applied to the IntProperty.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #linkMap(Supplier, Consumer, Function, Function)}
     *
     * @param getter             of the value source
     * @param setter             of the value source
     * @param transformer        mapping function from source to property
     * @param reverseTransformer inverse mapping function from property to source
     * @param <S>                type of the source
     */
    public <S> void wrapMap(Supplier<S> getter, Consumer<S> setter, ToIntFunction<S> transformer, IntFunction<S> reverseTransformer)
    {
        Objects.requireNonNull(getter);
        Objects.requireNonNull(setter);
        Objects.requireNonNull(transformer);
        Objects.requireNonNull(reverseTransformer);

        setValue(transformer.applyAsInt(getter.get()));
        addChangeListener(obs -> setter.accept(reverseTransformer.apply(getValue())));
    }

    /**
     * Observe an existing source of value and propagate changes to this IntProperty<br>
     * The IntProperty will be set with the current value of the source.
     * <p></p>
     * Changes to the source will be applied to the IntProperty.<br>
     * Changes to the IntProperty will not be applied to the source of value.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #link(Supplier, Consumer)}
     *
     * @param getter of the value source
     * @return IntPropertyHandle for this IntProperty set with the current value of the source
     */
    public IntPropertyHandle observe(IntSupplier getter)
    {
        Objects.requireNonNull(getter);

        setValue(getter.getAsInt());
        return new IntPropertyHandle(this, getter);
    }

    /**
     * Observe an existing source of value and propagate changes to this IntProperty with a transform<br>
     * The IntProperty will be set with the current value of the source.
     * <p></p>
     * Changes to the source will be applied to the IntProperty.<br>
     * Changes to the IntProperty will not be applied to the source of value.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #linkMap(Supplier, Consumer, Function, Function)}
     *
     * @param getter      of the value source
     * @param transformer mapping function from source to property
     * @param <S>         type of the source
     * @return IntPropertyHandle for this IntProperty set with the current value of the source
     */
    public <S> IntPropertyHandle observeMap(Supplier<S> getter, ToIntFunction<S> transformer)
    {
        Objects.requireNonNull(getter);
        Objects.requireNonNull(transformer);

        setValue(transformer.applyAsInt(getter.get()));
        return new IntPropertyHandle(this, () -> transformer.applyAsInt(getter.get()));
    }

    /**
     * Link an existing source of value to this IntProperty<br>
     * The IntProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the IntProperty will be applied to the source of value.<br>
     * Changes to the source will be applied using the IntPropertyHandle with {@link IntPropertyHandle#update()}<br>
     *
     * @param getter of the value source
     * @param setter of the value source
     * @return IntPropertyHandle for this IntProperty set with the current value of the source
     */
    public IntPropertyHandle link(IntSupplier getter, IntConsumer setter)
    {
        Objects.requireNonNull(getter);
        Objects.requireNonNull(setter);

        setValue(getter.getAsInt());
        addChangeListener(obs -> setter.accept(getValue()));
        return new IntPropertyHandle(this, getter);
    }

    /**
     * Link an existing source of value to this IntProperty with a bijective transform<br>
     * The IntProperty will be set with the current value of the source.
     * <p></p>
     * Changes to the IntProperty will be applied to the source of value.<br>
     * Changes to the source will be applied using the IntPropertyHandle with {@link IntPropertyHandle#update()}<br>
     *
     * @param getter             of the value source
     * @param setter             of the value source
     * @param transformer        mapping function from source to property
     * @param reverseTransformer inverse mapping function from property to source
     * @param <S>                type of the source
     * @return IntPropertyHandle for this IntProperty set with the current value of the source
     */
    public <S> IntPropertyHandle linkMap(Supplier<S> getter, Consumer<S> setter, ToIntFunction<S> transformer, IntFunction<S> reverseTransformer)
    {
        Objects.requireNonNull(getter);
        Objects.requireNonNull(setter);
        Objects.requireNonNull(transformer);
        Objects.requireNonNull(reverseTransformer);

        setValue(transformer.applyAsInt(getter.get()));
        addChangeListener(obs -> setter.accept(reverseTransformer.apply(getValue())));
        return new IntPropertyHandle(this, () -> transformer.applyAsInt(getter.get()));
    }

    /**
     * Wrap an existing source of value to a IntProperty<br>
     * The IntProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the IntProperty will be applied to the source of value.<br>
     * Changes to the source will not be applied to the IntProperty.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #fromLink(IntSupplier, IntConsumer)}
     *
     * @param getter of the value source
     * @param setter of the value source
     * @return created IntProperty with the current value of the source
     */
    public static IntProperty fromWrap(IntSupplier getter, IntConsumer setter)
    {
        IntProperty property = new IntProperty();
        property.wrap(getter, setter);
        return property;
    }

    /**
     * Wrap an existing source of value to a IntProperty with a bijective transform<br>
     * The IntProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the IntProperty will be applied to the source of value.<br>
     * Changes to the source will not be applied to the IntProperty.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #fromLink(IntSupplier, IntConsumer)}
     *
     * @param getter             of the value source
     * @param setter             of the value source
     * @param transformer        mapping function from source to property
     * @param reverseTransformer inverse mapping function from property to source
     * @param <S>                type of the source
     * @return created IntProperty with the current value of the source
     */
    public static <S> IntProperty fromWrapMap(Supplier<S> getter, Consumer<S> setter, ToIntFunction<S> transformer, IntFunction<S> reverseTransformer)
    {
        IntProperty property = new IntProperty();
        property.wrapMap(getter, setter, transformer, reverseTransformer);
        return property;
    }

    /**
     * Observe an existing source of value and propagate changes to a IntProperty<br>
     * The IntProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the source will be applied to the Property.<br>
     * Changes to the IntProperty will not be applied to the source of value.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #fromLink(IntSupplier, IntConsumer)}
     *
     * @param getter of the value source
     * @return IntPropertyHandle for the newly created IntProperty with the current value of the source
     */
    public static IntPropertyHandle fromObserve(IntSupplier getter)
    {
        IntProperty property = new IntProperty();
        return property.observe(getter);
    }

    /**
     * Observe an existing source of value and propagate changes to a IntProperty with a transform<br>
     * The IntProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the source will be applied to the IntProperty.<br>
     * Changes to the IntProperty will not be applied to the source of value.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #fromLinkMap(Supplier, Consumer, ToIntFunction, IntFunction)}
     *
     * @param getter      of the value source
     * @param transformer mapping function from source to property
     * @param <S>         type of the source
     * @return IntPropertyHandle for the newly created IntProperty with the current value of the source
     */
    public static <S> IntPropertyHandle fromObserveMap(Supplier<S> getter, ToIntFunction<S> transformer)
    {
        IntProperty property = new IntProperty();
        return property.observeMap(getter, transformer);
    }

    /**
     * Link an existing source of value to a IntProperty<br>
     * The IntProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the IntProperty will be applied to the source of value.<br>
     * Changes to the source will be applied using the IntPropertyHandle with {@link IntPropertyHandle#update()}<br>
     *
     * @param getter of the value source
     * @param setter of the value source
     * @return created IntProperty with the current value of the source
     */
    public static IntPropertyHandle fromLink(IntSupplier getter, IntConsumer setter)
    {
        IntProperty property = new IntProperty();
        return property.link(getter, setter);
    }

    /**
     * Link an existing source of value to a IntProperty with a bijective transform<br>
     * The IntProperty will be initialized with the current value of the source.
     * <p></p>
     * Changes to the IntProperty will be applied to the source of value.<br>
     * Changes to the source will be applied using the IntPropertyHandle with {@link IntPropertyHandle#update()}<br>
     *
     * @param getter             of the value source
     * @param setter             of the value source
     * @param transformer        mapping function from source to property
     * @param reverseTransformer inverse mapping function from property to source
     * @param <S>                type of the source
     * @return created IntProperty with the current value of the source
     */
    public static <S> IntPropertyHandle fromLinkMap(Supplier<S> getter, Consumer<S> setter, ToIntFunction<S> transformer, IntFunction<S> reverseTransformer)
    {
        IntProperty property = new IntProperty();
        return property.linkMap(getter, setter, transformer, reverseTransformer);
    }
}
