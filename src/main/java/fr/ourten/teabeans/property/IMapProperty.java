package fr.ourten.teabeans.property;

import fr.ourten.teabeans.listener.MapValueChangeListener;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface IMapProperty<K, T> extends IProperty<Map<K, T>>
{
    void addChangeListener(MapValueChangeListener<K, ? super T> listener);

    void removeChangeListener(MapValueChangeListener<K, ? super T> listener);

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

    default boolean removeValue(T value)
    {
        K[] keys = (K[]) keySet().toArray();
        boolean find = false;
        for (int i = 0; i < keys.length; i++)
        {
            if (get(keys[i]).equals(value))
            {
                remove(keys[i]);
                find = true;
            }
        }
        return find;
    }

    T replace(K key, T element);

    void clear();

    int size();

    default boolean isEmpty()
    {
        return size() == 0;
    }
}
