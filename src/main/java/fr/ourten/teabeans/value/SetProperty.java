package fr.ourten.teabeans.value;

import fr.ourten.teabeans.listener.ListValueChangeListener;

import java.util.Collection;
import java.util.Set;

public interface SetProperty<T> extends IProperty<Set<T>>
{
    void addListener(ListValueChangeListener<? super T> listener);

    void removeListener(ListValueChangeListener<?> listener);

    void add(T element);

    void addAll(Collection<T> elements);

    boolean remove( T element);

    void replace(T oldElement, T newElement);

    boolean contains(T element);

    void clear();

    int size();

    default boolean isEmpty()
    {
        return this.size() == 0;
    }
}
