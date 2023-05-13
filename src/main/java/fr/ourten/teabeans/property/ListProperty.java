package fr.ourten.teabeans.property;

import fr.ourten.teabeans.listener.ListChange;
import fr.ourten.teabeans.listener.ListValueChangeListener;
import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.listener.holder.ListListenersHolder;
import fr.ourten.teabeans.listener.holder.ListenersHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ListProperty<T> extends PropertyBase<List<T>> implements IListProperty<T>
{
    private final Supplier<List<T>> listSupplier;

    private ListListenersHolder<T> listenersHolder;

    private List<T> immutableView;

    protected List<T> value;
    protected List<T> oldValue;

    private final ListChange<T> changeEvent = new ListChange<>(this);

    public ListProperty(Supplier<List<T>> listSupplier, List<T> value)
    {
        this.value = value;
        oldValue = value;

        this.value = listSupplier.get();
        if (value != null)
            this.value.addAll(value);
        this.listSupplier = listSupplier;
    }

    public ListProperty(List<T> value)
    {
        this(ArrayList::new, value);
    }

    public ListProperty()
    {
        this(null);
    }

    @Override
    public List<T> getValue()
    {
        if (observable != null)
            return observable.getValue();

        if (immutableView == null)
            immutableView = Collections.unmodifiableList(value);
        return immutableView;
    }

    @Override
    protected void setPropertyValue(List<T> value)
    {
        if (immutableView != null && !Objects.equals(value, this.value))
            immutableView = Collections.unmodifiableList(value);

        this.value = value;
        invalidate();

        if (isPristine())
            setPristine(false);
    }

    public List<T> getModifiableValue()
    {
        return value;
    }

    @Override
    public void addListChangeListener(ListValueChangeListener<? super T> listener)
    {
        startObserving();
        listenersHolder = ListListenersHolder.addListChangeListener(listenersHolder, listener);
    }

    @Override
    public void removeListChangeListener(ListValueChangeListener<? super T> listener)
    {
        listenersHolder = ListListenersHolder.removeListChangeListener(listenersHolder, listener);
        stopObserving();
    }

    @Override
    public void addChangeListener(ValueChangeListener<? super List<T>> listener)
    {
        startObserving();
        listenersHolder = ListListenersHolder.addChangeListener(listenersHolder, listener);
    }

    @Override
    public void removeChangeListener(ValueChangeListener<? super List<T>> listener)
    {
        listenersHolder = ListListenersHolder.removeChangeListener(listenersHolder, listener);
        stopObserving();
    }

    @Override
    public void addListener(ValueInvalidationListener listener)
    {
        startObserving();
        listenersHolder = ListListenersHolder.addListener(listenersHolder, listener);
    }

    @Override
    public void removeListener(ValueInvalidationListener listener)
    {
        listenersHolder = ListListenersHolder.removeListener(listenersHolder, listener);
        stopObserving();
    }

    @Override
    public void addChangeListener(ValueInvalidationListener listener)
    {
        startObserving();
        listenersHolder = ListListenersHolder.addChangeListener(listenersHolder, listener);
    }

    @Override
    public void removeChangeListener(ValueInvalidationListener listener)
    {
        listenersHolder = ListListenersHolder.removeListener(listenersHolder, listener);
        stopObserving();
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

    @Override
    public boolean add(T element)
    {
        List<T> oldList = null;
        if (ListenersHolder.hasChangeListeners(listenersHolder))
        {
            oldList = listSupplier.get();
            oldList.addAll(value);
        }

        boolean changed = value.add(element);

        if (changed)
        {
            changeEvent.forAddition(value.size() - 1, element);
            invalidateElement(oldList);
        }

        return changed;
    }

    @Override
    public void add(int index, T element)
    {
        List<T> oldList = null;
        if (ListenersHolder.hasChangeListeners(listenersHolder))
        {
            oldList = listSupplier.get();
            oldList.addAll(value);
        }

        value.add(index, element);

        changeEvent.forAddition(index, element);
        invalidateElement(oldList);
    }

    @Override
    public boolean addAll(Collection<? extends T> elements)
    {
        var changed = new AtomicBoolean(false);
        elements.forEach(element ->
        {
            if (add(element))
                changed.set(true);
        });
        return changed.get();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> elements)
    {
        var changed = new AtomicBoolean(false);

        for (T element : elements)
        {
            add(index, element);
            changed.set(true);
            index++;
        }
        return changed.get();
    }

    @Override
    public boolean removeAll(Collection<?> elements)
    {
        var changed = new AtomicBoolean(false);

        for (Object element : elements)
        {
            if (remove(element))
                changed.set(true);
        }
        return changed.get();
    }

    @Override
    public boolean retainAll(Collection<?> elements)
    {
        return removeIf(element -> !elements.contains(element));
    }

    @Override
    public boolean removeIf(Predicate<? super T> filter)
    {
        Objects.requireNonNull(filter);

        List<T> oldList = null;
        if (ListenersHolder.hasChangeListeners(listenersHolder))
        {
            oldList = listSupplier.get();
            oldList.addAll(value);
        }

        boolean removed = false;
        Iterator<T> each = iterator();
        while (each.hasNext())
        {
            T potentialRemove = each.next();
            if (filter.test(potentialRemove))
            {
                var removeIndex = indexOf(potentialRemove);
                each.remove();
                removed = true;

                if (!isMuted())
                {
                    changeEvent.forRemoval(removeIndex, potentialRemove);
                    fireListChangeListeners();
                }
            }
        }

        if (removed)
            invalidate(oldList);
        return removed;
    }

    @Override
    public T remove(int index)
    {
        List<T> oldList = null;
        if (ListenersHolder.hasChangeListeners(listenersHolder))
        {
            oldList = listSupplier.get();
            oldList.addAll(value);
        }
        T removed = value.remove(index);

        changeEvent.forRemoval(index, removed);
        invalidateElement(oldList);
        return removed;
    }

    @Override
    public T set(int index, T element)
    {
        T oldValue = value.get(index);
        List<T> oldList = null;
        if (ListenersHolder.hasChangeListeners(listenersHolder))
        {
            oldList = listSupplier.get();
            oldList.addAll(value);
        }

        value.set(index, element);

        changeEvent.forReplace(index, oldValue, element);
        invalidateElement(oldList);
        return oldValue;
    }

    public void invalidateElement(List<T> oldList)
    {
        if (isMuted())
            return;

        fireListChangeListeners();
        invalidate(oldList);
    }

    protected void invalidate(List<T> oldList)
    {
        oldValue = oldList;
        invalidate();
    }

    @Override
    public void clear()
    {
        List<T> oldList = null;

        if (ListenersHolder.hasChangeListeners(listenersHolder) || ListListenersHolder.hasListChangeListeners(listenersHolder))
        {
            oldList = listSupplier.get();
            oldList.addAll(value);
        }
        value.clear();

        if (isMuted())
            return;

        if (oldList != null)
        {
            for (int i = 0; i < oldList.size(); i++)
            {
                changeEvent.forRemoval(i, oldList.get(i));
                fireListChangeListeners();
            }
        }
        invalidate(oldList);
    }

    @Override
    public void sort(Comparator<? super T> comparator)
    {
        List<T> temp = listSupplier.get();
        temp.addAll(value);
        temp.sort(comparator);

        setValue(temp);
    }

    @Override
    protected boolean hasListeners()
    {
        return listenersHolder != null;
    }

    private void fireListChangeListeners()
    {
        ListListenersHolder.fireListChangeListeners(listenersHolder, changeEvent);
    }

    @Override
    protected void fireChangeListeners(List<T> oldValue, List<T> newValue)
    {
        ListListenersHolder.fireChangeListeners(listenersHolder, this, oldValue, newValue);
    }

    @Override
    protected void fireInvalidationListeners()
    {
        ListListenersHolder.fireInvalidationListeners(listenersHolder, this);
    }

    @Override
    protected void fireChangeArglessListeners()
    {
        ListListenersHolder.fireChangeArglessListeners(listenersHolder, this);
    }

    ///////////////
    // DELEGATES //
    ///////////////

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
    public int indexOf(Object element)
    {
        return value.indexOf(element);
    }

    @Override
    public boolean contains(Object element)
    {
        return value.contains(element);
    }

    @Override
    public T get(int index)
    {
        return value.get(index);
    }

    @Override
    public Object[] toArray()
    {
        return value.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a)
    {
        return value.toArray(a);
    }

    @Override
    public boolean containsAll(Collection<?> c)
    {
        return value.containsAll(c);
    }

    @Override
    public int lastIndexOf(Object o)
    {
        return value.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator()
    {
        return value.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index)
    {
        return value.listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex)
    {
        return value.subList(fromIndex, toIndex);
    }
}