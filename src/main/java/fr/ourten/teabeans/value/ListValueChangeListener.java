package fr.ourten.teabeans.value;

public interface ListValueChangeListener<T>
{
    /**
     * Fired when an element of an observed list change. It will be fired at
     * element deletion, element adding and element change.
     * 
     * @param observable
     *            is the observed list.
     * @param oldValue
     *            is the previous value if the event is fired from a change or a
     *            remove, else it will be null.
     * @param newValue
     *            is the new value added or changed. If the event is fired from
     *            a remove it will be null.
     */
    void valueChanged(ObservableValue<?> observable, T oldValue, T newValue);
}