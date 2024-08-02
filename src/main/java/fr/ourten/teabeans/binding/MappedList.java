package fr.ourten.teabeans.binding;

import fr.ourten.teabeans.listener.IListChange;
import fr.ourten.teabeans.listener.ListChange;
import fr.ourten.teabeans.listener.ListValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.listener.holder.ListListenersHolder;
import fr.ourten.teabeans.listener.holder.ListenersHolder;
import fr.ourten.teabeans.value.ListObservableValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Function;

public class MappedList<T, P> implements ListObservableValue<T>
{
    protected ListListenersHolder<T> listenersHolder;

    private boolean isMuted;
    private boolean isValid;

    private final ListObservableValue<P> source;
    private final Function<P, T>         mappingFunction;

    private final List<T> mappedList = new ArrayList<>();

    private final NullValueMapping nullValueMapping;

    private final ListValueChangeListener<P> changeListener = this::onSourceChange;

    private final ListChange<T> mappedChangeEvent = new ListChange<>(this);

    public MappedList(ListObservableValue<P> source, Function<P, T> mappingFunction, NullValueMapping nullValueMapping)
    {
        this.source = source;
        this.mappingFunction = mappingFunction;
        this.nullValueMapping = nullValueMapping;

        source.addListChangeListener(changeListener);
        mappedList.addAll(source.stream().map(mappingFunction).toList());
    }

    public MappedList(ListObservableValue<P> source, Function<P, T> mappingFunction)
    {
        this(source, mappingFunction, NullValueMapping.IGNORE);
    }

    private void onSourceChange(IListChange<? extends P> change)
    {
        var mappedOldValue = nullValueMapping == NullValueMapping.IGNORE && change.oldValue() == null ? null : mappingFunction.apply(change.oldValue());
        var mappedNewValue = nullValueMapping == NullValueMapping.IGNORE && change.newValue() == null ? null : mappingFunction.apply(change.newValue());

        if (change.wasAddition())
        {
            if (change.changeIndex() == mappedList.size())
                this.mappedList.add(mappedNewValue);
            else
                this.mappedList.add(change.changeIndex(), mappedNewValue);

            mappedChangeEvent.forAddition(change.changeIndex(), mappedNewValue);
        }
        else if (change.wasRemoval())
        {
            this.mappedList.remove(change.changeIndex());
            mappedChangeEvent.forRemoval(change.changeIndex(), mappedOldValue);
        }
        else if (change.wasReplace())
        {
            this.mappedList.set(change.changeIndex(), mappedNewValue);
            mappedChangeEvent.forReplace(change.changeIndex(), mappedOldValue, mappedNewValue);
        }

        this.invalidate();
        this.fireListChangeListeners();
    }

    private void invalidate()
    {
        this.isValid = false;

        if (!isMuted())
        {
            this.fireInvalidationListeners();
            this.isValid = true;
        }
    }

    @Override
    public void addListener(ValueInvalidationListener listener)
    {
        listenersHolder = ListListenersHolder.addListener(listenersHolder, listener);
    }

    @Override
    public void removeListener(ValueInvalidationListener listener)
    {
        listenersHolder = ListListenersHolder.removeListener(listenersHolder, listener);
    }

    @Override
    public void addChangeListener(ValueInvalidationListener listener)
    {
        listenersHolder = ListListenersHolder.addChangeListener(listenersHolder, listener);
    }

    @Override
    public void removeChangeListener(ValueInvalidationListener listener)
    {
        listenersHolder = ListListenersHolder.removeChangeListener(listenersHolder, listener);
    }

    @Override
    public void addListChangeListener(ListValueChangeListener<? super T> listener)
    {
        listenersHolder = ListListenersHolder.addListChangeListener(listenersHolder, listener);
    }

    @Override
    public void removeListChangeListener(ListValueChangeListener<? super T> listener)
    {
        listenersHolder = ListListenersHolder.removeListChangeListener(listenersHolder, listener);
    }

    protected void fireInvalidationListeners()
    {
        ListenersHolder.fireInvalidationListeners(listenersHolder, this);
    }

    protected void fireChangeArglessListeners()
    {
        ListenersHolder.fireChangeArglessListeners(listenersHolder, this);
    }

    protected void fireListChangeListeners()
    {
        ListListenersHolder.fireListChangeListeners(listenersHolder, mappedChangeEvent);
    }

    @Override
    public void mute()
    {
        isMuted = true;
    }

    @Override
    public void unmute()
    {
        isMuted = false;

        if (!isValid)
            invalidate();
    }

    @Override
    public boolean isMuted()
    {
        return isMuted;
    }

    public ListObservableValue<P> source()
    {
        return source;
    }

    @Override
    public int size()
    {
        return mappedList.size();
    }

    @Override
    public boolean contains(Object o)
    {
        return mappedList.contains(o);
    }

    @Override
    public Iterator<T> iterator()
    {
        return mappedList.iterator();
    }

    @Override
    public Object[] toArray()
    {
        return mappedList.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a)
    {
        return mappedList.toArray(a);
    }

    @Override
    public boolean add(T t)
    {
        throw new UnsupportedOperationException("MappedList is considered immutable. Change the source list to achieve mutability");
    }

    @Override
    public boolean containsAll(Collection<?> c)
    {
        return mappedList.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c)
    {
        throw new UnsupportedOperationException("MappedList is considered immutable. Change the source list to achieve mutability");
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c)
    {
        throw new UnsupportedOperationException("MappedList is considered immutable. Change the source list to achieve mutability");
    }

    @Override
    public boolean removeAll(Collection<?> c)
    {
        throw new UnsupportedOperationException("MappedList is considered immutable. Change the source list to achieve mutability");
    }

    @Override
    public boolean retainAll(Collection<?> c)
    {
        throw new UnsupportedOperationException("MappedList is considered immutable. Change the source list to achieve mutability");
    }

    @Override
    public void clear()
    {
        throw new UnsupportedOperationException("MappedList is considered immutable. Change the source list to achieve mutability");
    }

    @Override
    public T get(int index)
    {
        return mappedList.get(index);
    }

    @Override
    public T set(int index, T element)
    {
        throw new UnsupportedOperationException("MappedList is considered immutable. Change the source list to achieve mutability");
    }

    @Override
    public void add(int index, T element)
    {
        throw new UnsupportedOperationException("MappedList is considered immutable. Change the source list to achieve mutability");
    }

    @Override
    public T remove(int index)
    {
        throw new UnsupportedOperationException("MappedList is considered immutable. Change the source list to achieve mutability");
    }

    @Override
    public int indexOf(Object o)
    {
        return mappedList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o)
    {
        return mappedList.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator()
    {
        return mappedList.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index)
    {
        return mappedList.listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex)
    {
        return mappedList.subList(fromIndex, toIndex);
    }

    public enum NullValueMapping
    {
        IGNORE,
        MAP
    }
}
