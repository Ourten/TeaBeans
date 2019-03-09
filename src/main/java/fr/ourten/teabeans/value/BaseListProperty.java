package fr.ourten.teabeans.value;

import fr.ourten.teabeans.listener.ListValueChangeListener;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class BaseListProperty<T> extends BaseProperty<List<T>> implements ListProperty<T>
{
    private Supplier<List<T>>   listSupplier;
    private BiFunction<T, T, T> checker;

    /**
     * The list of attached listeners that need to be notified when the value
     * change.
     */
    private final ArrayList<ListValueChangeListener<? super T>> listValueChangeListeners;

    public BaseListProperty(final Supplier<List<T>> listSupplier, final List<T> value, final String name)
    {
        super(value, name);
        this.listValueChangeListeners = new ArrayList<>();

        this.value = listSupplier.get();
        if (value != null)
            this.value.addAll(value);
        this.listSupplier = listSupplier;
    }

    public BaseListProperty(final List<T> value, final String name)
    {
        this(ArrayList::new, value, name);
    }

    public BaseListProperty(final Supplier<List<T>> listSupplier, final List<T> value)
    {
        this(listSupplier, value, "");
    }

    public BaseListProperty(final List<T> value)
    {
        this(value, "");
    }

    @Override
    public List<T> getValue()
    {
        return Collections.unmodifiableList(this.value);
    }

    public List<T> getModifiableValue()
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
    public void removeListener(final ListValueChangeListener<? super T> listener)
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
    public T get(final int index)
    {
        return this.value.get(index);
    }

    private void add(T element, final Consumer<T> action)
    {
        List<T> oldList = null;
        if (!this.valueChangeListeners.isEmpty())
        {
            oldList = this.listSupplier.get();
            oldList.addAll(this.value);
        }

        if (this.checker != null)
            element = this.checker.apply(null, element);

        action.accept(element);

        this.invalidateElement(null, element, oldList);
    }

    @Override
    public void add(final T element)
    {
        this.add(element, this.value::add);
    }

    @Override
    public void add(final int index, final T element)
    {
        this.add(element, e -> this.value.add(index, e));
    }

    @Override
    public void addAll(final Collection<T> elements)
    {
        elements.forEach(this::add);
    }

    @Override
    public T remove(final int index)
    {
        List<T> oldList = null;
        if (!this.valueChangeListeners.isEmpty())
        {
            oldList = this.listSupplier.get();
            oldList.addAll(this.value);
        }
        final T rtn = this.value.remove(index);

        this.invalidateElement(rtn, null, oldList);
        return rtn;
    }

    @Override
    public boolean contains(final T element)
    {
        return this.value.contains(element);
    }

    @Override
    public void set(int index, T element)
    {
        final T oldValue = this.value.get(index);
        List<T> oldList = null;
        if (!this.valueChangeListeners.isEmpty())
        {
            oldList = this.listSupplier.get();
            oldList.addAll(this.value);
        }

        if (this.checker != null)
            element = this.checker.apply(this.value.get(index), element);

        this.value.set(index, element);

        this.invalidateElement(oldValue, element, oldList);
    }

    @Override
    public void replace(T oldElement, T newElement)
    {
        final T oldValue = this.value.contains(oldElement) ? oldElement : null;
        List<T> oldList = null;
        if (!this.valueChangeListeners.isEmpty())
        {
            oldList = this.listSupplier.get();
            oldList.addAll(this.value);
        }

        if (this.checker != null)
            newElement = this.checker.apply(oldValue, newElement);

        this.value.remove(oldElement);
        this.value.add(newElement);

        this.invalidateElement(oldElement, newElement, oldList);
    }

    public void invalidateElement(T oldElement, T newElement, List<T> oldList)
    {
        if (this.isMuted())
            return;

        this.fireListChangeListeners(oldElement, newElement);
        this.invalidate(oldList);
    }

    @Override
    public int indexOf(final T element)
    {
        return this.value.indexOf(element);
    }

    @Override
    public void clear()
    {
        List<T> oldList = null;

        if (!this.valueChangeListeners.isEmpty() || !this.listValueChangeListeners.isEmpty())
        {
            oldList = this.listSupplier.get();
            oldList.addAll(this.value);
        }
        this.value.clear();

        if (this.isMuted())
            return;

        if (oldList != null)
            oldList.forEach(oldValue -> this.fireListChangeListeners(oldValue, null));
        this.invalidate(oldList);
    }

    @Override
    public void sort(final Comparator<? super T> comparator)
    {
        final List<T> temp = this.listSupplier.get();
        temp.addAll(this.value);
        temp.sort(comparator);

        this.setValue(temp);
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