package fr.ourten.teabeans.value.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiFunction;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import fr.ourten.teabeans.listener.MapValueChangeListener;
import fr.ourten.teabeans.value.BaseProperty;

/**
 * @author Phenix246
 * 
 *         BaseMapProperty is an object that allow to put different element
 *         together with a specific key value. (@see {@link Map}).
 */
public class BaseMapProperty<K, T> extends BaseProperty<Map<K, T>> implements IMapProperty<K, T>
{
    /**
     * The checker use to verify the value of a element of the property
     */
    private BiFunction<T, T, T>                                   checker;

    /**
     * The list of attached listeners that need to be notified when the value
     * change.
     */
    private final ArrayList<MapValueChangeListener<K, ? super T>> mapValueChangeListeners;

    public BaseMapProperty(final Map<K, T> value, final String name)
    {
        super(value, name);
        this.mapValueChangeListeners = Lists.newArrayList();

        this.value = value != null ? Maps.newHashMap(value) : Maps.newHashMap();
    }

    public BaseMapProperty(final Map<K, T> value)
    {
        this(value, "");
    }

    /**
     * @return a copy of the values of the property
     */
    @Override
    public ImmutableMap<K, T> getValue()
    {
        return ImmutableMap.copyOf(this.value);
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

    private void fireMapChangeListeners(final K key, final T oldValue, final T newValue)
    {
        for (final MapValueChangeListener<K, ? super T> listener : this.mapValueChangeListeners)
            listener.valueChanged(this, key, oldValue, newValue);
    }

    /**
     * @return the value checker set for the elements of this property
     */
    public BiFunction<T, T, T> getElementChecker()
    {
        return this.checker;
    }

    /**
     * Set the value element checker for this property. Set it to null to remove
     * the checker.
     * 
     * @param checker
     */
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
        if (!this.valueChangeListeners.isEmpty())
            old = getValue();

        if (this.checker != null)
            value = this.checker.apply(null, value);

        this.value.put(key, value);

        this.fireInvalidationListeners();
        this.fireMapChangeListeners(key, null, value);
        this.fireChangeListeners(old, this.value);
        return null;
    }

    @Override
    public void putAll(final Map<K, ? extends T> elements)
    {
        elements.forEach(this::put);
    }

    /**
     * @return a Set of the property entry element.
     */
    @Override
    public Set<Entry<K, T>> entrySet()
    {
        return this.value.entrySet();
    }

    /**
     * @return a set which contain all the key.
     */
    @Override
    public Set<K> keySet()
    {
        return this.value.keySet();
    }

    /**
     * @return a Collection which contain all the values.
     */
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

        if (!this.valueChangeListeners.isEmpty())
            old = getValue();
        final T rtn = this.value.remove(key);

        this.fireInvalidationListeners();
        this.fireMapChangeListeners(key, oldValue, null);
        this.fireChangeListeners(old, this.value);
        return rtn;
    }

    @Override
    public T replace(final K key, T element)
    {
        final T oldValue = this.value.get(key);
        Map<K, T> old = null;
        if (!this.valueChangeListeners.isEmpty())
            old = getValue();

        if (this.checker != null)
            element = this.checker.apply(this.value.get(key), element);

        final T rtn = this.value.replace(key, element);

        this.fireInvalidationListeners();
        this.fireMapChangeListeners(key, oldValue, element);
        this.fireChangeListeners(old, this.value);
        return rtn;
    }

    /**
     * Remove all the elements for this property
     */
    @Override
    public void clear()
    {
        this.setValue(Maps.newHashMap());
    }

    /**
     * @return the number of element for this property
     */
    @Override
    public int size()
    {
        return this.value.size();
    }
}