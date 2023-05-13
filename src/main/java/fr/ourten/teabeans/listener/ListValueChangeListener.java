package fr.ourten.teabeans.listener;

@FunctionalInterface
public interface ListValueChangeListener<T>
{
    /**
     * Fired when an element of an observed list change. It will be fired at
     * element deletion, element adding and element change.
     *
     * @param change
     */
    void valueChanged(IListChange<? extends T> change);
}