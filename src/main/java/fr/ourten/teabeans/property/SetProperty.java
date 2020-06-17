package fr.ourten.teabeans.property;

import fr.ourten.teabeans.listener.ListValueChangeListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public class SetProperty<T> extends Property<Set<T>> implements ISetProperty<T>
{
    private Supplier<Set<T>> setSupplier;

    private Set<T> immutableView;

    /**
     * The list of attached listeners that need to be notified when the value
     * change.
     */
    private final ArrayList<ListValueChangeListener<? super T>> listValueChangeListeners;

    public SetProperty(Supplier<Set<T>> setSupplier, Set<T> value)
    {
        super(value);
        listValueChangeListeners = new ArrayList<>();

        this.value = setSupplier.get();
        if (value != null)
            this.value.addAll(value);
        this.setSupplier = setSupplier;
    }

    public SetProperty(Set<T> value)
    {
        this(HashSet::new, value);
    }

    @Override
    protected void setPropertyValue(Set<T> value)
    {
        if (immutableView != null && !Objects.equals(value, this.value))
            immutableView = Collections.unmodifiableSet(value);

        super.setPropertyValue(value);
    }

    @Override
    public Set<T> getValue()
    {
        if (immutableView == null)
            immutableView = Collections.unmodifiableSet(value);
        return immutableView;
    }

    public Set<T> getModifiableValue()
    {
        return value;
    }

    @Override
    public void addListener(ListValueChangeListener<? super T> listener)
    {
        if (!isObserving() && hasObservable())
            startObserving();
        if (!listValueChangeListeners.contains(listener))
            listValueChangeListeners.add(listener);
    }

    @Override
    public void removeListener(ListValueChangeListener<? super T> listener)
    {
        listValueChangeListeners.remove(listener);
        if (listValueChangeListeners.isEmpty() && hasObservable())
            stopObserving();
    }

    @Override
    public void add(T element)
    {
        Set<T> oldSet = null;
        if (!valueChangeListeners.isEmpty())
        {
            oldSet = setSupplier.get();
            oldSet.addAll(value);
        }

        boolean added = value.add(element);

        if (added)
            invalidateElement(null, element, oldSet);
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
        if (!valueChangeListeners.isEmpty())
        {
            oldSet = setSupplier.get();
            oldSet.addAll(value);
        }
        boolean removed = value.remove(element);

        if (removed)
            invalidateElement(element, null, oldSet);
        return removed;
    }

    @Override
    public boolean contains(T element)
    {
        return value.contains(element);
    }

    @Override
    public void replace(T oldElement, T newElement)
    {
        T oldValue = value.contains(oldElement) ? oldElement : null;
        Set<T> oldSet = null;
        if (!valueChangeListeners.isEmpty())
        {
            oldSet = setSupplier.get();
            oldSet.addAll(value);
        }

        value.remove(oldElement);
        value.add(newElement);

        invalidateElement(oldElement, newElement, oldSet);
    }

    public void invalidateElement(T oldElement, T newElement, Set<T> oldSet)
    {
        if (isMuted())
            return;

        fireListChangeListeners(oldElement, newElement);
        invalidate(oldSet);
    }

    protected void invalidate(Set<T> oldSet)
    {
        oldValue = oldSet;
        invalidate();
    }

    @Override
    public void clear()
    {
        Set<T> oldSet = null;

        if (!valueChangeListeners.isEmpty() || !listValueChangeListeners.isEmpty())
        {
            oldSet = setSupplier.get();
            oldSet.addAll(value);
        }
        value.clear();

        if (isMuted())
            return;

        if (oldSet != null)
            oldSet.forEach(oldValue -> fireListChangeListeners(oldValue, null));
        invalidate(oldSet);
    }

    @Override
    public int size()
    {
        return getValue().size();
    }

    @Override
    public Iterator<T> iterator()
    {
        return getValue().iterator();
    }

    @Override
    protected boolean hasListeners()
    {
        return super.hasListeners() || !listValueChangeListeners.isEmpty();
    }

    private void fireListChangeListeners(T oldValue, T newValue)
    {
        for (int i = 0, listValueChangeListenersSize = listValueChangeListeners.size(); i < listValueChangeListenersSize; i++)
        {
            ListValueChangeListener<? super T> listener = listValueChangeListeners.get(i);
            listener.valueChanged(this, oldValue, newValue);
        }
    }
}
