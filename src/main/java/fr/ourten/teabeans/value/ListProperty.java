package fr.ourten.teabeans.value;

import java.text.Collator;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import fr.ourten.teabeans.listener.ListValueChangeListener;

public interface ListProperty<T> extends IProperty<List<T>>
{
    void addListener(ListValueChangeListener<? super T> listener);

    void removeListener(ListValueChangeListener<? super T> listener);

    T get(int index);

    void add(int index, T element);

    void add(T element);

    void addAll(Collection<T> elements);

    public default boolean remove(final T element)
    {
        if (this.indexOf(element) != -1)
        {
            this.remove(this.indexOf(element));
            return true;
        }
        return false;
    }

    T remove(int index);

    void set(int index, T element);

    boolean contains(T element);

    int indexOf(T element);

    void clear();

    boolean isEmpty();

    void sort(Comparator<? super T> comparator);

    /**
     * Will sort the contained list according to natural order
     */
    public default void sort()
    {
        @SuppressWarnings("unchecked")
        final Comparator<T> naturalOrder = (o1, o2) ->
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
        this.sort(naturalOrder);
    }
}