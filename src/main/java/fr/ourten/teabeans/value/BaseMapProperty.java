package fr.ourten.teabeans.value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import fr.ourten.teabeans.listener.MapValueChangeListener;

public class BaseMapProperty<K, T> extends BaseProperty<Map<K, T>> implements MapProperty<K, T>
{
    private Supplier<Map<K, T>>                                   mapSupplier;
    private BiFunction<T, T, T>                                   checker;

    /**
     * The list of attached listeners that need to be notified when the value
     * change.
     */
    private final ArrayList<MapValueChangeListener<K, ? super T>> mapValueChangeListeners;

    public BaseMapProperty(final Supplier<Map<K, T>> mapSupplier, final Map<K, T> value, final String name)
    {
        super(value, name);
        this.mapValueChangeListeners = Lists.newArrayList();

        this.value = mapSupplier.get();
        if (value != null)
            this.value.putAll(value);
        this.mapSupplier = mapSupplier;
    }

    public BaseMapProperty(final Map<K, T> value, final String name)
    {
        this(() -> Maps.newHashMap(), value, name);
    }

    public BaseMapProperty(final Supplier<Map<K, T>> mapSupplier, final Map<K, T> value)
    {
        this(mapSupplier, value, "");
    }

    public BaseMapProperty(final Map<K, T> value)
    {
        this(value, "");
    }

    @Override
    public ImmutableMap<K, T> getValue()
    {
        return ImmutableMap.copyOf(this.value);
    }

    public Map<K, T> getModifiableValue()
    {
        return this.value;
    }

    @Override
    public void addListener(final MapValueChangeListener<K, ? super T> listener)
    {
        if (!this.mapValueChangeListeners.contains(listener))
            this.mapValueChangeListeners.add(listener);
    }

    @Override
    public void removeListener(final MapValueChangeListener<K, ? super T> listener)
    {
        this.mapValueChangeListeners.remove(listener);
    }

    private void fireListChangeListeners(final K key, final T oldValue, final T newValue)
    {
        for (final MapValueChangeListener<K, ? super T> listener : this.mapValueChangeListeners)
            listener.valueChanged(this, key, oldValue, newValue);
    }

    public BiFunction<T, T, T> getElementChecker()
    {
        return this.checker;
    }

    public void setElementChecker(final BiFunction<T, T, T> checker)
    {
        this.checker = checker;
    }

    @Override
    public T get(final K key)
    {
        return this.value.get(key);
    }

    @Override
    public T put(final K key, T value)
    {
        Map<K, T> old = null;
        if (!this.valueChangeListeners.isEmpty() || !this.mapValueChangeListeners.isEmpty())
        {
            old = this.mapSupplier.get();
            old.putAll(this.value);
        }

        if (this.checker != null)
            value = this.checker.apply(null, value);

        this.value.put(key, value);

        this.fireInvalidationListeners();
        this.fireListChangeListeners(key, null, value);
        this.fireChangeListeners(old, this.value);
        return null;
    }

    @Override
    public void putAll(final Map<K, ? extends T> elements)
    {
        elements.forEach(this::put);
    }

    @Override
    public Set<Entry<K, T>> entrySet()
    {
        return this.value.entrySet();
    }

    @Override
    public Set<K> keySet()
    {
        return this.value.keySet();
    }

    @Override
    public Collection<T> values()
    {
        return this.value.values();
    }

    @Override
    public boolean containsKey(final K key)
    {
        return this.value.containsKey(key);
    }

    @Override
    public boolean containsValue(final T value)
    {
        return this.value.containsValue(value);
    }

    @Override
    public T remove(final K key)
    {
        final T oldValue = this.value.get(key);
        Map<K, T> old = null;

        if (!this.valueChangeListeners.isEmpty() || !this.mapValueChangeListeners.isEmpty())
        {
            old = this.mapSupplier.get();
            old.putAll(this.value);
        }
        final T rtn = this.value.remove(key);

        this.fireInvalidationListeners();
        this.fireListChangeListeners(key, oldValue, null);
        this.fireChangeListeners(old, this.value);
        return rtn;
    }

    @Override
    public T replace(final K key, T element)
    {
        final T oldValue = this.value.get(key);
        Map<K, T> old = null;
        if (!this.valueChangeListeners.isEmpty() || !this.mapValueChangeListeners.isEmpty())
        {
            old = this.mapSupplier.get();
            old.putAll(this.value);
        }

        if (this.checker != null)
            element = this.checker.apply(this.value.get(key), element);

        final T rtn = this.value.replace(key, element);

        this.fireInvalidationListeners();
        this.fireListChangeListeners(key, oldValue, element);
        this.fireChangeListeners(old, this.value);
        return rtn;
    }

    @Override
    public void clear()
    {
        Map<K, T> old = null;

        if (!this.valueChangeListeners.isEmpty() || !this.mapValueChangeListeners.isEmpty())
        {
            old = this.mapSupplier.get();
            old.putAll(this.value);
        }
        this.value.clear();

        this.fireInvalidationListeners();
        this.fireChangeListeners(old, this.value);
        if (old != null)
            old.forEach((key, oldValue) -> this.fireListChangeListeners(key, oldValue, null));
    }

    @Override
    public int size()
    {
        return this.value.size();
    }
}