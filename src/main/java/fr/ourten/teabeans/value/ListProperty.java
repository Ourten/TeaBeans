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

public class ListProperty<T> extends BaseProperty<List<T>> implements IListProperty<T>
{
    private BiFunction<T, T, T>                                 checker;

    /**
     * The list of attached listeners that need to be notified when the value
     * change.
     */
    private final ArrayList<ListValueChangeListener<? super T>> listValueChangeListeners;

    protected ListProperty(final List<T> value, final String name)
    {
        super(value, name);
        this.listValueChangeListeners = Lists.newArrayList();

        this.value = value;
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
        List<T> old = null;
        if (!this.valueChangeListeners.isEmpty())
            old = this.getValue();

        if (this.checker != null)
            element = this.checker.apply(null, element);

        action.accept(element);

        this.fireInvalidationListeners();
        this.fireListChangeListeners(null, element);
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
        List<T> old = null;
        if (!this.valueChangeListeners.isEmpty())
            old = this.getValue();
        final T rtn = this.value.remove(index);

        this.fireInvalidationListeners();
        this.fireListChangeListeners(rtn, null);
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
        final T oldValue = this.value.get(index);
        List<T> old = null;
        if (!this.valueChangeListeners.isEmpty())
            old = this.getValue();

        if (this.checker != null)
            element = this.checker.apply(this.value.get(index), element);

        this.value.set(index, element);

        this.fireInvalidationListeners();
        this.fireListChangeListeners(oldValue, element);
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
        List<T> old = null;

        if (!this.valueChangeListeners.isEmpty())
            old = this.getValue();
        this.value.clear();

        this.fireInvalidationListeners();
        this.fireChangeListeners(old, this.value);
    }

    @Override
    public void sort(final Comparator<? super T> comparator)
    {
        final ArrayList<T> temp = Lists.newArrayList(this.value);
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