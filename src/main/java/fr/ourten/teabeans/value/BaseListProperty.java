package fr.ourten.teabeans.value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import fr.ourten.teabeans.listener.ListValueChangeListener;

public class BaseListProperty<T> extends BaseProperty<List<T>> implements ListProperty<T>
{
    private BiFunction<T, T, T>                                 checker;

    /**
     * The list of attached listeners that need to be notified when the value
     * change.
     */
    private final ArrayList<ListValueChangeListener<? super T>> listValueChangeListeners;

    public BaseListProperty(final List<T> value, final String name)
    {
        super(value, name);
        this.listValueChangeListeners = Lists.newArrayList();

        this.value = value != null ? Lists.newArrayList(value) : Lists.newArrayList();
    }

    public BaseListProperty(final List<T> value)
    {
        this(value, "");
    }

    @Override
    public ImmutableList<T> getValue()
    {
        return ImmutableList.copyOf(this.value);
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
        this.fireInvalidationListeners();
        this.fireListChangeListeners(null, element);

        List<T> old = null;
        if (!this.valueChangeListeners.isEmpty())
            old = Lists.newArrayList(this.value);

        if (this.checker != null)
            element = this.checker.apply(null, element);

        action.accept(element);

        this.fireChangeListeners(old, this.value);
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
        this.fireInvalidationListeners();
        this.fireListChangeListeners(this.value.get(index), null);

        List<T> old = null;
        if (!this.valueChangeListeners.isEmpty())
            old = Lists.newArrayList(this.value);
        final T rtn = this.value.remove(index);
        this.fireChangeListeners(old, this.value);

        return rtn;
    }

    @Override
    public boolean contains(final T element)
    {
        return this.value.contains(element);
    }

    @Override
    public void set(final int index, T element)
    {
        this.fireInvalidationListeners();
        this.fireListChangeListeners(this.value.get(index), element);

        List<T> old = null;
        if (!this.valueChangeListeners.isEmpty())
            old = Lists.newArrayList(this.value);

        if (this.checker != null)
            element = this.checker.apply(this.value.get(index), element);

        this.value.set(index, element);
        this.fireChangeListeners(old, this.value);
    }

    @Override
    public int indexOf(final T element)
    {
        return this.value.indexOf(element);
    }

    @Override
    public void clear()
    {
        this.fireInvalidationListeners();
        List<T> old = null;

        if (!this.valueChangeListeners.isEmpty())
            old = Lists.newArrayList(this.value);
        this.value.clear();
        this.fireChangeListeners(old, this.value);
    }

    @Override
    public void sort(final Comparator<? super T> comparator)
    {
        final ArrayList<T> temp = Lists.newArrayList(this.value);
        temp.sort(comparator);

        this.fireChangeListeners(this.value, temp);
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