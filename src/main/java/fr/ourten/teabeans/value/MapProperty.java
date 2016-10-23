package fr.ourten.teabeans.value;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import fr.ourten.teabeans.listener.ListValueChangeListener;

public interface MapProperty<K, T> extends IProperty<Map<K, T>>
{
    void addListener(ListValueChangeListener<? super T> listener);

    void removeListener(ListValueChangeListener<? super T> listener);

    T put(K key, T value);

    void putAll(Map<K, ? extends T> elements);

    T get(K key);

    default T getOrDefault(K key, T defaultValue)
    {
        T t;
        return (((t = get(key)) != null) || containsKey(key)) ? t : defaultValue;
    }

    Set<Map.Entry<K, T>> entrySet();

    Set<K> keySet();

    Collection<T> values();

    boolean containsKey(K key);

    boolean containsValue(T value);

    T remove(K key);

    T replace(K key, T element);

    void clear();

    int size();

    default boolean isEmpty()
    {
        return this.size() == 0;
    }
}
