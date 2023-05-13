package fr.ourten.teabeans.property;

import fr.ourten.teabeans.listener.SetValueChangeListener;
import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.listener.holder.ListenersHolder;
import fr.ourten.teabeans.listener.holder.SetListenersHolder;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public class SetProperty<T> extends Property<Set<T>> implements ISetProperty<T>
{
    private final Supplier<Set<T>> setSupplier;

    private SetListenersHolder<T> listenersHolder;

    private Set<T> immutableView;

    public SetProperty(Supplier<Set<T>> setSupplier, Set<T> value)
    {
        super(value);

        this.value = setSupplier.get();
        if (value != null)
            this.value.addAll(value);
        this.setSupplier = setSupplier;
    }

    public SetProperty(Set<T> value)
    {
        this(HashSet::new, value);
    }

    public SetProperty()
    {
        this(null);
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
    public void addSetChangeListener(SetValueChangeListener<? super T> listener)
    {
        startObserving();
        listenersHolder = SetListenersHolder.addSetChangeListener(listenersHolder, listener);
    }

    @Override
    public void removeSetChangeListener(SetValueChangeListener<? super T> listener)
    {
        listenersHolder = SetListenersHolder.removeSetChangeListener(listenersHolder, listener);
        stopObserving();
    }

    @Override
    public void addChangeListener(ValueChangeListener<? super Set<T>> listener)
    {
        startObserving();
        listenersHolder = SetListenersHolder.addChangeListener(listenersHolder, listener);
    }

    @Override
    public void removeChangeListener(ValueChangeListener<? super Set<T>> listener)
    {
        listenersHolder = SetListenersHolder.removeChangeListener(listenersHolder, listener);
        stopObserving();
    }

    @Override
    public void addListener(ValueInvalidationListener listener)
    {
        startObserving();
        listenersHolder = SetListenersHolder.addListener(listenersHolder, listener);
    }

    @Override
    public void removeListener(ValueInvalidationListener listener)
    {
        listenersHolder = SetListenersHolder.removeListener(listenersHolder, listener);
        stopObserving();
    }

    @Override
    public void addChangeListener(ValueInvalidationListener listener)
    {
        startObserving();
        listenersHolder = SetListenersHolder.addChangeListener(listenersHolder, listener);
    }

    @Override
    public void removeChangeListener(ValueInvalidationListener listener)
    {
        listenersHolder = SetListenersHolder.removeListener(listenersHolder, listener);
        stopObserving();
    }

    @Override
    public void add(T element)
    {
        Set<T> oldSet = null;
        if (ListenersHolder.hasChangeListeners(listenersHolder))
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
        if (ListenersHolder.hasChangeListeners(listenersHolder))
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
        if (ListenersHolder.hasChangeListeners(listenersHolder))
        {
            oldSet = setSupplier.get();
            oldSet.addAll(value);
        }

        value.remove(oldElement);
        value.add(newElement);

        invalidateElement(oldValue, newElement, oldSet);
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

        if (ListenersHolder.hasChangeListeners(listenersHolder) || SetListenersHolder.hasListChangeListeners(listenersHolder))
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
        return listenersHolder != null;
    }

    private void fireListChangeListeners(T oldValue, T newValue)
    {
        SetListenersHolder.fireListChangeListeners(listenersHolder, this, oldValue, newValue);
    }

    @Override
    protected void fireChangeListeners(Set<T> oldValue, Set<T> newValue)
    {
        SetListenersHolder.fireChangeListeners(listenersHolder, this, oldValue, newValue);
    }

    @Override
    protected void fireInvalidationListeners()
    {
        SetListenersHolder.fireInvalidationListeners(listenersHolder, this);
    }

    @Override
    protected void fireChangeArglessListeners()
    {
        SetListenersHolder.fireChangeArglessListeners(listenersHolder, this);
    }
}
