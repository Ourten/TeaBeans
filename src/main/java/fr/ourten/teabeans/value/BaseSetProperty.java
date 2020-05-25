package fr.ourten.teabeans.value;

import fr.ourten.teabeans.listener.ListValueChangeListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class BaseSetProperty<T> extends BaseProperty<Set<T>> implements SetProperty<T>
{
    private Supplier<Set<T>>    setSupplier;
    private BiFunction<T, T, T> checker;

    private Set<T> immutableView;

    /**
     * The list of attached listeners that need to be notified when the value
     * change.
     */
    private final ArrayList<ListValueChangeListener<? super T>> listValueChangeListeners;

    public BaseSetProperty(final Supplier<Set<T>> setSupplier, final Set<T> value, final String name)
    {
        super(value, name);
        this.listValueChangeListeners = new ArrayList<>();

        this.value = setSupplier.get();
        if (value != null)
            this.value.addAll(value);
        this.setSupplier = setSupplier;
    }

    public BaseSetProperty(final Set<T> value, final String name)
    {
        this(HashSet::new, value, name);
    }

    public BaseSetProperty(final Supplier<Set<T>> setSupplier, final Set<T> value)
    {
        this(setSupplier, value, "");
    }

    public BaseSetProperty(final Set<T> value)
    {
        this(value, "");
    }

    @Override
    public Set<T> getValue()
    {
        if (immutableView == null)
            immutableView = Collections.unmodifiableSet(this.value);
        return immutableView;
    }

    public Set<T> getModifiableValue()
    {
        return this.value;
    }

    @Override
    public void addListener(final ListValueChangeListener<? super T> listener)
    {
        if (!this.listValueChangeListeners.contains(listener))
            this.listValueChangeListeners.add(listener);
    }

    @Override
    public void removeListener(final ListValueChangeListener<?> listener)
    {
        this.listValueChangeListeners.remove(listener);
    }

    public BiFunction<T, T, T> getElementChecker()
    {
        return this.checker;
    }

    public void setElementChecker(final BiFunction<T, T, T> checker)
    {
        this.checker = checker;
    }

    @Override
    public void add(T element)
    {
        Set<T> oldSet = null;
        if (!this.valueChangeListeners.isEmpty())
        {
            oldSet = this.setSupplier.get();
            oldSet.addAll(this.value);
        }

        if (this.checker != null)
            element = this.checker.apply(null, element);

        boolean added = this.value.add(element);

        if (added)
            this.invalidateElement(null, element, oldSet);
    }

    @Override
    public void addAll(Collection<T> elements)
    {
        elements.forEach(this::add);
    }

    @Override
    public boolean remove(T element)
    {
        Set<T> oldSet = null;
        if (!this.valueChangeListeners.isEmpty())
        {
            oldSet = this.setSupplier.get();
            oldSet.addAll(this.value);
        }
        boolean removed = this.value.remove(element);

        if (removed)
            this.invalidateElement(element, null, oldSet);
        return removed;
    }

    @Override
    public boolean contains(final T element)
    {
        return this.value.contains(element);
    }

    @Override
    public void replace(T oldElement, T newElement)
    {
        final T oldValue = this.value.contains(oldElement) ? oldElement : null;
        Set<T> oldSet = null;
        if (!this.valueChangeListeners.isEmpty())
        {
            oldSet = this.setSupplier.get();
            oldSet.addAll(this.value);
        }

        if (this.checker != null)
            newElement = this.checker.apply(oldValue, newElement);

        this.value.remove(oldElement);
        this.value.add(newElement);

        this.invalidateElement(oldElement, newElement, oldSet);
    }

    public void invalidateElement(T oldElement, T newElement, Set<T> oldSet)
    {
        if (this.isMuted())
            return;

        this.fireListChangeListeners(oldElement, newElement);
        this.invalidate(oldSet);
    }

    @Override
    public void clear()
    {
        Set<T> oldSet = null;

        if (!this.valueChangeListeners.isEmpty() || !this.listValueChangeListeners.isEmpty())
        {
            oldSet = this.setSupplier.get();
            oldSet.addAll(this.value);
        }
        this.value.clear();

        if (this.isMuted())
            return;

        if (oldSet != null)
            oldSet.forEach(oldValue -> this.fireListChangeListeners(oldValue, null));
        this.invalidate(oldSet);
    }

    private void fireListChangeListeners(final T oldValue, final T newValue)
    {
        for (final ListValueChangeListener<? super T> listener : this.listValueChangeListeners)
            listener.valueChanged(this, oldValue, newValue);
    }

    @Override
    public int size()
    {
        return this.getValue().size();
    }
}
