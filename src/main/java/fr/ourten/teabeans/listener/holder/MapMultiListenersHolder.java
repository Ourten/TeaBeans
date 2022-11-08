package fr.ourten.teabeans.listener.holder;

import fr.ourten.teabeans.listener.MapValueChangeListener;
import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.value.ObservableValue;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class MapMultiListenersHolder<K, V> extends MultiListenersHolder<Map<K, V>> implements MapListenersHolder<K, V>
{
    private MapValueChangeListener<? super K, ? super V>[] mapValueChangeListeners;

    public MapMultiListenersHolder(
            MapValueChangeListener<? super K, ? super V> mapValueChangeListener,
            ValueChangeListener<? super Map<K, V>> valueChangeListener,
            ValueInvalidationListener arglessValueChangeListener,
            ValueInvalidationListener invalidationListener)
    {
        super(valueChangeListener, arglessValueChangeListener, invalidationListener);

        if (mapValueChangeListener != null)
            this.mapValueChangeListeners = new MapValueChangeListener[] { mapValueChangeListener };
    }

    @Override
    public MapListenersHolder<K, V> addMapChangeListener(MapValueChangeListener<? super K, ? super V> listener)
    {
        if (mapValueChangeListeners == null)
        {
            mapValueChangeListeners = new MapValueChangeListener[] { listener };
            return this;
        }

        for (var mapValueChangeListener : mapValueChangeListeners)
        {
            if (Objects.equals(mapValueChangeListener, listener))
                return this;
        }

        var length = mapValueChangeListeners.length;
        mapValueChangeListeners = Arrays.copyOf(mapValueChangeListeners, length + 1);
        mapValueChangeListeners[length] = listener;
        return this;
    }

    @Override
    public MapListenersHolder<K, V> removeMapChangeListener(MapValueChangeListener<? super K, ? super V> listener)
    {
        if (mapValueChangeListeners == null)
            return this;

        MapValueChangeListener<? super K, ? super V>[] newListeners = cloneListenerArray(listener,
                mapValueChangeListeners,
                MapValueChangeListener.class);

        if (newListeners.length == 1 &&
                (valueChangeListeners == null || valueChangeListeners.length == 1) &&
                (arglessValueChangeListeners == null || arglessValueChangeListeners.length == 1) &&
                (invalidationListeners == null || invalidationListeners.length == 1)
        )
            return new MapMonoListenerHolder<>(newListeners[0], valueChangeListeners == null ? null : valueChangeListeners[0], arglessValueChangeListeners == null ? null : arglessValueChangeListeners[0], invalidationListeners == null ? null : invalidationListeners[0]);

        mapValueChangeListeners = newListeners;
        return this;
    }

    @Override
    public MapListenersHolder<K, V> addChangeListener(ValueChangeListener<? super Map<K, V>> listener)
    {
        return (MapListenersHolder<K, V>) super.addChangeListener(listener);
    }

    @Override
    public MapListenersHolder<K, V> removeChangeListener(ValueChangeListener<? super Map<K, V>> listener)
    {
        if (valueChangeListeners == null)
            return this;

        ValueChangeListener<? super Map<K, V>>[] newListeners = cloneListenerArray(listener,
                valueChangeListeners,
                ValueChangeListener.class);

        if (newListeners.length == 1 &&
                (mapValueChangeListeners == null || mapValueChangeListeners.length == 1) &&
                (arglessValueChangeListeners == null || arglessValueChangeListeners.length == 1) &&
                (invalidationListeners == null || invalidationListeners.length == 1)
        )
            return new MapMonoListenerHolder<>(mapValueChangeListeners == null ? null : mapValueChangeListeners[0], newListeners[0], arglessValueChangeListeners == null ? null : arglessValueChangeListeners[0], invalidationListeners == null ? null : invalidationListeners[0]);

        valueChangeListeners = newListeners;
        return this;
    }

    @Override
    public MapListenersHolder<K, V> addChangeListener(ValueInvalidationListener listener)
    {
        return (MapListenersHolder<K, V>) super.addChangeListener(listener);
    }

    @Override
    public MapListenersHolder<K, V> removeChangeListener(ValueInvalidationListener listener)
    {
        if (arglessValueChangeListeners == null)
            return this;

        var newListeners = cloneListenerArray(listener,
                arglessValueChangeListeners,
                ValueInvalidationListener.class);

        if (newListeners.length <= 1 &&
                (mapValueChangeListeners == null || mapValueChangeListeners.length == 1) &&
                (valueChangeListeners == null || valueChangeListeners.length == 1) &&
                (invalidationListeners == null || invalidationListeners.length == 1)
        )
            return new MapMonoListenerHolder<>(mapValueChangeListeners == null ? null : mapValueChangeListeners[0], valueChangeListeners == null ? null : valueChangeListeners[0], newListeners[0], invalidationListeners == null ? null : invalidationListeners[0]);

        arglessValueChangeListeners = newListeners;
        return this;
    }

    @Override
    public MapListenersHolder<K, V> addListener(ValueInvalidationListener listener)
    {
        return (MapListenersHolder<K, V>) super.addListener(listener);
    }

    @Override
    public MapListenersHolder<K, V> removeListener(ValueInvalidationListener listener)
    {
        if (invalidationListeners == null)
            return this;

        var newListeners = cloneListenerArray(listener,
                invalidationListeners,
                ValueInvalidationListener.class);

        if (newListeners.length <= 1 &&
                (mapValueChangeListeners == null || mapValueChangeListeners.length == 1) &&
                (valueChangeListeners == null || valueChangeListeners.length == 1) &&
                (arglessValueChangeListeners == null || arglessValueChangeListeners.length == 1)
        )
            return new MapMonoListenerHolder<>(mapValueChangeListeners == null ? null : mapValueChangeListeners[0], valueChangeListeners == null ? null : valueChangeListeners[0], arglessValueChangeListeners == null ? null : arglessValueChangeListeners[0], newListeners[0]);

        invalidationListeners = newListeners;
        return this;
    }

    @Override
    public void fireMapChangeListeners(ObservableValue<? extends Map<K, V>> observable, K key, V oldValue, V newValue)
    {
        var listeners = mapValueChangeListeners;

        if (listeners == null)
            return;

        for (var listener : listeners)
            listener.valueChanged(observable, key, oldValue, newValue);
    }

    @Override
    public boolean hasMapChangeListeners()
    {
        return mapValueChangeListeners != null;
    }
}
