package fr.ourten.teabeans.property;

import fr.ourten.teabeans.listener.MapValueChangeListener;
import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.listener.holder.ListenersHolder;
import fr.ourten.teabeans.listener.holder.MapListenersHolder;

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
    private final Supplier<Map<K, V>> mapSupplier;

    private Map<K, V> immutableView;

    private MapListenersHolder<K, V> listenersHolder;

    public MapProperty(Supplier<Map<K, V>> mapSupplier, Map<K, V> value)
    {
        super(value);

        this.value = mapSupplier.get();
        if (value != null)
            this.value.putAll(value);
        this.mapSupplier = mapSupplier;
    }

    public MapProperty(Map<K, V> value)
    {
        this(HashMap::new, value);
    }

    public MapProperty()
    {
        this(null);
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
    public void addChangeListener(MapValueChangeListener<K, ? super V> listener)
    {
        startObserving();
        listenersHolder = MapListenersHolder.addMapChangeListener(listenersHolder, listener);
    }

    @Override
    public void removeChangeListener(MapValueChangeListener<K, ? super V> listener)
    {
        listenersHolder = MapListenersHolder.removeMapChangeListener(listenersHolder, listener);
        stopObserving();
    }

    @Override
    public void addChangeListener(ValueChangeListener<? super Map<K, V>> listener)
    {
        startObserving();
        listenersHolder = MapListenersHolder.addChangeListener(listenersHolder, listener);
    }

    @Override
    public void removeChangeListener(ValueChangeListener<? super Map<K, V>> listener)
    {
        listenersHolder = MapListenersHolder.removeChangeListener(listenersHolder, listener);
        stopObserving();
    }

    @Override
    public void addListener(ValueInvalidationListener listener)
    {
        startObserving();
        listenersHolder = MapListenersHolder.addListener(listenersHolder, listener);
    }

    @Override
    public void removeListener(ValueInvalidationListener listener)
    {
        listenersHolder = MapListenersHolder.removeListener(listenersHolder, listener);
        stopObserving();
    }

    @Override
    public void addChangeListener(ValueInvalidationListener listener)
    {
        startObserving();
        listenersHolder = MapListenersHolder.addChangeListener(listenersHolder, listener);
    }

    @Override
    public void removeChangeListener(ValueInvalidationListener listener)
    {
        listenersHolder = MapListenersHolder.removeListener(listenersHolder, listener);
        stopObserving();
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
        if (ListenersHolder.hasChangeListeners(listenersHolder))
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

        if (ListenersHolder.hasChangeListeners(listenersHolder))
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
        if (ListenersHolder.hasChangeListeners(listenersHolder))
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

        fireMapChangeListeners(key, oldElement, newElement);
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

        if (ListenersHolder.hasChangeListeners(listenersHolder) || MapListenersHolder.hasMapChangeListeners(listenersHolder))
        {
            oldMap = mapSupplier.get();
            oldMap.putAll(value);
        }
        value.clear();

        if (isMuted())
            return;

        if (oldMap != null)
            oldMap.forEach((key, oldValue) -> fireMapChangeListeners(key, oldValue, null));
        invalidate(oldMap);
    }

    @Override
    public int size()
    {
        return value.size();
    }

    @Override
    protected boolean hasListeners()
    {
        return listenersHolder != null;
    }

    private void fireMapChangeListeners(K key, V oldValue, V newValue)
    {
        MapListenersHolder.fireMapChangeListeners(listenersHolder, this, key, oldValue, newValue);
    }

    @Override
    protected void fireChangeListeners(Map<K, V> oldValue, Map<K, V> newValue)
    {
        MapListenersHolder.fireChangeListeners(listenersHolder, this, oldValue, newValue);
    }

    @Override
    protected void fireInvalidationListeners()
    {
        MapListenersHolder.fireInvalidationListeners(listenersHolder, this);
    }

    @Override
    protected void fireChangeArglessListeners()
    {
        MapListenersHolder.fireChangeArglessListeners(listenersHolder, this);
    }
}