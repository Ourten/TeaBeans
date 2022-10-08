package fr.ourten.teabeans.property;

import fr.ourten.teabeans.property.handle.PropertyHandle;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Property<T> extends PropertyBase<T>
{
    protected T value;
    protected T oldValue;

    public Property()
    {
        this(null);
    }

    public Property(T value)
    {
        this.value = value;
        oldValue = value;
    }

    @Override
    public T getValue()
    {
        return observable == null ? value : observable.getValue();
    }

    @Override
    public void setValue(T value)
    {
        if (isBound())
            throw new UnsupportedOperationException("Cannot set the value of a bound property");
        setPropertyValue(value);
    }

    @Override
    protected void setPropertyValue(T value)
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

        if (!Objects.equals(value, oldValue))
        {
            fireChangeArglessListeners();
            fireChangeListeners(oldValue, value);
        }
        fireInvalidationListeners();

        oldValue = value;
    }

    @Override
    protected void afterBindProperty()
    {
        if (value == null || !value.equals(observable.getValue()))
        {
            if (isPristine())
                setPristine(false);

            fireChangeArglessListeners();
            fireChangeListeners(value, observable.getValue());
        }
        fireInvalidationListeners();
    }

    /**
     * Wrap an existing source of value to a Property<br>
     * The Property will be initialized with the current value of the source.
     * <p></p>
     * Changes to the Property will be applied to the source of value.<br>
     * Changes to the source will not be applied to the Property.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #fromLink(Supplier, Consumer)}
     *
     * @param currentValue of the source
     * @param setter       of the value source
     * @param <T>          contained type of Property and source
     * @return created Property with the current value of the source
     */
    public static <T> Property<T> fromWrap(T currentValue, Consumer<T> setter)
    {
        Property<T> property = new Property<>();
        property.wrap(currentValue, setter);
        return property;
    }

    /**
     * Wrap an existing source of value to a Property with a bijective transform<br>
     * The Property will be initialized with the current value of the source.
     * <p></p>
     * Changes to the Property will be applied to the source of value.<br>
     * Changes to the source will not be applied to the Property.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #fromLinkMap(Supplier, Consumer, Function, Function)}
     *
     * @param currentValue       of the source
     * @param setter             of the value source
     * @param transformer        mapping function from source to property
     * @param reverseTransformer inverse mapping function from property to source
     * @param <T>                contained type of Property
     * @param <S>                type of the source
     * @return created Property with the current value of the source
     */
    public static <T, S> Property<T> fromWrapMap(S currentValue, Consumer<S> setter, Function<S, T> transformer, Function<T, S> reverseTransformer)
    {
        Property<T> property = new Property<>();
        property.wrapMap(currentValue, setter, transformer, reverseTransformer);
        return property;
    }

    /**
     * Observe an existing source of value and propagate changes to a Property<br>
     * The Property will be initialized with the current value of the source.
     * <p></p>
     * Changes to the source will be applied to the Property.<br>
     * Changes to the Property will not be applied to the source of value.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #fromLink(Supplier, Consumer)}
     *
     * @param getter of the value source
     * @param <T>    contained type of Property and source
     * @return PropertyHandle for the newly created Property with the current value of the source
     */
    public static <T> PropertyHandle<T> fromObserve(Supplier<T> getter)
    {
        Property<T> property = new Property<>();
        return property.observe(getter);
    }

    /**
     * Observe an existing source of value and propagate changes to a Property with a transform<br>
     * The Property will be initialized with the current value of the source.
     * <p></p>
     * Changes to the source will be applied to the Property.<br>
     * Changes to the Property will not be applied to the source of value.<br>
     * <p></p>
     * When bidirectional changes are needed use {@link #fromLinkMap(Supplier, Consumer, Function, Function)}
     *
     * @param getter      of the value source
     * @param transformer mapping function from source to property
     * @param <T>         contained type of Property
     * @param <S>         type of the source
     * @return PropertyHandle for the newly created Property with the current value of the source
     */
    public static <T, S> PropertyHandle<T> fromObserveMap(Supplier<S> getter, Function<S, T> transformer)
    {
        Property<T> property = new Property<>();
        return property.observeMap(getter, transformer);
    }

    /**
     * Link an existing source of value to a Property<br>
     * The Property will be initialized with the current value of the source.
     * <p></p>
     * Changes to the Property will be applied to the source of value.<br>
     * Changes to the source will be applied using the PropertyHandle with {@link PropertyHandle#update()}<br>
     *
     * @param getter of the value source
     * @param setter of the value source
     * @param <T>    contained type of Property and source
     * @return PropertyHandle for the newly created Property with the current value of the source
     */
    public static <T> PropertyHandle<T> fromLink(Supplier<T> getter, Consumer<T> setter)
    {
        Property<T> property = new Property<>();
        return property.link(getter, setter);
    }

    /**
     * Link an existing source of value to a Property with a bijective transform<br>
     * The Property will be initialized with the current value of the source.
     * <p></p>
     * Changes to the Property will be applied to the source of value.<br>
     * Changes to the source will be applied using the PropertyHandle with {@link PropertyHandle#update()}<br>
     *
     * @param getter             of the value source
     * @param setter             of the value source
     * @param transformer        mapping function from source to property
     * @param reverseTransformer inverse mapping function from property to source
     * @param <T>                contained type of Property and source
     * @param <S>                type of the source
     * @return PropertyHandle for the newly created Property with the current value of the source
     */
    public static <T, S> PropertyHandle<T> fromLinkMap(Supplier<S> getter, Consumer<S> setter, Function<S, T> transformer, Function<T, S> reverseTransformer)
    {
        Property<T> property = new Property<>();
        return property.linkMap(getter, setter, transformer, reverseTransformer);
    }
}