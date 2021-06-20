package fr.ourten.teabeans.property;

import fr.ourten.teabeans.listener.ListValueChangeListener;

import java.text.Collator;
import java.util.Comparator;
import java.util.List;

public interface IListProperty<T> extends IProperty<List<T>>, List<T>
{
    void addChangeListener(ListValueChangeListener<? super T> listener);

    void removeChangeListener(ListValueChangeListener<? super T> listener);

    @Override
    default boolean remove(Object element)
    {
        if (indexOf(element) != -1)
        {
            remove(indexOf(element));
            return true;
        }
        return false;
    }

    void replace(T oldElement, T newElement);

    @Override
    default boolean isEmpty()
    {
        return size() == 0;
    }

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