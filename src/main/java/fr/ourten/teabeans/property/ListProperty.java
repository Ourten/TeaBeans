package fr.ourten.teabeans.property;

import fr.ourten.teabeans.listener.ListValueChangeListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ListProperty<T> extends Property<List<T>> implements IListProperty<T>
{
    private final Supplier<List<T>> listSupplier;

    private List<T> immutableView;

    /**
     * The list of attached listeners that need to be notified when the value
     * change.
     */
    private final ArrayList<ListValueChangeListener<? super T>> listValueChangeListeners;

    public ListProperty(Supplier<List<T>> listSupplier, List<T> value)
    {
        super(value);
        listValueChangeListeners = new ArrayList<>();

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
    protected void setPropertyValue(List<T> value)
    {
        if (immutableView != null && !Objects.equals(value, this.value))
            immutableView = Collections.unmodifiableList(value);

        super.setPropertyValue(value);
    }

    @Override
    public List<T> getValue()
    {
        if (immutableView == null)
            immutableView = Collections.unmodifiableList(value);
        return immutableView;
    }

    public List<T> getModifiableValue()
    {
        return value;
    }

    @Override
    public void addChangeListener(ListValueChangeListener<? super T> listener)
    {
        if (!isObserving() && hasObservable())
            startObserving();
        if (!listValueChangeListeners.contains(listener))
            listValueChangeListeners.add(listener);
    }

    @Override
    public void removeChangeListener(ListValueChangeListener<? super T> listener)
    {
        listValueChangeListeners.remove(listener);
        if (listValueChangeListeners.isEmpty() && hasObservable())
            stopObserving();
    }

    private boolean add(T element, Function<T, Boolean> action)
    {
        List<T> oldList = null;
        if (!valueChangeListeners.isEmpty())
        {
            oldList = listSupplier.get();
            oldList.addAll(value);
        }

        boolean changed = action.apply(element);

        invalidateElement(null, element, oldList);
        return changed;
    }

    @Override
    public boolean add(T element)
    {
        return add(element, value::add);
    }

    @Override
    public void add(int index, T element)
    {
        add(element, e ->
        {
            value.add(index, e);
            return true;
        });
    }

    @Override
    public boolean addAll(Collection<? extends T> elements)
    {
        AtomicBoolean changed = new AtomicBoolean(false);
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
        AtomicBoolean changed = new AtomicBoolean(false);

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
        AtomicBoolean changed = new AtomicBoolean(false);

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
        if (!valueChangeListeners.isEmpty())
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
                each.remove();
                removed = true;

                if (!isMuted())
                    fireListChangeListeners(potentialRemove, null);
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
        if (!valueChangeListeners.isEmpty())
        {
            oldList = listSupplier.get();
            oldList.addAll(value);
        }
        T rtn = value.remove(index);

        invalidateElement(rtn, null, oldList);
        return rtn;
    }

    @Override
    public T set(int index, T element)
    {
        T oldValue = value.get(index);
        List<T> oldList = null;
        if (!valueChangeListeners.isEmpty())
        {
            oldList = listSupplier.get();
            oldList.addAll(value);
        }

        value.set(index, element);

        invalidateElement(oldValue, element, oldList);
        return oldValue;
    }

    @Override
    public void replace(T oldElement, T newElement)
    {
        T oldValue = value.contains(oldElement) ? oldElement : null;
        List<T> oldList = null;
        if (!valueChangeListeners.isEmpty())
        {
            oldList = listSupplier.get();
            oldList.addAll(value);
        }

        if (oldValue != null)
        {
            int oldElementIndex = value.indexOf(oldValue);
            value.set(oldElementIndex, newElement);
        }
        else
            value.add(newElement);

        value.remove(oldElement);

        invalidateElement(oldValue, newElement, oldList);
    }

    public void invalidateElement(T oldElement, T newElement, List<T> oldList)
    {
        if (isMuted())
            return;

        fireListChangeListeners(oldElement, newElement);
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

        if (!valueChangeListeners.isEmpty() || !listValueChangeListeners.isEmpty())
        {
            oldList = listSupplier.get();
            oldList.addAll(value);
        }
        value.clear();

        if (isMuted())
            return;

        if (oldList != null)
            oldList.forEach(oldValue -> fireListChangeListeners(oldValue, null));
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