package fr.ourten.teabeans.property;

import fr.ourten.teabeans.listener.SetValueChangeListener;

import java.util.Collection;
import java.util.Set;

public interface ISetProperty<T> extends IProperty<Set<T>>, Iterable<T>
{
    void addSetChangeListener(SetValueChangeListener<? super T> listener);

    void removeSetChangeListener(SetValueChangeListener<? super T> listener);

    void add(T element);

    void addAll(Collection<T> elements);

    boolean remove(T element);

    void replace(T oldElement, T newElement);

    boolean contains(T element);

    void clear();

    int size();

    default boolean isEmpty()
    {
        return size() == 0;
    }
}
