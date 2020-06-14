package fr.ourten.teabeans.value;

import fr.ourten.teabeans.listener.MapValueChangeListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class MapProperty<K, T> extends Property<Map<K, T>> implements IMapProperty<K, T>
{
    private Supplier<Map<K, T>> mapSupplier;
    private BiFunction<T, T, T> checker;

    private Map<K, T> immutableView;

    /**
     * The list of attached listeners that need to be notified when the value
     * change.
     */
    private final ArrayList<MapValueChangeListener<K, ? super T>> mapValueChangeListeners;

    public MapProperty(Supplier<Map<K, T>> mapSupplier, Map<K, T> value)
    {
        super(value);
        mapValueChangeListeners = new ArrayList<>();

        this.value = mapSupplier.get();
        if (value != null)
            this.value.putAll(value);
        this.mapSupplier = mapSupplier;
    }

    public MapProperty(Map<K, T> value)
    {
        this(HashMap::new, value);
    }

    @Override
    protected void setPropertyValue(Map<K, T> value)
    {
        if (immutableView != null && !Objects.equals(value, this.value))
            immutableView = Collections.unmodifiableMap(value);

        super.setPropertyValue(value);
    }

    @Override
    public Map<K, T> getValue()
    {
        if (immutableView == null)
            immutableView = Collections.unmodifiableMap(value);
        return immutableView;
    }

    public Map<K, T> getModifiableValue()
    {
        return value;
    }

    @Override
    public void addListener(MapValueChangeListener<K, ? super T> listener)
    {
        if (!mapValueChangeListeners.contains(listener))
            mapValueChangeListeners.add(listener);
    }

    @Override
    public void removeListener(MapValueChangeListener<K, ? super T> listener)
    {
        mapValueChangeListeners.remove(listener);
    }

    private void fireListChangeListeners(K key, T oldValue, T newValue)
    {
        for (MapValueChangeListener<K, ? super T> listener : mapValueChangeListeners)
            listener.valueChanged(this, key, oldValue, newValue);
    }

    public BiFunction<T, T, T> getElementChecker()
    {
        return checker;
    }

    public void setElementChecker(BiFunction<T, T, T> checker)
    {
        this.checker = checker;
    }

    @Override
    public T get(K key)
    {
        return value.get(key);
    }

    @Override
    public T put(K key, T value)
    {
        Map<K, T> oldMap = null;
        if (!valueChangeListeners.isEmpty())
        {
            oldMap = mapSupplier.get();
            oldMap.putAll(this.value);
        }

        if (checker != null)
            value = checker.apply(null, value);

        this.value.put(key, value);

        invalidateElement(key, null, value, oldMap);
        return null;
    }

    @Override
    public void putAll(Map<K, ? extends T> elements)
    {
        elements.forEach(this::put);
    }

    @Override
    public Set<Entry<K, T>> entrySet()
    {
        return value.entrySet();
    }

    @Override
    public Set<K> keySet()
    {
        return value.keySet();
    }

    @Override
    public Collection<T> values()
    {
        return value.values();
    }

    @Override
    public boolean containsKey(K key)
    {
        return value.containsKey(key);
    }

    @Override
    public boolean containsValue(T value)
    {
        return this.value.containsValue(value);
    }

    @Override
    public T remove(K key)
    {
        T oldValue = value.get(key);
        Map<K, T> oldMap = null;

        if (!valueChangeListeners.isEmpty())
        {
            oldMap = mapSupplier.get();
            oldMap.putAll(value);
        }
        T rtn = value.remove(key);

        invalidateElement(key, oldValue, null, oldMap);
        return rtn;
    }

    @Override
    public T replace(K key, T element)
    {
        T oldValue = value.get(key);
        Map<K, T> oldMap = null;
        if (!valueChangeListeners.isEmpty())
        {
            oldMap = mapSupplier.get();
            oldMap.putAll(value);
        }

        if (checker != null)
            element = checker.apply(value.get(key), element);

        T rtn = value.replace(key, element);

        invalidateElement(key, oldValue, element, oldMap);
        return rtn;
    }

    public void invalidateElement(K key, T oldElement, T newElement, Map<K, T> oldMap)
    {
        if (isMuted())
            return;

        fireListChangeListeners(key, oldElement, newElement);
        invalidate(oldMap);
    }

    protected void invalidate(Map<K, T> oldMap)
    {
        oldValue = oldMap;
        invalidate();
    }

    @Override
    public void clear()
    {
        Map<K, T> oldMap = null;

        if (!valueChangeListeners.isEmpty() || !mapValueChangeListeners.isEmpty())
        {
            oldMap = mapSupplier.get();
            oldMap.putAll(value);
        }
        value.clear();

        if (isMuted())
            return;

        if (oldMap != null)
            oldMap.forEach((key, oldValue) -> fireListChangeListeners(key, oldValue, null));
        invalidate(oldMap);
    }

    @Override
    public int size()
    {
        return value.size();
    }
}