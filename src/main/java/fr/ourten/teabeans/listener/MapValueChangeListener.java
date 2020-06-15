package fr.ourten.teabeans.listener;

import fr.ourten.teabeans.value.ObservableValue;

@FunctionalInterface
public interface MapValueChangeListener<K, V>
{
    /**
     * Fired when an element of an observed map change. It will be fired at
     * element deletion, element adding and element change.
     *
     * @param observable is the observed map.
     * @param key        is the key link to the value.
     * @param oldValue   is the previous value if the event is fired from a change or a
     *                   remove, else it will be null.
     * @param newValue   is the new value added or changed. If the event is fired from
     *                   a remove it will be null.
     */
    void valueChanged(ObservableValue<?> observable, K key, V oldValue, V newValue);
}
