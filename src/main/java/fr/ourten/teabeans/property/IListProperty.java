package fr.ourten.teabeans.property;

import fr.ourten.teabeans.listener.ListValueChangeListener;

import java.text.Collator;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public interface IListProperty<T> extends IProperty<List<T>>, Iterable<T>
{
    void addChangeListener(ListValueChangeListener<? super T> listener);

    void removeChangeListener(ListValueChangeListener<? super T> listener);

    T get(int index);

    void add(int index, T element);

    void add(T element);

    void addAll(Collection<T> elements);

    default boolean remove(T element)
    {
        if (indexOf(element) != -1)
        {
            remove(indexOf(element));
            return true;
        }
        return false;
    }

    T remove(int index);

    void set(int index, T element);

    void replace(T oldElement, T newElement);

    boolean contains(T element);

    int indexOf(T element);

    void clear();

    int size();

    default boolean isEmpty()
    {
        return size() == 0;
    }

    void sort(Comparator<? super T> comparator);

    /**
     * Will sort the contained list according to natural order
     */
    default void sort()
    {
        Comparator<T> naturalOrder = (o1, o2) ->
        {
            if (o1 == null && o2 == null)
                return 0;
            if (o1 == null)
                return -1;
            if (o2 == null)
                return 1;

            if (o1 instanceof Comparable)
                return ((Comparable<T>) o1).compareTo(o2);

            return Collator.getInstance().compare(o1.toString(), o2.toString());
        };
        sort(naturalOrder);
    }
}