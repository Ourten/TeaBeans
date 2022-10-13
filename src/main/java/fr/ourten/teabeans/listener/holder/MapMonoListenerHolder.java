package fr.ourten.teabeans.listener.holder;

import fr.ourten.teabeans.listener.MapValueChangeListener;
import fr.ourten.teabeans.listener.ValueChangeListener;
import fr.ourten.teabeans.listener.ValueInvalidationListener;
import fr.ourten.teabeans.value.ObservableValue;

import java.util.Map;
import java.util.Objects;

public class MapMonoListenerHolder<K, V> extends MonoListenerHolder<Map<K, V>> implements MapListenersHolder<K, V>
{
    private MapValueChangeListener<? super K, ? super V> mapValueChangeListener;

    public MapMonoListenerHolder(MapValueChangeListener<? super K, ? super V> mapValueChangeListener,
                                 ValueChangeListener<? super Map<K, V>> valueChangeListener,
                                 ValueInvalidationListener arglessValueChangeListener,
                                 ValueInvalidationListener invalidationListener)
    {
        super(valueChangeListener, arglessValueChangeListener, invalidationListener);

        this.mapValueChangeListener = mapValueChangeListener;
    }

    @Override
    public MapListenersHolder<K, V> addMapChangeListener(MapValueChangeListener<? super K, ? super V> listener)
    {
        if (mapValueChangeListener != null && !Objects.equals(mapValueChangeListener, listener))
            return new MapMultiListenersHolder<K, V>(mapValueChangeListener, valueChangeListener, arglessValueChangeListener, invalidationListener)
                    .addMapChangeListener(listener);
        mapValueChangeListener = listener;
        return this;
    }

    @Override
    public MapListenersHolder<K, V> removeMapChangeListener(MapValueChangeListener<? super K, ? super V> listener)
    {
        if (Objects.equals(listener, mapValueChangeListener))
            mapValueChangeListener = null;
        if (valueChangeListener == null && arglessValueChangeListener == null && invalidationListener == null)
            return null;
        return this;
    }

    @Override
    public MapListenersHolder<K, V> addChangeListener(ValueChangeListener<? super Map<K, V>> listener)
    {
        if (valueChangeListener != null && !Objects.equals(valueChangeListener, listener))
            return new MapMultiListenersHolder<K, V>(mapValueChangeListener, valueChangeListener, arglessValueChangeListener, invalidationListener)
                    .addChangeListener(listener);
        valueChangeListener = listener;
        return this;
    }

    @Override
    public MapListenersHolder<K, V> removeChangeListener(ValueChangeListener<? super Map<K, V>> listener)
    {
        if (Objects.equals(listener, valueChangeListener))
            valueChangeListener = null;
        if (mapValueChangeListener == null && arglessValueChangeListener == null && invalidationListener == null)
            return null;
        return this;
    }

    @Override
    public MapListenersHolder<K, V> addChangeListener(ValueInvalidationListener listener)
    {
        if (arglessValueChangeListener != null && !Objects.equals(arglessValueChangeListener, listener))
            return new MapMultiListenersHolder<K, V>(mapValueChangeListener, valueChangeListener, arglessValueChangeListener, invalidationListener)
                    .addChangeListener(listener);
        arglessValueChangeListener = listener;
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public MapListenersHolder<K, V> removeChangeListener(ValueInvalidationListener listener)
    {
        if (Objects.equals(listener, arglessValueChangeListener))
            arglessValueChangeListener = null;
        if (mapValueChangeListener == null && valueChangeListener == null && invalidationListener == null)
            return null;
        return this;
    }

    @Override
    public MapListenersHolder<K, V> addListener(ValueInvalidationListener listener)
    {
        if (invalidationListener != null && !Objects.equals(invalidationListener, listener))
            return new MapMultiListenersHolder<K, V>(mapValueChangeListener, valueChangeListener, arglessValueChangeListener, invalidationListener)
                    .addListener(listener);
        invalidationListener = listener;
        return this;
    }

    @Override
    public MapListenersHolder<K, V> removeListener(ValueInvalidationListener listener)
    {
        if (Objects.equals(listener, invalidationListener))
            invalidationListener = null;
        if (mapValueChangeListener == null && valueChangeListener == null && arglessValueChangeListener == null)
            return null;
        return this;
    }

    @Override
    public void fireMapChangeListeners(ObservableValue<? extends Map<K, V>> observable, K key, V oldValue, V newValue)
    {
        if (mapValueChangeListener != null)
            mapValueChangeListener.valueChanged(observable, key, oldValue, newValue);
    }

    @Override
    public boolean hasMapChangeListeners()
    {
        return mapValueChangeListener != null;
    }
}
