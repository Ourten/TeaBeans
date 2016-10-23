package fr.ourten.teabeans.value;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import fr.ourten.teabeans.listener.ListValueChangeListener;

public interface MapProperty<T> extends IProperty<Map<String, T>>
{
    void addListener(ListValueChangeListener<? super T> listener);

    void removeListener(ListValueChangeListener<? super T> listener);

    T put(String key, T value);

    void putAll(Map<String, ? extends T> elements);

    T get(String key);

    default T getOrDefault(String key, T defaultValue)
    {
        T t;
        return (((t = get(key)) != null) || containsKey(key)) ? t : defaultValue;
    }

    Set<Map.Entry<String, T>> entrySet();

    Set<String> keySet();

    Collection<T> values();

    boolean containsKey(String key);

    boolean containsValue(T value);

    T remove(String key);

    T replace(String key, T element);

    void clear();

    int size();

    default boolean isEmpty()
    {
        return this.size() == 0;
    }
}
