package fr.ourten.teabeans.property;

import fr.ourten.teabeans.listener.MapValueChangeListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public class MapProperty<K, V> extends Property<Map<K, V>> implements IMapProperty<K, V>
{
    private Supplier<Map<K, V>> mapSupplier;

    private Map<K, V> immutableView;

    /**
     * The list of attached listeners that need to be notified when the value
     * change.
     */
    private final ArrayList<MapValueChangeListener<K, ? super V>> mapValueChangeListeners;

    public MapProperty(Supplier<Map<K, V>> mapSupplier, Map<K, V> value)
    {
        super(value);
        mapValueChangeListeners = new ArrayList<>();

        this.value = mapSupplier.get();
        if (value != null)
            this.value.putAll(value);
        this.mapSupplier = mapSupplier;
    }

    public MapProperty(Map<K, V> value)
    {
        this(HashMap::new, value);
    }

    @Override
    protected void setPropertyValue(Map<K, V> value)
    {
        if (immutableView != null && !Objects.equals(value, this.value))
            immutableView = Collections.unmodifiableMap(value);

        super.setPropertyValue(value);
    }

    @Override
    public Map<K, V> getValue()
    {
        if (immutableView == null)
            immutableView = Collections.unmodifiableMap(value);
        return immutableView;
    }

    public Map<K, V> getModifiableValue()
    {
        return value;
    }

    @Override
    public void addListener(MapValueChangeListener<K, ? super V> listener)
    {
        if (!mapValueChangeListeners.contains(listener))
            mapValueChangeListeners.add(listener);
    }

    @Override
    public void removeListener(MapValueChangeListener<K, ? super V> listener)
    {
        mapValueChangeListeners.remove(listener);
    }

    private void fireListChangeListeners(K key, V oldValue, V newValue)
    {
        for (MapValueChangeListener<K, ? super V> listener : mapValueChangeListeners)
            listener.valueChanged(this, key, oldValue, newValue);
    }

    @Override
    public V get(K key)
    {
        return value.get(key);
    }

    @Override
    public V put(K key, V value)
    {
        Map<K, V> oldMap = null;
        if (!valueChangeListeners.isEmpty())
        {
            oldMap = mapSupplier.get();
            oldMap.putAll(this.value);
        }

        V previousValue = this.value.put(key, value);

        invalidateElement(key, previousValue, value, oldMap);
        return null;
    }

    @Override
    public void putAll(Map<K, ? extends V> elements)
    {
        elements.forEach(this::put);
    }

    @Override
    public Set<Entry<K, V>> entrySet()
    {
        return value.entrySet();
    }

    @Override
    public Set<K> keySet()
    {
        return value.keySet();
    }

    @Override
    public Collection<V> values()
    {
        return value.values();
    }

    @Override
    public boolean containsKey(K key)
    {
        return value.containsKey(key);
    }

    @Override
    public boolean containsValue(V value)
    {
        return this.value.containsValue(value);
    }

    @Override
    public V remove(K key)
    {
        V oldValue = value.get(key);
        Map<K, V> oldMap = null;

        if (!valueChangeListeners.isEmpty())
        {
            oldMap = mapSupplier.get();
            oldMap.putAll(value);
        }
        V rtn = value.remove(key);

        invalidateElement(key, oldValue, null, oldMap);
        return rtn;
    }

    @Override
    public V replace(K key, V element)
    {
        V oldValue = value.get(key);
        Map<K, V> oldMap = null;
        if (!valueChangeListeners.isEmpty())
        {
            oldMap = mapSupplier.get();
            oldMap.putAll(value);
        }

        V rtn = value.replace(key, element);

        if (rtn == null)
            invalidate();
        else
            invalidateElement(key, oldValue, element, oldMap);
        
        return rtn;
    }

    public void invalidateElement(K key, V oldElement, V newElement, Map<K, V> oldMap)
    {
        if (isMuted())
            return;

        fireListChangeListeners(key, oldElement, newElement);
        invalidate(oldMap);
    }

    protected void invalidate(Map<K, V> oldMap)
    {
        oldValue = oldMap;
        invalidate();
    }

    @Override
    public void clear()
    {
        Map<K, V> oldMap = null;

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