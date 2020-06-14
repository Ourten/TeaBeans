package fr.ourten.teabeans.value;

import fr.ourten.teabeans.listener.ListValueChangeListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ListProperty<T> extends Property<List<T>> implements IListProperty<T>
{
    private Supplier<List<T>>   listSupplier;
    private BiFunction<T, T, T> checker;

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
    public void addListener(ListValueChangeListener<? super T> listener)
    {
        if (!listValueChangeListeners.contains(listener))
            listValueChangeListeners.add(listener);
    }

    @Override
    public void removeListener(ListValueChangeListener<? super T> listener)
    {
        listValueChangeListeners.remove(listener);
    }

    public BiFunction<T, T, T> getElementChecker()
    {
        return checker;
    }

    public void setElementChecker(BiFunction<T, T, T> checker)
    {
        this.checker = checker;
    }

    @Override
    public T get(int index)
    {
        return value.get(index);
    }

    private void add(T element, Consumer<T> action)
    {
        List<T> oldList = null;
        if (!valueChangeListeners.isEmpty())
        {
            oldList = listSupplier.get();
            oldList.addAll(value);
        }

        if (checker != null)
            element = checker.apply(null, element);

        action.accept(element);

        invalidateElement(null, element, oldList);
    }

    @Override
    public void add(T element)
    {
        add(element, value::add);
    }

    @Override
    public void add(int index, T element)
    {
        add(element, e -> value.add(index, e));
    }

    @Override
    public void addAll(Collection<T> elements)
    {
        elements.forEach(this::add);
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
    public boolean contains(T element)
    {
        return value.contains(element);
    }

    @Override
    public void set(int index, T element)
    {
        T oldValue = value.get(index);
        List<T> oldList = null;
        if (!valueChangeListeners.isEmpty())
        {
            oldList = listSupplier.get();
            oldList.addAll(value);
        }

        if (checker != null)
            element = checker.apply(value.get(index), element);

        value.set(index, element);

        invalidateElement(oldValue, element, oldList);
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

        if (checker != null)
            newElement = checker.apply(oldValue, newElement);

        value.remove(oldElement);
        value.add(newElement);

        invalidateElement(oldElement, newElement, oldList);
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
    public int indexOf(T element)
    {
        return value.indexOf(element);
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

    private void fireListChangeListeners(T oldValue, T newValue)
    {
        for (ListValueChangeListener<? super T> listener : listValueChangeListeners)
            listener.valueChanged(this, oldValue, newValue);
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
}